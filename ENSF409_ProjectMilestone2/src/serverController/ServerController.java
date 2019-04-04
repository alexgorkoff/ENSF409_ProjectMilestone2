package serverController;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import serverModel.*;

public class ServerController implements Runnable {

	private ArrayList<Supplier> suppliers;
	private Inventory theInventory;
	private Shop theShop;
	private SocketPack customerSockets;
	
	private int actionPerformed;

	public ServerController(Socket theSocket) {
		
		suppliers = new ArrayList<Supplier>();
		readSuppliers();
		theInventory = new Inventory(readItems());
		theShop = new Shop(theInventory, suppliers);
		
		customerSockets = new SocketPack(theSocket);
		
		actionPerformed = 0;
		
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

	private void printMenuChoices() {
		customerSockets.sendString("Please choose from one of the following options: ");
		customerSockets.sendString("1. List all tools in the inventory.");
		customerSockets.sendString("2. Search for tool by tool name.");
		customerSockets.sendString("3. Search for tool by tool id.");
		customerSockets.sendString("4. Check item quantity.");
		customerSockets.sendString("5. Decrease item quantity.");
		customerSockets.sendString("6. Print today's order.");
		customerSockets.sendString("7. Quit.");
		customerSockets.sendString("");
		customerSockets.sendString("Please enter your selection: \0");
	}
	
//	public String readGUIInput() throws IOException {
//		 
//		StringBuffer input = null;
//		 
//		input = new StringBuffer(customerSockets.getSocketIn().readLine());
//		 
//		return input.toString();
//		 
//	}

	public void menu() throws IOException {

//			int choice;
			
			while (true) {

//				printMenuChoices();
//				
//				try {
//					
//					choice = Integer.parseInt(customerSockets.getSocketIn().readLine());
//					
//					
//				} catch(NumberFormatException nfe) {
//					customerSockets.sendStringPrintln("\n*** Invalid input. Please enter an Integer. ***\n");
//					continue;
//				} catch(IOException ioe) {
//					customerSockets.sendStringPrintln("\n*** IOException in menu() ***\n");
//					continue;
//				}
				
				String choice = customerSockets.getSocketIn().readLine();

				switch (choice) {

				case "1":
					theShop.listAllItems(customerSockets);
					break;
				case "2":
					searchForItemByName();
					break;
				case "3":
					searchForItemById();
					break;
				case "4":
					checkItemQuantity();
					break;
				case "5":
					decreaseItem();
					break;
				case "6":
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
//		customerSockets.sendStringPrintln(theShop.decreaseItem(name));
//		JOptionPane.showMessageDialog(null, theShop.decreaseItem(name));
		
	}

	private void checkItemQuantity() {
		
		String itemName = getItemName();
		
		customerSockets.sendString("OutputQuantity");
		
		String itemQuantity = theShop.getItemQuantity(itemName);
		
		customerSockets.sendString(itemQuantity);
//		customerSockets.sendStringPrintln(theShop.getItemQuantity(name));
//		JOptionPane.showMessageDialog(null, theShop.getItemQuantity(name));
		
	}

	private String getItemName() {
		
		String clientResponse = "";
		customerSockets.sendString("name");
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
		customerSockets.sendString("id");
		try {
			 clientResponse = customerSockets.getSocketIn().readLine();
			 itemID = Integer.parseInt(clientResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemID;
		}

	private void searchForItemById() {

		int id;
	
		id = getItemId();

		
	}

	private void searchForItemByName() {

		String name = getItemName();
//		JOptionPane.showMessageDialog(null, theShop.getItem(name));
		//customerSockets.sendStringPrintln(theShop.getItem(name));

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
