/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenaciouspanda.jobstretchtest;
import com.tenaciouspanda.jobstretch.database.*;
import java.sql.Connection;
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
                System.out.println(currentUser.getFName());
                System.out.println(currentUser.getLName());
                System.out.println(currentUser.getZip());
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
        String[][] results = DBconnection.searchUser("Thomas","Wilson");
        for (String[] i : results) {
            for (String k : i) {
                if(k.equals(i[7]))
                    System.out.print(k);
                else
                    System.out.print(k+", ");
            }
            System.out.println();
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
