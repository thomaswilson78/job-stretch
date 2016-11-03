/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Thomas
 */
public class Search {
    //for geocoding, returns location by username
    public String returnSearchLocationUser(Connection conn, String username) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String feedback = "";
        try {
            String search = "SELECT Address, City, State, Zip FROM userTable WHERE username = ?";
            pst = conn.prepareStatement(search);
            pst.setString(1, username);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()) {
                feedback = rs.getString(0)+", " + rs.getString(1) +", "+ rs.getString(2) + " " + rs.getInt(3);
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
            feedback = "";
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
    }//needs testing
    //Done

    //get results by name, can possibly be multiple, so using array to return all possible results
    public String[] returnSearchLocationUser(Connection conn, String first, String last) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String feedback[];
        try {
            String search = "SELECT Address, City, State, Zip FROM userTable WHERE fname = ? AND lname = ?";
            pst = conn.prepareStatement(search);
            pst.setString(1, first);
            pst.setString(2, last);
            pst.execute();
            rs = pst.getResultSet();

            if(rs.next()) {
                rs.last();
                int x = rs.getRow();
                feedback = new String[x];
            }
            else {
                return null;
            }
            rs.beforeFirst();

            int y = 0;
            while(rs.next()) {
                feedback[y] = rs.getString(0)+", " + rs.getString(1) +", "+ rs.getString(2) + " " + rs.getInt(3);
                y++;
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
            feedback = null;
        }
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
        return feedback;
    }//needs testing
    //Done

    //return search results for a business, giving all business locations
    public String[] returnSearchLocationBusiness(Connection conn, String name) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String feedback[];
        try {
            String search = "SELECT * FROM businessLocation WHERE name = ?";
            pst = conn.prepareStatement(search);
            pst.setString(1, name);
            pst.execute();
            rs = pst.getResultSet();

            if(rs.next()) {
                rs.last();
                int x = rs.getRow();
                feedback = new String[x];
            }
            else {
                return null;
            }
            rs.beforeFirst();

            int y = 0;
            while(rs.next()) {
                feedback[y] = rs.getString(0)+", " + rs.getString(1) +", "+ rs.getString(2) + " " + rs.getInt(3);
                y++;
            }
        }
        catch (Exception ex) {
                System.out.println(ex);
                feedback = null;
        }
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
        return feedback;
    }//needs testing
    //Done
}
