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
        zip=0;
        employed=false;
        lat=0;lon=0;
        DBconnection.setUser(this, userID);
    }
    /**
    * Initializes contact list for the user. Should be used after user logs in.
    * */
    public void setContacts() {
        DBconnection.setContacts(this);
    }
    /**
    * Updates information for user object, then updates database via DBconnection class. Should be used for updating the user's profile instead of the set functions.
    *
     * @param fst
     * @param lst
     * @param c
     * @param str
     * @param st
     * @param z
     * @param occu
     * @param bus
     * @param sum
     * @param emp
     * @param start
     * @param end */
    public void updateProfile(String fst, String lst, 
            String c, String str, String st, 
            int z, String occu, String bus, 
            String sum, boolean emp, String start, String end) {
        //updates values in the user object
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
            if(!start.equals(""))
                startDate = df.parse(start);
            if(!end.equals(""))
                endDate = df.parse(end);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        DBconnection.updateUser(this);
    }
    /**
    * Returns a user object containing the contact based on the user ID provided.
    *
     * @param contactID
     * @return  */
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
    /**
     * For DBconnection. Takes contact from database and adds them to the contact list.
     * @param contact 
     */
    //
    public void addToContactList(User contact) {
        connections.add(contact);
    }
    /**
     * Used for adding new contacts to the user's contact list and to the database.
     * @param contact 
     */
    public void addNewContact(User contact) {
        connections.add(contact);
        DBconnection.addContact(this.userID, contact.getUserID());
    }
    
    /**
     * Returns UserID.
     * @return 
     */
    public int getUserID() {
            return userID;
    }
    /**
     * Returns username.
     * @return 
     */
    public String getUserName(){
            return userName;
    }
    /**
     * Sets username, should only be used by DBconnection.
     * @param u
     */
    public void setUserName(String u) {
        userName=u;
    }
    /**
     * Returns user's first name.
     * @return 
     */
    public String getFName(){
        if(fname!=null)
            return fname;
        return "";
    }
    /**
     * Sets user's first name, should only be used by DBconnection.
     * @param f 
     */
    public void setFName(String f) {
        fname = f;
    }
    /**
     * Returns user's last name.
     * @return 
     */
    public String getLName(){
        if(lname!=null)
            return lname;
        return "";
    }
    /**
     * Sets user's last name, should only be used by DBconnection.
     * @param l
     */
    public void setLName(String l) {
        lname = l;
    }
    /**
     * Returns user's street that they work on.
     * @return 
     */
    public String getStreet(){
        if(street!=null)
            return street;
        return "";
    }
    /**
     * Sets user's street, should only be used by DBconnection.
     * @param s
     */
    public void setStreet(String s) {
        street = s;
    }
    /**
     * Returns user's city that they work in.
     * @return 
     */
    public String getCity(){
        if(city!=null)
            return city;
        return "";
    }
    /**
     * Sets user's city, should only be used by DBconnection.
     * @param c
     */
    public void setCity(String c) {
        city = c;
    }
    /**
     * Returns user's state that they work in.
     * @return 
     */
    public String getState(){
        if(state!=null)
            return state;
        return "";
    }
    /**
     * Sets user's state, should only be used by DBconnection.
     * @param s
     */
    public void setState(String s) {
        state = s;
    }
    /**
     * Returns user's zip code that they work in.
     * @return 
     */
    public int getZip(){
            return zip;
    }
    /**
     * Sets user's zip code, should only be used by DBconnection.
     * @param z
     */
    public void setZip(int z) {
        zip = z;
    }
    /**
     * Returns the business the user works for.
     * @return 
     */
    public String getBusiness() {
        if(business!=null)
            return business;
        return "";
    }
    /**
     * Sets user's business, should only be used by DBconnection.
     * @param b
     */
    public void setBusiness(String b) {
        business = b;
    }
    /**
     * Returns user's job title.
     * @return 
     */
    public String getJobTitle() {
        if(jobTitle!=null)
            return jobTitle;
        return "";
    }
    /**
     * Sets user's job title, should only be used by DBconnection.
     * @param j
     */
    public void setJobTitle(String j) {
        jobTitle = j;
    }
    /**
     * Returns a string of the user's start date.
     * @return 
     */
    public String getStartDateString() {
        if (startDate!=null)
            return startDate.toString();
        return "";
    }
    /**
     * Returns a Date object of the user's start date.
     * @return 
     */
    public Date getStartDate() {
        if(startDate!=null)
            return startDate;
        return new Date();
    }
    /**
     * Set's user's start date, should only be used by DBconnection.
     * @param s 
     */
    public void setStartDate(Date s) {
        try {
            startDate = s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Returns a string of the user's end date.
     * @return 
     */
    public String getEndDateString() {
        if (startDate!=null)
            return startDate.toString();
        return "";
    }
    /**
     * Returns a date object of the user's end date.
     * @return 
     */
    public Date getEndDate() {
        if(endDate!=null)
            return endDate;
        return new Date();
    }
    /**
     * Set's user's end date, should only be used by DBconnection.
     * @param s 
     */
    public void setEndDate(Date s) {
        try {
            endDate = s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Returns user's summary.
     * @return 
     */
    public String getSummary(){
        if(summary!=null)
            return summary;
        return "";
    }
    /**
     * Set's user's summary, should only be used by DBconnection.
     * @param s 
     */
    public void setSummary(String s) {
        summary = s;
    }
    /**
     * Returns user's employment status.
     * @return 
     */
    public boolean getEmployed(){
        return employed;
    }
    /**
     * Set's user's employment status, should only be used by DBconnection.
     * @param e
     */
    public void setEmployed(boolean e) {
        employed=e;
    }
    /**
     * Returns the latitude location of where the user works. For the Google Map.
     * @return 
     */
    public float getLat() {
        return lat;
    }
    /**
     * Set's latitude location for user, should only be used by DBconnection.
     * @param l 
     */
    public void setLat(float l) {
        lat = l;
    }
    /**
     * Returns the longitude location of where the user works. For the Google Map.
     * @return 
     */
    public float getLon() {
        return lon;
    }
    /**
     * Set's longitude location for user, should only be used by DBconnection.
     * @param l 
     */
    public void setLon(float l) {
        lon = l;
    }
    /**
     * Returns an array list that holds the value for all the user's contacts.
     * @return 
     */
    public ArrayList<User> getContacts() {
        return connections;
    }
}