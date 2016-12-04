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
    private ArrayList<BusLocations> busLocations = new ArrayList();
    Business(String n) {
        busName = n;
        DBconnection.getBusiness(this);
    }
    public boolean updateBus(String n, String i, String w, String s, Date f) {
        busName = n;
        busIndustry = i;
        busWebsite = w;
        busSummary = s;
        busFounded = f;
        return DBconnection.updateBusiness(n,i,w,s,f);
    }
    protected void setLocations(int locID, String str, String c, String st, int z, float lat, float lon) {
        BusLocations bl = new BusLocations();
        bl.setLocation(locID, str, c, st, z, lat, lon);
        busLocations.add(bl);
    }
    public void addBusLocation(String str, String c, String st, int z, float lat, float lon) {
        int locID = DBconnection.getLocationID(busName, c, str, st, z, lat, lon);
        boolean add=true;
        for (BusLocations bl : busLocations)
            if(bl.getLocationID()==locID) {
                add=false;
                break;
            }
        if(add) {
            setLocations(locID, c, str, st, z, lat, lon);
        }
    }
    public void updateLocation(int locID, String str, String c, String st, int z, float lat, float lon) {
        if(DBconnection.updateBusinessLocation(locID, str, c, st, z, lat, lon));
        {
            for (BusLocations bl : busLocations) {
                if(bl.getLocationID()==locID)
                    busLocations.remove(bl);
            }
            setLocations(locID,str,c,st,z,lat,lon);
        }
    }
    public String getName() {
        return busName;
    }
    public String getIndustry() {
        if(busIndustry!=null)
            return busIndustry;
        return "";
    }
    protected void setIndustry(String i) {
        busIndustry = i;
    }
    public String getWebsite() {
        if(busWebsite!=null)
            return busWebsite;
        return "";
    }
    protected void setWebsite(String w) {
        busWebsite = w;
    }
    public String getSummary () {
        if(busSummary!=null)
            return busSummary;
        return "";
    }
    protected void setSummary(String s) {
        busSummary = s;
    }
    public Date getFounded() {
        if(busFounded!=null)
            return busFounded;
        return new Date();
    }
    protected void setFounded(String s) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            busFounded = df.parse(s);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    protected void setFounded(Date d) {
        busFounded=d;
    }
    public ArrayList getLocations() {
        return busLocations;
    }
}