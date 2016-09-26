/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class Geocoder {
    private String api_key = "AIzaSyBIG3n-yCC830m-wQ5fkvWfZOYhDP8Ah58";
    private GeoApiContext context;
    
    
    public Geocoder(){
        context = new GeoApiContext().setApiKey(api_key);
    }
    
    public GeocodingResult geocode(String location) {
        GeocodingResult result = null;
        if(context == null)
            throw new IllegalStateException("Must initialize Geocoder before use");
        
        try {
            result =  GeocodingApi.geocode(context,
                    location).await()[0];
        } catch (Exception ex) {
            Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
