package serverModel;

import java.sql.*;
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
	private ResultSet supplierResult;
	private ResultSet itemResult;
	
	/**
	 * Constructor for Database. 
	 * Initializes all member variables for the Database.
	 */
	public Database()
	{
		try {
			// Get a connection to database
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop", "ToolShopUser", "ToolShop2019");
			
			
			
			// Create a statement
			myStatement = myConnection.createStatement();
			
			// Execute SQL query
			supplierResult = myStatement.executeQuery("select * from suppliers"); 
			itemResult = myStatement.executeQuery("select * from items");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function that processes the data in both the tables.
	 * @throws SQLException
	 */
	public void processResult() throws SQLException
	{
		while(supplierResult.next())
		{
			System.out.println(supplierResult.getInt("SupplierID") + ", " + supplierResult.getString("SupplierName") + ", " + supplierResult.getString("SupplierAddress") + ", " + supplierResult.getString("SupplierContact"));
		}
	}
	
}
