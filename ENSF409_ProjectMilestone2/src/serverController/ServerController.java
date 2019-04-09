package serverController;
import java.awt.event.ActionEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import interfaces.*;
import serverModel.*;

/**
 * Server side controller, directs client 
 * requests to perform any backend actions.
 * 
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.0
 * @since April 4th, 2019
 *
 */

public class ServerController implements Runnable, SessionID {

	/**
	 * List to maintain record of all suppliers in the inventory system
	 */
	private ArrayList<Supplier> suppliers;
	
	/**
	 * Object used to maintain the inventory of the store
	 */
	private Inventory theInventory;
	
	/**
	 * Reference to the store associated with the Inventory and Suppliers
	 */
	private Shop theShop;
	
	/**
	 * Object containing streams to facilitate client/server communication
	 */
	private SocketPack customerSockets;
	
	/**
	 * Reference to the database from which Inventory and Supplier information is retrieved and updated
	 */
	private Database allInfo;
	
	/**
	 * Indicates the state of the program as directed by the client side of the application (GUI Action Listeners)
	 * Data member will direct client side to retrieve certain information from user for further use in the back end
	 */
	private String sessionID;

	/**
	 * Constructor of type ServerController
	 * @param theSocket : input socket from the server, used to derive input/output streams for client/server communication
	 */
	public ServerController(Socket theSocket) {
		allInfo = new Database();
		try {
			suppliers = processSupplierResult();
			theInventory = processItemResult();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		theShop = new Shop(theInventory, suppliers);
		
		customerSockets = new SocketPack(theSocket);
		
	}

	/**
	 * Creates the supplier list from the database
	 * @return ArrayList<Supplier>: returns the list of suppliers 
	 * @throws SQLException: throws any SQL exceptions
	 */
	public ArrayList<Supplier> processSupplierResult() throws SQLException
	{
		allInfo.setMyStatement(allInfo.getMyConnection().createStatement());
		ResultSet results = allInfo.getMyStatement().executeQuery("select * from suppliers"); 
		ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
		while(results.next())
		{
			supplierList.add(new Supplier(results.getInt("SupplierID"),results.getString("SupplierName"),results.getString("SupplierAddress"),results.getString("SupplierContact")));
		}
		return supplierList;
	}
	
	/**
	 * Creates the inventory list from the database
	 * @return Inventory: returns an object of type inventory
	 * @throws SQLException: throws any SQL exceptions
	 */
	public Inventory processItemResult() throws SQLException
	{
		allInfo.setMyStatement(allInfo.getMyConnection().createStatement());
		ResultSet results = allInfo.getMyStatement().executeQuery("select * from items"); 
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		while(results.next())
		{
			itemList.add(new Item(results.getInt("ItemID"),results.getString("ItemName"),results.getInt("ItemQuantity"),results.getDouble("ItemPrice"),findSupplier(results.getInt("SupplierID"))));
		}
		Inventory inv = new Inventory(itemList);
		allInfo.setTheInventory(inv);
		return inv;
	}
	
	

	/**
	 * Searches for and Retrieves a supplier, as indicated by the supplierID
	 * @param supplierId : the ID number associated with a particular supplier
	 * @return theSupplier : theSupplier (if found) pertaining to an ID number
	 */
	private Supplier findSupplier(int supplierId) {
		Supplier theSupplier = null;
		for (Supplier s : suppliers) {
			if (s.getSupId() == supplierId) {
				theSupplier = s;
				break;
			}

		}
		return theSupplier;
	}

	/**
	 * Receives directives from the client side (GUI Action Listeners)
	 * Sets sessionID based on the above input
	 * Contacts back end methods in order to fulfill the request of the user on the client side
	 * @throws IOException
	 */
	public void menu() throws IOException {
			
			while (true) {

				String choice = customerSockets.getSocketIn().readLine();

				switch (choice) {

				case "1":
					sessionID = LIST_ALL_ITEMS;
					listAllItems();
					break;
				case "2":
					sessionID = SEARCH_ITEM_NAME;
					searchForItemByName();
					break;
				case "3":
					sessionID = SEARCH_ITEM_ID;
					searchForItemById();
					break;
				case "4":
					sessionID = GET_ITEM_QUANTITY;
					checkItemQuantity();
					break;
				case "5":
					sessionID = DECREASE_QUANTITY;
					decreaseItem();
					allInfo.writeToItemTable();
					break;
				case "6":
					sessionID = PRINT_ORDER;
					printOrder();
					break;
				case "7":
					sessionID = QUIT;
					customerSockets.sendString(sessionID);
					return;
				default:
					customerSockets.sendString("\nInvalid selection Please try again!");
					break;
				}
			}

	}

	/**
	 * Outputs the Order to the client side
	 */
	private void printOrder() {
		customerSockets.sendString(theShop.printOrder());
	}

	/**
	 * Reduces the quantity of an item as indicated by the user (searched by name)
	 */
	private void decreaseItem() {

		String name  = getItemName();
		String itemReduce = theShop.decreaseItem(name);
		customerSockets.sendString(itemReduce);
		
	}

	/**
	 * Retrieves the item quantity as indicated by the user (searched by name)
	 */
	private synchronized void checkItemQuantity() {
		
		String itemName = getItemName();
		
		String itemQuantity = theShop.getItemQuantity(itemName);
		
		customerSockets.sendString(itemQuantity);
		
	}

	/**
	 * Communicates the sessionID to the client side
	 * Expects a response from the client side (a user input of an item name)
	 * @return clientResponse : the name of the item to be located
	 */
	private String getItemName() {
		
		String clientResponse = "";
		customerSockets.sendString(sessionID);
		try {
			 clientResponse = customerSockets.getSocketIn().readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clientResponse;
	}

	/**
	 * Communicates the sessionID to the client side
	 * Expects a response from the client side (a user input of an item ID)
	 * @return itemID : the ID of the item to be located
	 */
	private int getItemId() {
		
		int itemID = 0;

		String clientResponse = "";
		customerSockets.sendString(sessionID);
		try {
			 clientResponse = customerSockets.getSocketIn().readLine();
			 itemID = Integer.parseInt(clientResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemID;
		}

	/**
	 * Communicates a sessionID to the client side
	 * Expects a response from the client side (an item ID, as indicated by user)
	 * Returns results from a search of whether or not an item is located in inventory
	 */
	private void searchForItemById() {

		int itemID = 0;
		customerSockets.sendString(sessionID);
		try {
			 itemID = Integer.parseInt(customerSockets.getSocketIn().readLine());
			 customerSockets.sendString(theShop.getItem(itemID));

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Communicates a sessionID to the client side
	 * Expects a response from the client side (an item name, as indicated by user)
	 * Returns results from a search of whether or not an item is located in inventory
	 */
	private void searchForItemByName() {

		String name = "";
		customerSockets.sendString(sessionID);
		try {
			 name = customerSockets.getSocketIn().readLine();
			 customerSockets.sendString(theShop.getItem(name));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Communicates a sessionID to the client side
	 * Invokes a method to output the items in the inventory to the client side
	 */
	public void listAllItems() {
		customerSockets.sendString(sessionID);
		theShop.listAllItems(customerSockets);
	}

	/**
	 * Invokes the menu() function after the client is deployed to the server
	 */
	@Override
	public void run() {
		
		try {
			menu();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
