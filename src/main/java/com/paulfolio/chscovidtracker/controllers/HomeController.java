package com.paulfolio.chscovidtracker.controllers;

import com.paulfolio.chscovidtracker.models.CountyStats;
import com.paulfolio.chscovidtracker.services.CountyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CountyDataService countyDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<CountyStats> allStats = countyDataService.getCountyStats();
        int sumTotal = allStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int newTotal = allStats.stream().mapToInt(stat -> stat.getNewCases()).sum();
        model.addAttribute("totalStateCases", sumTotal);
        model.addAttribute("newStateCases",newTotal);
        model.addAttribute("countyStats", allStats);
        model.addAttribute("lastUpdate",countyDataService.getMetaData().getLastUpdate().toLocaleString());
        model.addAttribute("today",countyDataService.getMetaData().getTodayDate());
        model.addAttribute("headerDates",countyDataService.getMetaData().getHeaderDates());
        return "home";
    }
}
