package com.tenaciouspanda.jobstretch.database;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Business {
    private String busName;
    private String busIndustry;
    private String busWebsite;
    private String busSummary;
    private Date busFounded;
    private ArrayList busLocations = new ArrayList();
    Business(String n) {
        busName = n;
        DBconnection.getBusiness(this);
    }
    public void updateBus(String n, String i, String w, String s, Date f, ArrayList l) {
        busName = n;
        busIndustry = i;
        busWebsite = w;
        busSummary = s;
        busFounded = f;
        busLocations = l;
        //DBconnection.updateBusiness(n,i,w,s,f,l);
    }
    //this update function handles date founded as a String and converts it into a Date variable.
    public void updateBus(String n, String i, String w, String s, String f, ArrayList l) {
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
        if(busIndustry!=null)
            return busIndustry;
        return "";
    }
    public void setIndustry(String i) {
        busIndustry = i;
    }
    public String getWebsite() {
        if(busWebsite!=null)
            return busWebsite;
        return "";
    }
    public void setWebsite(String w) {
        busWebsite = w;
    }
    public String getSummary () {
        if(busSummary!=null)
            return busSummary;
        return "";
    }
    public void setSummary(String s) {
        busSummary = s;
    }
    public Date getFounded() {
        if(busFounded!=null)
            return busFounded;
        return new Date();
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
    public ArrayList getLocations() {
        return busLocations;
    }
    public void setLocations(String[] loc) {
        busLocations.add(loc);
    }
}