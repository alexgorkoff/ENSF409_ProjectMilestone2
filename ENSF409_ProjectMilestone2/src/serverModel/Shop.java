package serverModel;
import java.util.ArrayList;

/**
 * The shop class that handles all backend operations regarding 
 * the inventory and the suppliers.
 * @author Matteo Messana, Alexander Gorkoff, Usman Farooq
 * @version 1.0
 * @since April 4th, 2019
 */
public class Shop {
	/**
	 * Inventory object that stores the item list.
	 */
	private Inventory theInventory;
	
	/**
	 * Supplier ArrayList that contains a list of all suppliers.
	 */
	private ArrayList <Supplier> supplierList;
	
	/**
	 * Constructor for the shop
	 * @param inventory: receives an inventory object.
	 * @param suppliers: receives  a supplier object.
	 */
	public Shop (Inventory inventory, ArrayList <Supplier> suppliers) {
		
		theInventory = inventory;
		supplierList = suppliers;
		
	}
	
	/**
	 * getter for inventory
	 * @return theInventory: returns an inventory object.
	 */
	public Inventory getTheInventory () {
		return theInventory;
	}
	
	/**
	 * setter for inventory
	 * @param inventory: sets the inventory parameter.
	 */
	public void setTheInventory (Inventory inventory) {
		theInventory = inventory;
	}
	
	/**
	 * getter for SupplierList
	 * @return supplierList: returns a supplier list.
	 */
	public ArrayList<Supplier> getSupplierList (){
		return supplierList;
	}
	
	/**
	 * setter for supplierList
	 * @param suppliers: receives a list of suppliers.
	 */
	public void setSupplierList (ArrayList <Supplier> suppliers){
		supplierList = suppliers;
	}
	
	/**
	 * Lists all items in the inventory
	 * @param customerSockets: uses a SocketPack to 
	 * send info between client and server
	 */
	public void listAllItems(SocketPack customerSockets) {
		
		String item = theInventory.toString();
		//for(String s: item.split("\n")) {
		customerSockets.sendString(item);	
		//}
		
	}
	
	/**
	 * Decreases the item quantity
	 * @param name: item name
	 * @return message: notify whether the item has been decreased or not.
	 */
	public String decreaseItem (String name) {
		if (theInventory.manageItem(name) == null)
			return "Couldn't decrease item quantity!";
		else
			return "Item quantity was decreased!";
	}

	/**
	 * Lists all suppliers
	 * @param customerSockets: uses a SocketPack to 
	 * send info between client and server.
	 */
	public void listAllSuppliers(SocketPack customerSockets) {
		// TODO Auto-generated method stub
		for (Supplier s: supplierList) {
			customerSockets.sendString(s.toString());
		}
		
	}

	
	/**
	 * searches for and gets an item specified by name provided, 
	 * if it exists in the list
	 * @param name: name of item
	 * @return String: either the found item or a message 
	 * stating the item could not be found
	 */
	public String getItem(String name) {
		// TODO Auto-generated method stub
		Item theItem = theInventory.searchForItem(name);
		if (theItem == null)
		     return "Item " + name + " could not be found!";
		else
			 return outputItem (theItem);
			
	}

	/**
	 * searches for and gets an item specified by id provided, 
	 * if it exists in the list
	 * @param id: id of item
	 * @return String: either the found item or a message 
	 * stating the item could not be found
	 */
	public String getItem(int id) {
		// TODO Auto-generated method stub
		Item theItem = theInventory.searchForItem(id);
		if (theItem == null)
		     return "Item number " + id + " could not be found!";
		else
			return outputItem (theItem);
			 
		
	}
	
	/**
	 * Displays item information
	 * @param theItem: the item sent to the function
	 * @return String: the item information
	 */
	private String outputItem (Item theItem){
		
		String theOutput = 
				" -- Tool ID: " + theItem.getItemId() + " -- Tool Name: " + theItem.getItemName() 
				+ " -- Quantity: " + theItem.getItemQuantity() + " -- Price: " + theItem.getItemPrice() + " --";
		
		return "The item information is as follows: " + theOutput;
	}

	/**
	 * Gets the quantity of specified item by name.
	 * @param name: name of item
	 * @return String: item quantity
	 */
	public String getItemQuantity(String name) {
		// TODO Auto-generated method stub
		int quantity = theInventory.getItemQuantity(name);
		
		if (quantity < 0)
		    return "\nItem " + name + " could not be found!";
		else
			return "\nThe quantity of Item " + name + " is: " + quantity;
	}

	/**
	 * Prints the order for the shop
	 * @return String: item order.
	 */
	public String printOrder() {
		
		return theInventory.printOrder() + "\n";
	}

	

}
