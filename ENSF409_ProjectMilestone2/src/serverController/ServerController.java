package serverController;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import serverModel.*;

public class ServerController implements Runnable {

	private ArrayList<Supplier> suppliers;
	private Inventory theInventory;
	private Shop theShop;
	
	private SocketPack customerSockets;

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

	private void printMenuChoices() {
		customerSockets.sendStringPrintln("Please choose from one of the following options: ");
		customerSockets.sendStringPrintln("1. List all tools in the inventory.");
		customerSockets.sendStringPrintln("2. Search for tool by tool name.");
		customerSockets.sendStringPrintln("3. Search for tool by tool id.");
		customerSockets.sendStringPrintln("4. Check item quantity.");
		customerSockets.sendStringPrintln("5. Decrease item quantity.");
		customerSockets.sendStringPrintln("6. Print today's order.");
		customerSockets.sendStringPrintln("7. Quit.");
		customerSockets.sendStringPrintln("");
		customerSockets.sendStringPrintln("Please enter your selection: \0");
	}

	public void menu() throws NumberFormatException, IOException {

			int choice;
			
			while (true) {

				printMenuChoices();
				
				try {
					
					choice = Integer.parseInt(customerSockets.getSocketIn().readLine());
					
				} catch(NumberFormatException nfe) {
					customerSockets.sendStringPrintln("\n*** Invalid input. Please enter an Integer. ***\n");
					continue;
				} catch(IOException ioe) {
					customerSockets.sendStringPrintln("\n*** IOException in menu() ***\n");
					continue;
				}

				switch (choice) {

				case 1:
					theShop.listAllItems(customerSockets);
					break;
				case 2:
					searchForItemByName();
					break;
				case 3:
					searchForItemById();
					break;
				case 4:
					checkItemQuantity();
					break;
				case 5:
					decreaseItem();
					break;
				case 6:
					printOrder();
					break;
				case 7:
					customerSockets.sendStringPrintln("\nGood Bye!");
					customerSockets.sendStringPrintln("QUIT");
					return;
				default:
					customerSockets.sendStringPrintln("\nInvalid selection Please try again!");
					break;
				}
			}

	}

	private void printOrder() {
		customerSockets.sendStringPrintln(theShop.printOrder());
		//Need to pass along customer sockets
	}

	private void decreaseItem() {
		
		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.decreaseItem(name));
		
	}

	private void checkItemQuantity() {
		
		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.getItemQuantity(name));
		
	}

	private String getItemName() {
		
		try {
			customerSockets.sendStringPrintln("Please enter the name of the item: \0");
			String line = customerSockets.getSocketIn().readLine();
			return line;
		} catch(IOException ioe) {
			customerSockets.sendStringPrintln("\n*** IOException in getItemName(). ***\n");
			return null;
		}

	}

	private int getItemId() {
		
		int itemID;
		
		while(true) {
			try {
				customerSockets.sendStringPrintln("Please enter the ID number of the item: \0");
				itemID = Integer.parseInt(customerSockets.getSocketIn().readLine());
				return itemID;
			} catch(NumberFormatException nfe) {
				customerSockets.sendStringPrintln("\n*** Invalid input. Please enter an integer. ***\n");
				continue;
			} catch(IOException ioe) {
				customerSockets.sendStringPrintln("\n*** IOException in getItemID. ***\n");
				continue;
			}
		}
	}

	private void searchForItemById() {

		int id;
	
		id = getItemId();
		customerSockets.sendStringPrintln(theShop.getItem(id));
		
	}

	private void searchForItemByName() {

		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.getItem(name));

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
