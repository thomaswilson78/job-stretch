//Keep, create variables and functions to keep,retrieve data
package com.tenaciouspanda.jobstretch.database;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Business {
    private String busName;
    private String busIndustry;
    private String busWebsite;
    private String busSummary;
    private Date busFounded;
    private String[] busLocations;
    Business(String n) {
        busName = n;
        DBconnection.getBusiness(this);
    }
    public void updateBus(String n, String i, String w, String s, Date f, String[] l) {
        busName = n;
        busIndustry = i;
        busWebsite = w;
        busSummary = s;
        busFounded = f;
        busLocations = l;
        //DBconnection.updateBusiness(n,i,w,s,f,l);
    }
    //this update function handles date founded as a String and converts it into a Date variable.
    public void updateBus(String n, String i, String w, String s, String f, String[] l) {
        busName = n;
        busSummary = s;
        busIndustry = i;
        busWebsite = w;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            busFounded = df.parse(f);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        busLocations = l;
        //DBconnection.updateBusiness(n,i,w,s,busFounded,l);
    }
    public String getName() {
        return busName;
    }
    public String getIndustry() {
        return busIndustry;
    }
    public void setIndustry(String i) {
        busIndustry = i;
    }
    public String getWebsite() {
        return busWebsite;
    }
    public void setWebsite(String w) {
        busWebsite = w;
    }
    public String getSummary () {
        return busSummary;
    }
    public void setSummary(String s) {
        busSummary = s;
    }
    public Date getFounded() {
        return busFounded;
    }
    public void setFounded(String s) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            busFounded = df.parse(s);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void setFounded(Date d) {
        busFounded=d;
    }
    public String[] getLocations() {
        return busLocations;
    }
}