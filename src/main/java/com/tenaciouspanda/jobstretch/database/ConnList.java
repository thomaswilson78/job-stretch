package com.tenaciouspanda.jobstretch.database;

import java.util.ArrayList;
import java.sql.Connection;
public class ConnList {
    ArrayList<ConnectionObj> conList = new ArrayList<>();
    int userID;//userID for the person logged in

    ConnList(Connection c, int uID) {
        userID = uID;
    }

    //should be called when the user is able to login into the program
    public void addConnectionsToList(Connection conn) {
        Connections c = new Connections();
        String results[][] = c.retreiveConnections(conn, userID);
        if(results!=null)
            for (int x=0;x<results.length;x++) {
                ConnectionObj co = new ConnectionObj();
                co.setUserID(Integer.parseInt(results[x][0]));
                co.setUserName(results[x][1]);
                co.setFName(results[x][2]);
                co.setLName(results[x][3]);
                co.setCity(results[x][4]);
                co.setStreet(results[x][5]);
                co.setState(results[x][6]);
                co.setZip(Integer.parseInt(results[x][7]));
                co.setSummary(results[x][8]);
                co.setEmployed(results[x][9].equals("0"));
                co.setXcoord(Float.parseFloat(results[x][10]));
                co.setYcoord(Float.parseFloat(results[x][11]));
                conList.add(co);
            }
    }

    //returns list of all connections
    public ArrayList<ConnectionObj> returnList() {
        return conList;
    }
}