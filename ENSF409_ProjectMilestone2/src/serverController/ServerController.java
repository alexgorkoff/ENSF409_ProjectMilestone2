package serverController;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import serverModel.*;

public class ServerController implements Runnable, SessionID {

	private ArrayList<Supplier> suppliers;
	private Inventory theInventory;
	private Shop theShop;
	private SocketPack customerSockets;
	
	private String sessionID;

	public ServerController(Socket theSocket) {
		
		suppliers = new ArrayList<Supplier>();
		readSuppliers();
		theInventory = new Inventory(readItems());
		theShop = new Shop(theInventory, suppliers);
		
		customerSockets = new SocketPack(theSocket);
		
	}

	private ArrayList<Item> readItems() {

		ArrayList<Item> items = new ArrayList<Item>();

		try {
			FileReader fr = new FileReader("items.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				int supplierId = Integer.parseInt(temp[4]);

				Supplier theSupplier = findSupplier(supplierId);
				if (theSupplier != null) {
					Item myItem = new Item(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
							Double.parseDouble(temp[3]), theSupplier);
					items.add(myItem);
					theSupplier.getItemList().add(myItem);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return items;
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

	private void readSuppliers() {

		try {
			FileReader fr = new FileReader("suppliers.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				suppliers.add(new Supplier(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

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
