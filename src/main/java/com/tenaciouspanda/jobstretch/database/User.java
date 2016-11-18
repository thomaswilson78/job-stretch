//Keep, 
package com.tenaciouspanda.jobstretch.database;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class User {
    private ArrayList<User> connections = new ArrayList();
    final private int userID;
    private int zip;
    private String userName,fname,lname,street,city,state,business,jobTitle,summary;
    private Date startDate,endDate;
    private boolean employed;
    private float lat,lon;
    User(int uID) {
        userID=uID;
        DBconnection.setUser(this, userID);
    }
    //finds user based on userID and returns user object
    public User returnContactProfile(int contactID) {
        User returnUser = null;
        for (User u : connections) {
            if(u.userID==contactID) {
                returnUser = u;
                break;
            }
        }
        return returnUser;
    }
    
    //set values for this connection object
    public void updateProfile(String user, String fst, String lst, 
            String c, String str, String st, 
            int z, String occu, String bus, 
            String sum, boolean emp, String start, String end) {
        //updates values in the user object
        userName=user.trim();
        fname=fst.trim();
        lname=lst.trim();
        city=c.trim();
        street=str.trim();
        state=st.trim();
        zip=z;
        jobTitle=occu.trim();
        business=bus.trim();
        summary=sum.trim();
        employed=emp;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            startDate = df.parse(start);
            endDate = df.parse(end);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        DBconnection.updateUser(this);
    }
    public void addContactToList(User contact) {
        connections.add(contact);
        DBconnection.addConnection(this.userID, contact.getUserID());
    }
    //set values for this connection object
    public int getUserID() {
            return userID;
    }  
    public String getUserName(){
            return userName;
    }
    public void setUserName(String u) {
        userName=u;
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
    public void setStreet(String s) {
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
    public String getBusiness() {
        return business;
    }
    public void setBusiness(String b) {
        business = b;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String j) {
        jobTitle = j;
    }  
    public String getStartDate() {
        return startDate.toString();
    }
    public void setStartDate(Date s) {
        try {
            startDate = s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public String getEndDate() {
        return endDate.toString();
    }
    public void setEndDate(Date s) {
        try {
            endDate = s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public String getSummary(){
        return summary;
    }
    public void setSummary(String s) {
        summary = s;
    }
    public boolean getEmployed(){
        return employed;
    }
    public void setEmployed(boolean e) {
        employed=e;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float l) {
        lat = l;
    }
    public float getLon() {
        return lon;
    }
    public void setLon(float l) {
        lon = l;
    }
}