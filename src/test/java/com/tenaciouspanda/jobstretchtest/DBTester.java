/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenaciouspanda.jobstretchtest;
import com.tenaciouspanda.jobstretch.database.*;
import java.sql.Connection;
import java.util.ArrayList;

public class DBTester {
    public void testAll() {
        testConnection();
        testLogin();
        testUpdate();
    }
    //testing StaticConnection
    public void testConnection() {
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
    //Check Login
    public void testLogin() {
        try {
            int uID = DBconnection.checkLoginCred("myAccount", "myPassword");
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
                User newContact = new User(10000001);
                currentUser.addToContactList(newContact);
                User test = currentUser.returnContactProfile(10000001);
                System.out.println(test.getFName());
            }
            else {
                System.out.println("Not working");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        ArrayList<User> results = DBconnection.searchUser("","");
        for (User i : results) {
                System.out.println(i.getUserID()+" "+i.getFName()+" "+i.getLName());
            }
    }
    //Testing update function
    public void testUpdate() {
        try {
            int uID = DBconnection.checkLoginCred("myAccount", "myPassword");
            if(uID>0) {
                User currentUser = new User(uID);
                currentUser.setContacts();
                System.out.println(currentUser.getStreet());
                currentUser.updateProfile(currentUser.getFName(), currentUser.getLName(), currentUser.getCity(), "521 Lancaster Ave", currentUser.getState(), currentUser.getZip(), currentUser.getJobTitle(), currentUser.getBusiness(), currentUser.getSummary(), currentUser.getEmployed(), currentUser.getStartDateString(), currentUser.getEndDateString());
                System.out.println(currentUser.getStreet());
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
