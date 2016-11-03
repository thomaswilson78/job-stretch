
package com.tenaciouspanda.jobstretch.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Connections {
    public String[][] retreiveConnections(Connection conn, int userID) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String[][] results = null;
        try {
            String stmnt = "SELECT userTable.userID,userName,fname,lname,city,street,state,zip,summary,employed,Xcoord,Ycoord "
                    + "FROM userTable JOIN connections ON userTable.userID=userConnection "
                    + "WHERE connections.userID=?";
            pst = conn.prepareStatement(stmnt);
            pst.executeQuery();
            rs = pst.getResultSet();
            if(rs.next()) {
                rs.last();
                results = new String[rs.getRow()][12];
                rs.beforeFirst();
            }
            else {
                rs.close();
                pst.close();
                return null;
            }
            int x=0;
            while(rs.next()) {
                for(int i=0;i<12;i++)
                    results[x][i]=rs.getString(i);
                x++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally {
            if(rs!=null) {
               try {
                   rs.close();
               } catch (Exception e) {}
            }
            if(pst!=null) {
                try {
                    pst.close();
                } catch (Exception e) {}
            }
        }
        return results;
    }
    //add connection that does not have an account
    public void addConnection(Connection conn, int userID, String f, String l, String s, String c, String st, int zip, Date start, Date end, String employer) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String search = "INSERT INTO userTable (userName, firstName, lastName, street, city, state, zip) VALUES (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(search);
            pst.setString(1, f);
            pst.setString(2, l);
            pst.setString(3, s);
            pst.setString(4, c);
            pst.setString(5, st);
            pst.setInt(6, zip);
            pst.execute();
            pst.close();

            String getNewUserID = "SELECT userID FROM userTable WHERE userName IS NULL";
            pst = conn.prepareStatement(getNewUserID);
            pst.execute();
            rs = pst.getResultSet();
            rs.last();
            int newUserID = rs.getInt(0);
            pst.close();

            String addCon = "INSERT INTO connections VALUES (?,?), VALUES (?,?)";
            pst = conn.prepareStatement(addCon);
            pst.setInt(1, userID);
            pst.setInt(2, newUserID);
            pst.setInt(3, newUserID);
            pst.setInt(4, userID);
            pst.execute();
        }
        catch (Exception ex){
            System.out.println(ex);
        }		 
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
                pst = null;
            }
        }
    }
    //add connection for existing user
    public void addConnection(Connection conn, int userID, int connID) {
        PreparedStatement pst = null;
        try {
            String addCon = "INSERT INTO connections VALUES (?,?), VALUES (?,?)";
            pst = conn.prepareStatement(addCon);
            pst.setInt(1, userID);
            pst.setInt(2, connID);
            pst.setInt(3, connID);
            pst.setInt(4, userID);
            pst.execute();
        }
        catch (Exception ex){
            System.out.println(ex);
        }		 
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
                pst = null;
            }
        }
    }
}
