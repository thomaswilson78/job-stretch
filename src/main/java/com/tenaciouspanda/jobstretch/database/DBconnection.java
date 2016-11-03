package com.tenaciouspanda.jobstretch.database;

import java.sql.*;

public class DBconnection {
    final public static int RESULT_OK = 0;
    final public static int RESULT_ERROR_EXIST = 1;
    final public static int RESULT_ERROR_CONNFAIL = 2;
    private Connection conn;
    public DBconnection() {
        try {
        conn = DriverManager.getConnection
                ("jdbc:mysql://csc440.cwalelqxfs6f.us-east-1.rds.amazonaws.com:3306/CSC440", "limitedUser","");
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    
    public void closeConn() {
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //checks if user has registered an account. If so, will allow the sure to log in (true)
    public boolean checkLoginCred(String user, String password) {
        boolean valid = false;//returned value
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String login = "SELECT * FROM userTable WHERE userName=? AND pword=?";
            pst = conn.prepareStatement(login);
            pst.setString(1, user);
            pst.setString(2, password);
            pst.execute();

            //checks if a result is found for user name and password
            rs = pst.getResultSet();
            if(rs!= null && rs.next()) {
                valid = true;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //close out everything
        finally {
            if(rs!=null) {
                try {
                    rs.close();
                }
                catch (Exception e) {}
            }
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
        return valid;
    }

    //returns userID for user, used for getting connection information
    public int retreiveUserID(String userName) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        int feedback = 0;
        try {
            String search = "SELECT userID FROM userTable WHERE username = ?";
            pst = conn.prepareStatement(search);
            pst.setString(1, userName);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()) {
                feedback = rs.getInt(0);
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
            feedback = 0;
        }
        finally {
            if(rs!=null) {
                try {
                    rs.close();
                }
                catch (Exception e) {}
            }
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
       return feedback;
    }
    
    public int createAccount(String user, String pass, String fname, String lname, String city, String street, String state, int zip) {
        return AccountReg.createAccount(conn, user, pass, fname, lname, city, street, state, zip);
    }
    public void claimAccount(int uID, String user, String pass) {
        AccountReg.claimAccount(conn,uID,user,pass);
    }
}