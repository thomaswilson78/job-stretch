package dbconn;

import java.util.ArrayList;

public class ConnList {
	DBconnection db = new DBconnection();
	ArrayList<Connections> conList = new ArrayList<Connections>();
	int userID;//userID for the person logged in
	int[] connectionIDs;
	
	ConnList(int uID) {
		userID = uID;
	}
	
	public void addConnections(int userID) {
		connectionIDs = db.getConnections(userID);
	}
	
	//should be called when the user is able to login into the program
	public void addConnectionsToList() {
		for(int i = 0; i<connectionIDs.length;i++) {
			Connections person = new Connections();
			person = db.setConnectionValues(userID);
			if(person!=null) {
				conList.add(person);
			}
		}
	}
	
	//returns list of all connections
	public ArrayList<Connections> returnList() {
		return conList;
	}
}