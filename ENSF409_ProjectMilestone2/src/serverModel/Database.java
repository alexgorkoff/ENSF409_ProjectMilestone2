package serverModel;

import java.sql.*;
import java.util.ArrayList;
/**
 * A class that configures the data from the database
 * and makes it usable for the rest of the program.
 * @author Matteo Messana, Alexander Gorkoff, Usman Farooq
 * @version 1.0
 * @since April 4th, 2019
 */

public class Database {
	/**
	 * Member variable for connection to the database
	 */
	private Connection myConnection;
	
	/**
	 * Member variable for statements made for the connection
	 */
	private Statement myStatement;
	/**
	 * Member variable for the inventory
	 */
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
	
	/**
	 * Updates all quantity of items in the database 
	 */
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
			e.printStackTrace();
		}
	}

	/**
	 * getter for my connection
	 * @return myConnection: returns connecter for the database 
	 */
	public Connection getMyConnection() {
		return myConnection;
	}
	
	/**
	 * getter for my statement
	 * @return myStatement: returns a statement to the database
	 */
	public Statement getMyStatement() {
		return myStatement;
	}

	/**
	 * setter for my statement 
	 * @param myStatement: sends a statement
	 */
	public void setMyStatement(Statement myStatement) {
		this.myStatement = myStatement;
	}
	
	/**
	 * getter for inventory
	 * @return theInventroy: returns an inventory object
	 */
	public Inventory getTheInventory() {
		return theInventory;
	}
	
	/**
	 * setter for inventory
	 * @param theInventory: sends an inventory object
	 */
	public void setTheInventory(Inventory theInventory) {
		this.theInventory = theInventory;
	}	
}
