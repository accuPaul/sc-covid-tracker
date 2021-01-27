package com.paulfolio.chscovidtracker.controllers;

import com.paulfolio.chscovidtracker.models.CountyStats;
import com.paulfolio.chscovidtracker.services.CountyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private CountyDataService countyDataService;

    private boolean nameSortReverse = false;
    private boolean newSortReverse = true;
    private boolean totalSortReverse = true;
    private Comparator<CountyStats> compareByName = (CountyStats c1, CountyStats c2) -> c1.getCounty().compareTo(c2.getCounty());
    private Comparator<CountyStats> compareByNew = (CountyStats c1, CountyStats c2) -> c1.getNewCases().compareTo(c2.getNewCases());
    private Comparator<CountyStats> compareByTotal = (CountyStats c1, CountyStats c2) -> c1.getLatestTotal().compareTo(c2.getLatestTotal());


    @GetMapping({"/","/{sortBy}"})
    public String home(Model model, @PathVariable Optional<String> sortBy) {
        List<CountyStats> allStats = countyDataService.getCountyStats();
        int sumTotal = allStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int newTotal = allStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        String sortField = sortBy.isPresent() ? sortBy.get() : "";

       switch (sortField.toString()) {
           case "new": {
               if (newSortReverse) {
                   allStats.sort(compareByNew.reversed());
               } else {
                   allStats.sort(compareByNew);
               }
               newSortReverse =! newSortReverse;
               nameSortReverse = false;
               totalSortReverse = true;
               break;
           }
           case "total": {
               if (totalSortReverse) {
                   allStats.sort(compareByTotal.reversed());
               } else {
                   allStats.sort(compareByTotal);
               }
               totalSortReverse =! totalSortReverse;
               nameSortReverse = false;
               newSortReverse = true;
               break;
           }
           case "county": {
               if (nameSortReverse) {
                   allStats.sort(compareByName.reversed());
               } else {
                   allStats.sort(compareByName);
               }
               newSortReverse = true;
               nameSortReverse = !nameSortReverse;
               totalSortReverse = true;
               break;
           }
           default: {
               // just keep the list as-is
           }
       }

        model.addAttribute("totalStateCases", sumTotal);
        model.addAttribute("newStateCases",newTotal);
        model.addAttribute("countyStats", allStats);
        model.addAttribute("lastUpdate",countyDataService.getMetaData().getLastUpdate());
        model.addAttribute("today",countyDataService.getMetaData().getTodayDate());
        model.addAttribute("headerDates",countyDataService.getMetaData().getHeaderDates());
        return "home";
    }

}
