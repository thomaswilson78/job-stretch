package com.tenaciouspanda.jobstretch.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.ArrayList;

public class DBconnection {
    final static public int RESULT_OK = 0;
    final static public int RESULT_EXIST = -1;
    final static public int RESULT_CONNECT_FAILED = -2;
    /*Account Creation and Authorization*/
    //checks if user has registered an account. If so, will allow the sure to log in. Tested, works.
    public static int checkLoginCred(String user, String password) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        int valid = -1;//returned value
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String login = "SELECT userID FROM userTable WHERE userName=? AND pword=?";
            pst = StaticConnection.conn.prepareStatement(login);
            pst.setString(1, user);
            pst.setString(2, password);
            pst.execute();

            //checks if a result is found for user name and password
            rs = pst.getResultSet();
            if(rs!= null && rs.next()) {
                valid = rs.getInt("userID");
            }
        }
        catch (Exception e) {
            System.out.println(e);
            valid = -2;
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
    //creates account for user. Tested, works.
    public static int createAccount(
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
            String sum, 
            String startDate, 
            String endDate, 
            boolean employed) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        int result = 0;//returned value, 0 means account created
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //check if username exist
            String checkExistUN = "SELECT username FROM userTable WHERE username=?";
            pst = StaticConnection.conn.prepareStatement(checkExistUN);
            pst.setString(1, user);
            pst.execute();
            rs = pst.getResultSet();
            if(rs.next()) {
                result = -1;//error: username exist
                throw new Exception("Username Exist");
            }
            int locationID = getLocationID(bus,city,street,state,zip,0,0);
            //claim account if exists
            int newUserID=0;
            String checkExistAccount = "SELECT userTable.userID FROM userTable "
                    + "JOIN employment ON userTable.userID=employment.userID "
                    + "WHERE fname=? AND lname=? AND jobTitle=? AND businessInfoID=? AND username IS NULL";
            pst = StaticConnection.conn.prepareStatement(checkExistAccount);
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, occu);
            pst.setInt(4, locationID);
            pst.execute();
            rs = pst.getResultSet();
            if(rs != null && rs.next()) {
                int response = JOptionPane.showConfirmDialog(null,"An account with this info already exist. Do you wish to claim it?","Confirm",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                //if yes, returns entire userID
                if(response == JOptionPane.YES_OPTION) {
                    newUserID = rs.getInt("userID");//returns ID, call claimAccount
                    result=3;
                }
            }
            //creates account
            if(result==0) {
                //adds data in userTable
                String accountSetup = "INSERT INTO userTable (username, pword, fname, lname, employed, summary) VALUES (?,?,?,?,?,?)";
                pst = StaticConnection.conn.prepareStatement(accountSetup, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, user);
                pst.setString(2, pass);
                pst.setString(3, fname);
                pst.setString(4, lname);
                pst.setBoolean(5, employed);
                pst.setString(6, sum);
                pst.execute();
                rs = pst.getGeneratedKeys();
                if(rs.next()) 
                    newUserID = rs.getInt(1);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date start = df.parse(startDate);
                Date end = df.parse(endDate);
                String locationSetup = "INSERT INTO employment (userID, jobTitle, businessInfoID, startDate, endDate) VALUES (?,?,?,?,?)";//username, password, 
                pst = StaticConnection.conn.prepareStatement(locationSetup);
                pst.setInt(1, newUserID);
                pst.setString(2, occu);
                pst.setInt(3, locationID);
                pst.setDate(4, (new java.sql.Date(start.getTime())));
                pst.setDate(5, (new java.sql.Date(end.getTime())));
                pst.execute();
            }
            else 
                result=claimAccount(newUserID,user,pass);
        }
        catch (Exception e) {
                System.out.println(e);
                if(result==0 || result >2)
                    result = -2;//error: invalid information
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
    //attaches account to user if there exist an account with the same information but no username. Tested, works.
    private static int claimAccount(int userID, String username, String password) {
        int result=0;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String accountSetup = "UPDATE userTable SET username=?, pword=? WHERE userID=?"; 
            pst = StaticConnection.conn.prepareStatement(accountSetup);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setInt(3, userID);

            pst.execute();
        }
        catch (Exception e) {
            System.out.println(e);
            result=1;
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
    
    /*User Profiling*/
    //retreive user info, user to get info when user logs in and for getting info on contacts/connections. Tested, works.
    public static void setUser(User currentUser,int userID) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {            
            String getUTInfo = "SELECT userName,fname,lname,summary,employed FROM userTable WHERE userID=?";
            pst = StaticConnection.conn.prepareStatement(getUTInfo);
            pst.setInt(1, userID);
            pst.execute();
            rs = pst.getResultSet();
            if(rs.next()){
                currentUser.setUserName(rs.getString(1));
                currentUser.setFName(rs.getString(2));
                currentUser.setLName(rs.getString(3));
                currentUser.setSummary(rs.getString(4));
                currentUser.setEmployed(rs.getBoolean(5));
            }
            String getEmpInfo = "SELECT startDate,endDate,jobTitle,city,street,state,zip,businessName FROM employment "
                    +"JOIN businessLocations ON employment.businessInfoID = businessLocations.locationID "
                    + "WHERE userID = ?";
            pst = StaticConnection.conn.prepareStatement(getEmpInfo);
            pst.setInt(1, userID);
            pst.execute();
            rs = pst.getResultSet();
            if(rs.next()) {
                currentUser.setStartDate(rs.getDate(1));
                currentUser.setEndDate(rs.getDate(2));
                currentUser.setJobTitle(rs.getString(3));
                currentUser.setCity(rs.getString(4));
                currentUser.setStreet(rs.getString(5));
                currentUser.setState(rs.getString(6));
                currentUser.setZip(rs.getInt(7));
                currentUser.setBusiness(rs.getString(8));    
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
    }
    //updates user information in database. Untested.
    public static void updateUser(User currentUser) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        try {
            String updateUT = "UPDATE userTable SET fname=?,lname=?,summary=?,employed=? WHERE userID=?";
            pst = StaticConnection.conn.prepareStatement(updateUT);
            pst.setString(1, currentUser.getFName());
            pst.setString(2, currentUser.getLName());
            pst.setString(3, currentUser.getSummary());
            pst.setBoolean(4, currentUser.getEmployed());
            pst.setInt(5, currentUser.getUserID());
            pst.execute();
            
            int locationID = getLocationID(currentUser.getBusiness(),currentUser.getCity(),currentUser.getStreet(),currentUser.getState(),currentUser.getZip(),currentUser.getLat(),currentUser.getLon());
            
            String updateE = "UPDATE employment SET startDate=?,endDate=?,jobTitle=?,businessInfoID=? WHERE userID=?";
            pst = StaticConnection.conn.prepareStatement(updateE);
            java.sql.Date sqlDate = new java.sql.Date(currentUser.getStartDate().getTime());
            pst.setDate(1, sqlDate);
            sqlDate = new java.sql.Date(currentUser.getEndDate().getTime());
            pst.setDate(2, sqlDate);
            pst.setString(3, currentUser.getJobTitle());
            pst.setInt(4, locationID);
            pst.setInt(5, currentUser.getUserID());
            pst.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
    }
    //gets contact information based on current user and adds them to the list for the current user. Tested, works.
    public static void setContacts(User currentUser) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String getConIDs = "SELECT userConnection FROM connections WHERE userID = ?";
        try {
            pst = StaticConnection.conn.prepareStatement(getConIDs);
            pst.setInt(1, currentUser.getUserID());
            pst.execute();
            rs = pst.getResultSet();
            rs.last();
            int max = rs.getRow();
            rs.beforeFirst();
            int[] connectionIDs = new int [max];
            int pos=0;
            while(rs.next()) {
                connectionIDs[pos] = rs.getInt(1);
                pos++;
            }
            for (int cID : connectionIDs) {
                User contact = new User(cID);
                setUser(contact,cID);
                currentUser.addToContactList(contact);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
    
    /*Contacts*/
    //add connection that does not have an account. userID refers to the user who is logged, not contact's userID. Untested.
    public static boolean addNonexistantContact(int userID, String user, String pass, 
            String fname, String lname, String city, String street, String state, 
            int zip, String occu, String bus, Date start, Date end, boolean employed) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String addConnUT = "INSERT INTO userTable (firstName, lastName, employed) VALUES (?,?,?)";
            pst = StaticConnection.conn.prepareStatement(addConnUT, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setBoolean(3, employed);
            pst.execute();
            rs = pst.getGeneratedKeys();
            int newKey = 0;
            if(rs.next())
                newKey=rs.getInt(1);
            
            int locationID = getLocationID(bus,city,street,state,zip,0,0);
            
            String addConnE = "INSERT INTO employment (userID, startDate, endDate, jobTitle, businessInfoID) VALUES (?,?,?,?,?)";//username, password, 
            pst = StaticConnection.conn.prepareStatement(addConnE);
            pst.setInt(1, newKey);
            java.sql.Date sqlDate = new java.sql.Date(start.getTime());
            pst.setDate(2, sqlDate);
            sqlDate = new java.sql.Date(end.getTime());
            pst.setDate(3, sqlDate);
            pst.setString(4, occu);
            pst.setInt(5, locationID);
            pst.execute();
                
            String addCon = "INSERT INTO connections VALUES (?,?), VALUES (?,?)";
            pst = StaticConnection.conn.prepareStatement(addCon);
            pst.setInt(1, userID);
            pst.setInt(2, newKey);
            pst.setInt(3, newKey);
            pst.setInt(4, userID);
            pst.execute();
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }		 
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception ex) {}
                pst = null;
            }
        }
        return true;
    }
    //add contacts with an account. Tested, works.
    public static boolean addContact(int userID, int contactID) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        try {
            String addCon = "INSERT INTO connections VALUES (?,?), (?,?)";
            pst = StaticConnection.conn.prepareStatement(addCon);
            pst.setInt(1, userID);
            pst.setInt(2, contactID);
            pst.setInt(3, contactID);
            pst.setInt(4, userID);
            pst.execute();
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
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
        return true;
    }
    //remove contact
    public static boolean removeContact(int userID, int contactID) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        try {
            String addCon = "DELETE FROM connections WHERE userID=? AND userConnection=?";
            pst = StaticConnection.conn.prepareStatement(addCon);
            pst.setInt(1, userID);
            pst.setInt(2, contactID);
            pst.execute();
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
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
        return true;
    }
    //Retrieving information based on search criteria. Used to display information to user that allows the user to add that person as a contact. Tested, works.
    public static ArrayList<User> searchUser (String fname, String lname) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<User> results = new ArrayList();
        try {
            String search = "SELECT userID FROM userTable WHERE fname LIKE '%'||?||'%' AND lname LIKE '%'||?||'%'";
            pst = StaticConnection.conn.prepareStatement(search);
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.execute();
            rs = pst.getResultSet();
            while(rs.next()){
                User found = new User(rs.getInt(1));
                results.add(found);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
        return results;
    }
    
    /*Business*/
    //pulls information for businesses. Untested.
    public static void getBusiness(Business currentBus) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String getBus = "SELECT industry,founded,website,summary " +
                "FROM business WHERE business.businessName=?";
            pst = StaticConnection.conn.prepareStatement(getBus);
            pst.setString(0, currentBus.getName());
            pst.execute();
            rs = pst.getResultSet();
            rs.next();
            currentBus.setIndustry(rs.getString(1));
            currentBus.setFounded(rs.getDate(2));
            currentBus.setSummary(rs.getString(3));
            currentBus.setWebsite(rs.getString(4));
            
            String getLoc = "SELECT locationID,street,city,state,zip,lat,lon " +
                "FROM businessLocations WHERE businessName=?";
            pst = StaticConnection.conn.prepareStatement(getLoc);
            pst.setString(1, currentBus.getName());
            pst.execute();
            rs = pst.getResultSet();
            while(rs.next()) {
                currentBus.setLocations(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getFloat(6),rs.getFloat(7));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
    }
    //find or add a business and it's location. Untested;
    protected static int getLocationID(String busName, String city, String street, String state, int zip, float lat, float lon) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        int locationID=0;
        try {
            //check if business exist
            String checkExistBus = "SELECT businessName FROM business WHERE businessName=?";
            pst = StaticConnection.conn.prepareStatement(checkExistBus);
            pst.setString(1, busName);
            pst.execute();
            rs = pst.getResultSet();
            if(!rs.next()) {
                //add business (name only) if does not exist
                String addBus = "INSERT INTO business (businessName) VALUES (?)";
                pst = StaticConnection.conn.prepareStatement(addBus);
                pst.setString(1, busName);
                pst.execute();
            }
            
            //checks if business location exist
            String checkBusLocation = "SELECT locationID FROM businessLocations "
                    + "WHERE businessName=? AND city=? AND street=? AND state=? AND zip=?";
            pst = StaticConnection.conn.prepareStatement(checkBusLocation);
            pst.setString(1, busName);
            pst.setString(2, city);
            pst.setString(3, street);
            pst.setString(4, state);
            pst.setInt(5, zip);
            pst.execute();
            rs = pst.getResultSet();
            if(!rs.next()) {
                //add busines location if does not exist
                String addBus = "INSERT INTO businessLocations (businessName, city, street, state, zip,lat,lon) VALUES (?,?,?,?,?,?,?)";
                pst = StaticConnection.conn.prepareStatement(addBus, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, busName);
                pst.setString(2, city);
                pst.setString(3, street);
                pst.setString(4, state);
                pst.setInt(5, zip);
                pst.setFloat(6, lat);
                pst.setFloat(7, lon);
                pst.execute();
                rs = pst.getGeneratedKeys();
                if(rs.next())
                    locationID=rs.getInt(1);
            }
            else {
                locationID=rs.getInt("locationID");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
        return locationID;
    }
    /**
     * Updates the information located in the database.
     * @param n
     * @param i
     * @param w
     * @param s
     * @param f
     * @return 
     */
    protected static boolean updateBusiness(String n, String i, String w, String s, Date f) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        try {
            String upBus = "UPDATE business SET businessName=?,industry=?,website=?,summary=?,founded=? "
                    + "WHERE businessName=?";
            pst = StaticConnection.conn.prepareStatement(upBus);
            pst.setString(1, n);
            pst.setString(2, i);
            pst.setString(3, w);
            pst.setString(4, s);
            pst.setDate(5, (new java.sql.Date(f.getTime())));
            pst.setString(6, n);
            pst.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
        return true;
    }
    /**
     * Adds a new business into the database.
     * @param bn
     * @param i
     * @param f
     * @param w
     * @param s
     * @return 
     */
    public static int addNewBusiness(String bn, String i, Date f, String w, String s) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int success=0;
        try {
            String checkExistBus = "SELECT businessName FROM business WHERE businessName=?";
            pst = StaticConnection.conn.prepareStatement(checkExistBus);
            pst.setString(1, bn);
            pst.execute();
            rs = pst.getResultSet();
            if(!rs.next()) {
                String addBus = "INSERT INTO business VALUES (?,?,?,?,?)";
                pst = StaticConnection.conn.prepareStatement(addBus);
                pst.setString(1, bn);
                pst.setString(2, i);
                pst.setDate(3, (new java.sql.Date(f.getTime())));
                pst.setString(4, w);
                pst.setString(5, s);
                pst.execute();
            }
            else 
                success = -1;
        } catch (Exception ex) {
            success=-2;
            System.out.println(ex.getMessage());
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
        return success;
    }
    /**
     * Updates location changes into the database.
     * @param locID
     * @param str
     * @param c
     * @param st
     * @param z
     * @param lat
     * @param lon
     * @return 
     */
    protected static boolean updateBusinessLocation(int locID, String str, String c, String st, int z, float lat, float lon) {
        if(!StaticConnection.checkConnection())
            StaticConnection.initializeConnection();
        PreparedStatement pst = null;
        boolean success = true;
        try {
            
        } catch (Exception ex) {
            success = false;
            System.out.println(ex.getMessage());
        }
        finally {
            if(pst!=null) {
                try {
                    pst.close();
                }
                catch (Exception e) {}
            }
        }
        return success;
    }
    
    /*
    template:
    if(!StaticConnection.checkConnection())
        StaticConnection.initializeConnection();
    PreparedStatement pst = null;
    ResultSet rs = null;
    try {

    } catch (Exception ex) {

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
    */
}