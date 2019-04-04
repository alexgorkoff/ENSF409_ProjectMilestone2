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
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.0
 * @since April 4th, 2019
 *
 */

public class ServerController implements Runnable, SessionID {

	private ArrayList<Supplier> suppliers;
	private Inventory theInventory;
	private Shop theShop;
	private SocketPack customerSockets;
	private Database allInfo;
	
	private String sessionID;

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
	
	

	/*
	 * Finds the supplier which matches the supplierID
	 * @param supplierId
	 * @return theSupplier
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
					customerSockets.sendString("QUIT");
					return;
				default:
					customerSockets.sendString("\nInvalid selection Please try again!");
					break;
				}
			}

	}

	private void printOrder() {
		customerSockets.sendString(theShop.printOrder());
		//Need to pass along customer sockets
	}

	private void decreaseItem() {

		String name  = getItemName();
		String itemReduce = theShop.decreaseItem(name);
		customerSockets.sendString(itemReduce);
//		customerSockets.sendStringPrintln(theShop.decreaseItem(name));
//		JOptionPane.showMessageDialog(null, theShop.decreaseItem(name));
		
	}

	private synchronized void checkItemQuantity() {
		
		String itemName = getItemName();
		
		String itemQuantity = theShop.getItemQuantity(itemName);
		
		customerSockets.sendString(itemQuantity);
//		customerSockets.sendStringPrintln(theShop.getItemQuantity(name));
//		JOptionPane.showMessageDialog(null, theShop.getItemQuantity(name));
		
	}

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

	private void searchForItemByName() {

		String name = "";
		customerSockets.sendString(sessionID);
		try {
			 name = customerSockets.getSocketIn().readLine();
			 customerSockets.sendString(theShop.getItem(name));

		} catch (IOException e) {
			e.printStackTrace();
		}
//		JOptionPane.showMessageDialog(null, theShop.getItem(name));
		//customerSockets.sendStringPrintln(theShop.getItem(name));

	}
	public void listAllItems() {
		customerSockets.sendString(sessionID);
		theShop.listAllItems(customerSockets);
	}

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
