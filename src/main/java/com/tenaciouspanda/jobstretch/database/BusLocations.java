package com.tenaciouspanda.jobstretch.database;

/**
 *
 * @author Thomas
 */
public class BusLocations {
    private String street,city,state;
    private int locationID,zip;
    private float lat,lon;
    
    public BusLocations() {
        
    }
    
    
    public int getLocationID() {
        return locationID;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public String getState() {
        return state;
    }
    public int getZip() {
        return zip;
    }
    public float getLat() {
        return lat;
    }
    public float getLon() {
        return lon;
    }
    
    protected void setLocation(int lID, String s, String c, String st, int z, float l, float lo) {
        locationID = lID;
        city = c;
        street = s;
        state = st;
        zip = z;
        lat = l;
        lon = lo;
    }
}
