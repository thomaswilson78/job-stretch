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
public class Businesses {
    public String[] retreiveBusinessInfo(Connection conn, String busName) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String[] results = new String[4];
        try {
            String stmnt = "SELECT * FROM business WHERE businessName=?";
            pst = conn.prepareStatement(stmnt);
            pst.setString(1, busName);
            pst.executeQuery();
            rs = pst.getResultSet();
            
            while(rs.next()) {
                for(int i=0;i<4;i++)
                    results[i]=rs.getString(i+2);
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
}
