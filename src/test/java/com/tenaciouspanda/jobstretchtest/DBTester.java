/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenaciouspanda.jobstretchtest;
import com.tenaciouspanda.jobstretch.database.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DBTester {
    String username = "myAccount"+(new Random()).nextInt(5000);
    String password = "myPassword"+(new Random()).nextInt(5000);
    
    public void testAll() {
        testConnection();
        testUser();
        testUserUpdate();
    }
    //testing StaticConnection
    public void testConnection() {
        System.out.println("|||||Connection Test|||||");
        if(StaticConnection.checkConnection())
            System.out.println("Connection is open.");
        else
            System.out.println("Connection is not open.");
        
        StaticConnection.initializeConnection();
        System.out.println(StaticConnection.checkConnection());
        if(StaticConnection.checkConnection())
            System.out.println("Connection is open.");
        else
            System.out.println("Connection is not open.");
        Connection conn = StaticConnection.conn;
        StaticConnection.closeConnection();
        StaticConnection.closeConnection();
        try {
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if(StaticConnection.checkConnection())
            System.out.println("Connection is open.");
        else
            System.out.println("Connection is not open.");
    }
    //Test account creation, login, adding, 
    public void testUser() {
        try {
            System.out.println("|||||Login Test|||||");
            
            int success = DBconnection.createAccount(username, password,
                    "Test", "Testingson", "Richmond", +(new Random()).nextInt(5000)+" Street Ave.", "KY", 40475, "Homewrecker", "EKU", "Test Summary HERE", "02/22/2014", "12/31/2016", true);
            if(success!=DBconnection.RESULT_EXIST&&success!=DBconnection.RESULT_CONNECT_FAILED)
                System.out.println("Account Created!");
            else if(success==DBconnection.RESULT_EXIST)
                System.out.println("Account Exist.");
            else
                System.out.println("Database Error.");
            
            int uID = DBconnection.checkLoginCred(username, password);
            if(uID>0) {
                User currentUser = new User(uID);
                currentUser.setContacts();
                System.out.println(currentUser.getUserID());
                System.out.println(currentUser.getUserName());
                System.out.println(currentUser.getFName());
                System.out.println(currentUser.getLName());
                System.out.println(currentUser.getStreet());
                System.out.println(currentUser.getCity());
                System.out.println(currentUser.getState());
                System.out.println(currentUser.getZip());
                System.out.println(currentUser.getBusiness());
                System.out.println(currentUser.getStartDate());
                System.out.println(currentUser.getEndDate());
                System.out.println(currentUser.getEmployed());
                System.out.println(currentUser.getStartDateString());
                System.out.println(currentUser.getEndDateString());
                System.out.println(currentUser.getJobTitle());
                System.out.println(currentUser.getSummary());
                //add new contact to current user
                User newContact = new User(10000000);
                currentUser.addNewContact(newContact);
                //return contact information from contact array list
                User test = currentUser.returnContactProfile(10000000);
                System.out.println(test.getUserID());
                System.out.println(test.getUserName());
                System.out.println(test.getFName());
                System.out.println(test.getLName());
                System.out.println(test.getStreet());
                System.out.println(test.getCity());
                System.out.println(test.getState());
                System.out.println(test.getZip());
                System.out.println(test.getBusiness());
                System.out.println(test.getStartDate());
                System.out.println(test.getEndDate());
                System.out.println(test.getEmployed());
                System.out.println(test.getStartDateString());
                System.out.println(test.getEndDateString());
                System.out.println(test.getJobTitle());
                System.out.println(test.getSummary());
                test = null;
                //removes contact from user's contact list
                currentUser.removeFromContactList(10000000);
                test = currentUser.returnContactProfile(10000000);
                if(test!=null)
                    System.out.println(test.getUserID());
                else
                    System.out.println("Working!");
                //finds users based on first and last name and returns an array list of user objects
                ArrayList<User> results = DBconnection.searchUser("","");
                for (User i : results)
                    System.out.println(i.getUserID()+" "+i.getFName()+" "+i.getLName());
            }
            else {
                System.out.println("Not working.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    //Testing update function
    public void testUserUpdate() {
        try {
            int uID = DBconnection.checkLoginCred(username, password);
            if(uID>0) {
                User currentUser = new User(uID);
                currentUser.setContacts();
                System.out.println(currentUser.getStreet());
                currentUser.updateProfile(currentUser.getFName(), currentUser.getLName(), currentUser.getCity(), (new Random()).nextInt(600)+"Lancaster Ave", currentUser.getState(), currentUser.getZip(), currentUser.getJobTitle(), currentUser.getBusiness(), currentUser.getSummary(), currentUser.getEmployed(), currentUser.getStartDateString(), currentUser.getEndDateString());
                System.out.println(currentUser.getStreet());
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
