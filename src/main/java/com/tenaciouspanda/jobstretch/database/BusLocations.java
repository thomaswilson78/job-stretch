
package com.tenaciouspanda.jobstretch.database;

import java.util.ArrayList;
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
    protected void setLocationID(int lID) {
        locationID = lID;
    }
}
