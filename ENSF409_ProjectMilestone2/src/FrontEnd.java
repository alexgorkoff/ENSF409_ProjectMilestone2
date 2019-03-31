import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class FrontEnd implements Runnable {

	private ArrayList<Supplier> suppliers;
	private Inventory theInventory;
	private Shop theShop;
	private Scanner scan;
	
	private SocketPack customerSockets;

	FrontEnd(Socket theSocket) {
		suppliers = new ArrayList<Supplier>();
		readSuppliers();
		theInventory = new Inventory(readItems());
		theShop = new Shop(theInventory, suppliers);
		scan = new Scanner(System.in);
		
		customerSockets.setASocket(theSocket);
		
		try {
			customerSockets.setSocketIn(new BufferedReader(new InputStreamReader(theSocket.getInputStream())));
			customerSockets.setSocketOut(new PrintWriter(theSocket.getOutputStream()));
		} catch(Exception e) {
			System.err.println("Failed to initialize Front End");
		}
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
		customerSockets.sendStringPrintln("Please enter your selection: ");
	}

	public void menu() throws NumberFormatException, IOException {

		while (true) {

			printMenuChoices();

			//int choice = scan.nextInt();
			int choice = Integer.parseInt(customerSockets.getSocketIn().readLine() + "\0");
			//scan.nextLine();

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

	private void decreaseItem() throws IOException {
		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.decreaseItem(name));
	}

	private void checkItemQuantity() throws IOException {
		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.getItemQuantity(name));
	}

	private String getItemName() throws IOException {
		customerSockets.sendStringPrint("Please enter the name of the item: ");
		String line = customerSockets.getSocketIn().readLine() + "\0";
		return line;

	}

	private int getItemId() throws NumberFormatException, IOException {
		customerSockets.sendStringPrintln("Please enter the ID number of the item: ");
		
		int itemID = Integer.parseInt(customerSockets.getSocketIn().readLine() + "\0");
		
		return itemID;
	}

	private void searchForItemById() throws NumberFormatException, IOException {

		int id = getItemId();
		customerSockets.sendStringPrintln(theShop.getItem(id));

	}

	private void searchForItemByName() throws IOException {

		String name = getItemName();
		customerSockets.sendStringPrintln(theShop.getItem(name));

	}

	@Override
	public void run() {
		
		try {
			this.menu();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
