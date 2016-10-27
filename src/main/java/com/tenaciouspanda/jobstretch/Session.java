/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch;

import com.tenaciouspanda.jobstretch.database.DBconnection;

/**
 *
 * @author Simon
 */
public class Session {
    protected DBconnection dbc;
    protected String uid;
    
    public Session(){
        dbc = new DBconnection();
    }
    public boolean authenticate(String username, String password){
        if(DBconnection.checkLoginCred(username, password)){
            uid = username;
            return true;
        }else {
            return false;
        }
    }
    public boolean isAuthenticated(){
        return uid == null;
    }
}
