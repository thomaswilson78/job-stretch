//Keep, create variables and functions to keep,retrieve data
package com.tenaciouspanda.jobstretch.database;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Business {
    private String busName;
    private String busSummary;
    private Date busFounded;
    private String[] busLocations;
    
    public String getBusinessName() {
        return busName;
    }
    public void setBusinessName(String b) {
        busName=b;
    }
    public String getBusinessSummary () {
        return busSummary;
    }
    public void setBusinessSummary(String s) {
        busSummary=s;
    }
    public Date getBusinessFounded() {
        return busFounded;
    }
    public void setBusinessFounded(String s) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            busFounded = df.parse(s);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void setBusinessFounded(Date d) {
        busFounded=d;
    }
    public String[] getBusinessLocations() {
        return busLocations;
    }
}