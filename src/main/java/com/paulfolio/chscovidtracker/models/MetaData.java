package com.paulfolio.chscovidtracker.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Note: this object is created in the CountyDateService service, since that is also fetching the metaData */

public class MetaData {

    private Date lastUpdate;
    private String todayDate;
    private List<String> headerDates;

    public MetaData() {
        lastUpdate = new Date();
        headerDates = new ArrayList<>();
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<String> getHeaderDates() {
        return headerDates;
    }

    public void setHeaderDates(List<String> headerDates) {
        this.headerDates = headerDates;
    }
}
