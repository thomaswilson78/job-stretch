
package com.tenaciouspanda.jobstretch.database;

import java.util.*;


public class Connections {
	DBconnection db = new DBconnection();
	private int userID;
	private String userName;
	private String fname;
	private String lname;
	private String street;
	private String city;
	private String state;
	private int zip;
	private Date startDate;
	private Date endDate;
	private String currentEmployment;
	private String summary;
	Connections() {
		userID=0;
		userName="";
		fname="";
		lname="";
		street="";
		city="";
		state="";
		zip=0;
		currentEmployment="";
		summary="";
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
	
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date d) {
		startDate = d;
	}
	
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date d) {
		endDate = d;
	}
	
	public String getCurrent(){
		return currentEmployment;
	}
	public void setCurrent(String c) {
		currentEmployment = c;
	}
	
	public String getSummary(){
		return summary;
	}
	public void setSummary(String s){
		summary = s;
	}
}
