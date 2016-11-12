/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch.database;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Thomas
 */
public class AccountReg {
    //creates account for user
    public static int createAccount(Connection conn, String user, String pass, String fname, String lname, String city, String street, String state, int zip) {
        int result = 0;//returned value, 0 means account created
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //check if username exist
            String checkExistUN = "SELECT username FROM userTable WHERE username=?";
            pst = conn.prepareStatement(checkExistUN);
            pst.setString(1, user);
            pst.execute();
            rs = pst.getResultSet();
            if(rs.next()) {
                result = 1;//error: username exist
                throw new Exception("Username Exist");
            }
            //claim account if exists
            String checkExistAccount = "SELECT userTable.userID FROM userTable JOIN userLocation ON userTable.userID = userLocation.userID "
                    + "WHERE fname=? AND lname=? AND city=? AND street=? AND state=? AND zip=? AND username IS NULL";
            pst = conn.prepareStatement(checkExistAccount);
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, city);
            pst.setString(4, street);
            pst.setString(5, state);
            pst.setInt(6, zip);
            pst.execute();
            rs = pst.getResultSet();
            if(rs != null && rs.next()) {
                int response = JOptionPane.showConfirmDialog(null,"An account with this info already exist. Do you wish to claim it?","Confirm",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                //if yes, returns entire userID
                if(response == JOptionPane.YES_OPTION)
                    result = rs.getInt("userID");//returns ID, call claimAccount
            }
            if(result==0) {
                String accountSetup = "INSERT INTO userTable (username, pword, fname, lname) VALUES (?,?,?,?)"; 
                pst = conn.prepareStatement(accountSetup, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, user);
                pst.setString(2, pass);
                pst.setString(3, fname);
                pst.setString(4, lname);
                pst.execute();
                rs = pst.getGeneratedKeys();
                int newUserID=0;
                if(rs.next()) {
                    newUserID = rs.getInt(1);
                }
                String locationSetup = "INSERT INTO userLocation (userID, city, street, state, zip) VALUES (?,?,?,?,?)";
                pst = conn.prepareStatement(locationSetup);
                pst.setInt(1, newUserID);
                pst.setString(2, city);
                pst.setString(3, street);
                pst.setString(4, state);
                pst.setInt(5, zip);
                pst.execute();
            }
        }
        catch (Exception e) {
                System.out.println(e);
                if(result==0)
                    result = 2;//error: invalid information
        }
        //close out everything
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
            if(rs!=null) {
                try {
                    rs.close();
                }
                catch (Exception e) {}
            }
        }
        return result;
    }
    //if an account already exist
    public static void claimAccount(Connection conn, int userID, String username, String password) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String accountSetup = "UPDATE userTable SET username=?, pword=? WHERE userID=?";
            pst = conn.prepareStatement(accountSetup);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setInt(3, userID);

            pst.execute();
        }
        catch (Exception e) {
                System.out.println(e);
        }
        //close out everything
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
            if(rs!=null) {
                try {
                    rs.close();
                }
                catch (Exception e) {}
            }
        }
    }
}
