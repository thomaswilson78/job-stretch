/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch;

import com.tenaciouspanda.jobstretch.database.DBconnection;
import com.tenaciouspanda.jobstretch.database.User;
import com.tenaciouspanda.jobstretch.database.Business;
import com.tenaciouspanda.jobstretch.database.StaticConnection;
/**
 *
 * @author Simon
 */
public class Session {
    protected User currentUser;
    
    public Session(){
    }
    public boolean authenticate(String username, String password){
        int returned = DBconnection.checkLoginCred(username, password);
        if(returned!=DBconnection.RESULT_EXIST && returned != DBconnection.RESULT_CONNECT_FAILED) {
            DBconnection.setUser(currentUser, returned);
            DBconnection.setContacts(currentUser);
            return true;
        }
        return false;
    }
    public boolean isAuthenticated(){
        return currentUser == null;
    }

    public boolean register(
            String user,
            String pass,
            String fname,
            String lname,
            String city,
            String street,
            String state,
            int zip,
            String occu,
            String bus,
            boolean employed){
        int result = DBconnection.createAccount(user, pass, fname, lname, city, street, state, zip, occu, bus, employed);
        
        return (result == DBconnection.RESULT_OK);
    }

    public void logout() {
        this.currentUser = null;
        StaticConnection.closeConnection();
    }
}
