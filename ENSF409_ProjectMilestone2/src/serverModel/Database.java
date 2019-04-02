package serverModel;

import java.sql.*;

public class Database {
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet supplierResult;
	private ResultSet itemResult;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void processResult() throws SQLException
	{
		while(supplierResult.next())
		{
			System.out.println(supplierResult.getInt("SupplierID") + ", " + supplierResult.getString("SupplierName") + ", " + supplierResult.getString("SupplierAddress") + ", " + supplierResult.getString("SupplierContact"));
		}
	}
	
	public static void main(String args[]) throws SQLException
	{
		Database D = new Database();
		D.processResult();
	}
}
