/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch.database;

import java.util.Date;

/**
 *
 * @author Thomas
 */
public class ConnectionObj {
    private int userID;
	private String userName;
	private String fname;
	private String lname;
	private String street;
	private String city;
	private String state;
	private int zip;
	private String summary;
        private boolean employed;
        private float xcoord;
        private float ycoord;
	ConnectionObj() {
            userID=0;
            userName="";
            fname="";
            lname="";
            street="";
            city="";
            state="";
            zip=0;
            summary="";
            employed=false;
	}
	//set values for this connection object
	public int getUserID() {
		return userID;
	}
	public void setUserID(int u) {
		userID = u;
	}
	
	public String getUserName(){
		return userName;
	}
	public void setUserName(String u){
		userName = u;
	}
	
	public String getFName(){
		return fname;
	}
	public void setFName(String f) {
		fname = f;
	}
	
	public String getLName(){
		return lname;
	}
	public void setLName(String l) {
		lname = l;
	}
	
	public String getStreet(){
		return street;
	}
	public void setStreet(String s){
		street = s;
	}
	
	public String getCity(){
		return city;
	}
	public void setCity(String c) {
		city = c;
	}
	
	public String getState(){
		return state;
	}
	public void setState(String s) {
		state = s;
	}
	
	public int getZip(){
		return zip;
	}
	public void setZip(int z) {
		zip = z;
	}
	
	public String getSummary(){
		return summary;
	}
	public void setSummary(String s){
		summary = s;
	}
        
        public boolean getEmployed(){
		return employed;
	}
	public void setEmployed(boolean e){
		employed = e;
	}
        
        public float getXcoord() {
            return xcoord;
        }
        public void setXcoord(float x) {
            xcoord = x;
        }
        
        public float getYcoord() {
            return ycoord;
        }
        public void setYcoord(float y) {
            ycoord = y;
        }
}
