//reserved for package import

import java.sql.*;


public class DBconnection {
	
	private static Connection setupConnection() {
		Connection conn = null;
		try {
		conn = DriverManager.getConnection
				("jdbc:mysql://csc440.cwalelqxfs6f.us-east-1.rds.amazonaws.com:3306/csc440", "admin", "ekucsc440project");
		return conn;
		} catch (Exception e) {System.out.println(e);}
		
		return null;
	}
	//checks if user has registered an account. If so, will allow the sure to log in (true)
	private boolean checkLoginCred(String user, String password) {
		boolean valid = false;//returned value
		Connection conn = setupConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String login = "SELECT * FROM userTable WHERE userName=? AND password=?";
			pst = conn.prepareStatement(login);
			pst.setString(1, user);
			pst.setString(2, password);
			
			//checks if a result is found for user name and password
			rs = pst.getResultSet();
			if(rs.next()) {
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
				rs = null;
			}
			if(pst!=null) {
				try {
					pst.close();
				}
				catch (Exception e) {}
				pst = null;
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {}
				conn = null;
			}
		}
		return valid;
	}
	
	private int createAccount(String user, String pass, String fname, String lname, String city, String street, String state, int zip) {
		int valid = 0;//returned value, 0 means good
		Connection conn = setupConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String checkExistUN = "SELECT username FROM userTable WHERE username=?";
			pst = conn.prepareStatement(checkExistUN);
			pst.setString(1, user);
			rs = pst.getResultSet();
			if(rs.next()) {
				return 1;//error: username exist
			}
			pst.close();
			
			String accountSetup = "INSERT INTO userTable (username, pword, fname, lname, city, street, state, zip) VALUES (?,?,?,?,?,?,?,?)";//username, password, 
			pst = conn.prepareStatement(accountSetup);
			pst.setString(1, user);
			pst.setString(2, pass);
			pst.setString(3, fname);
			pst.setString(4, lname);
			pst.setString(5, city);
			pst.setString(6, street);
			pst.setString(7, state);
			pst.setInt(8, zip);
			
			pst.executeQuery();
		}
		catch (Exception e) {
			System.out.println(e);
			valid = 2;//error: invalid information
		}
		//close out everything
		finally {
			if(pst!=null) {
				try {
					pst.close();
				}
				catch (Exception e) {}
				pst = null;
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {}
				conn = null;
			}
		}
		return valid;
	}
	//for geocoding, returns location by username
	private String returnSearchLocationUser(String username) {
		 Connection conn = setupConnection();
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 String feedback = "";
		 try {
			 String search = "SELECT Address, City, State, Zip FROM userTable WHERE username = ?";
			 pst = conn.prepareStatement(search);
			 pst.setString(1, username);
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
					rs = null;
				}
				if(pst!=null) {
					try {
						pst.close();
					}
					catch (Exception e) {}
					pst = null;
				}
				if(conn!=null) {
					try {
						conn.close();
					} catch (Exception e) {}
					conn = null;
				}
		 }
		return feedback;
	}
	//get results by name, can possibly be multiple, so using array to return all possible results
	private String[] returnSearchLocationUser(String first, String last) {
		 Connection conn = setupConnection();
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 String feedback[];
		 try {
			 String search = "SELECT Address, City, State, Zip FROM userTable WHERE fname = ? AND lname = ?";
			 pst = conn.prepareStatement(search);
			 pst.setString(1, first);
			 pst.setString(2, last);
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
					pst = null;
				}
				if(conn!=null) {
					try {
						conn.close();
					} catch (Exception e) {}
					conn = null;
				}
		 }
		 return feedback;
	}
}
