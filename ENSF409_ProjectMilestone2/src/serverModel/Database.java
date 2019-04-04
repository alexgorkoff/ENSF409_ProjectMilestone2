package serverModel;

import java.sql.*;
import java.util.ArrayList;
/**
 * A class that configures the data from the database
 * and makes it usable for the rest of the program.
 * @author Matteo Messana, Alexander Gorkoff, Usman Farooq
 *
 */

public class Database {
	/**
	 * Member variables for class Database
	 */
	private Connection myConnection;
	private Statement myStatement;
	private Inventory theInventory;
	
	/**
	 * Constructor for Database. 
	 * Initializes all member variables for the Database.
	 */	
	public Database()
	{
		try {
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop", "ToolShopUser", "ToolShop2019");			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToItemTable()
	{
		
		try {
			String query = "update items set ItemQuantity = ? where ItemID = ?";
			PreparedStatement preparedStmt = myConnection.prepareStatement(query);

			for(int i = 0; i < theInventory.getItemList().size();i++)
			{
				preparedStmt.setInt(1,theInventory.getItemList().get(i).getItemQuantity());
				preparedStmt.setInt(2,theInventory.getItemList().get(i).getItemId());
				preparedStmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getMyConnection() {
		return myConnection;
	}
	
	public Statement getMyStatement() {
		return myStatement;
	}

	public void setMyStatement(Statement myStatement) {
		this.myStatement = myStatement;
	}

	public Inventory getTheInventory() {
		return theInventory;
	}
	
	public void setTheInventory(Inventory theInventory) {
		this.theInventory = theInventory;
	}	
}
