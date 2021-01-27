package com.paulfolio.chscovidtracker.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CountyStats {

    private String state;
    private String county;
    private int latestTotal;
    private int newCases;
    private LocalDateTime lastUpdate;
    List<String> headerDates = new ArrayList<>();
    List<Integer> newCasesList = new ArrayList<>();

    public CountyStats() {
        lastUpdate = LocalDateTime.now(ZoneId.of("America/New_York"));
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(int latestTotal) {
        this.latestTotal = latestTotal;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public Integer getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public List<String> getHeaderDates() {
        return headerDates;
    }

    public void setHeaderDates(List<String> headerDates) {
        this.headerDates = headerDates;
    }

    public List<Integer> getNewCasesList() {
        return newCasesList;
    }

    public void setNewCasesList(List<Integer> newCasesList) {
        this.newCasesList = newCasesList;
    }

    @Override
    public String toString() {
        return "CountyStats{" +
                "state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", latestTotal=" + latestTotal +
                '}';
    }
}
