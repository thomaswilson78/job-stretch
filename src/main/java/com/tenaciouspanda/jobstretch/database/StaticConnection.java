package com.tenaciouspanda.jobstretch.database;
import java.sql.Connection;
import java.sql.DriverManager;

public class StaticConnection {
    public static Connection conn;
    
    public static void initializeConnection() {
        try {
            if(conn==null || conn.isClosed())
            conn = DriverManager.getConnection
                    ("jdbc:mysql://csc440.cwalelqxfs6f.us-east-1.rds.amazonaws.com:3306/CSC440", "limitedUser","");
        } 
        catch (Exception e){
            System.out.println(e);
        }
    }
    //check if connection has been made and is open. If not, connection needs to be opened
    public static boolean checkConnection() {
        try {
        if(conn==null || conn.isClosed())
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
