package com.tenaciouspanda.jobstretchtest;


import com.google.maps.model.GeocodingResult;
import com.tenaciouspanda.jobstretch.Geocoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simon
 */
public class GeocoderTester {
    public void testAll(){
        testGeocoder();
    }
    
    public void testGeocoder(){
        Geocoder g = new Geocoder();
        
        GeocodingResult r = g.geocode("1600 Amphitheatre Parkway Mountain View, CA 94043");
        
        if(r == null)
            throw new IllegalStateException("Got null for geocoded coordinate");
    }
}
