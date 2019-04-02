package serverModel;
import java.util.ArrayList;

/**
 * Class Inventory 
 * 
 * @author Dr. Mohamed Moshirpour (Retrieved Sunday March 31st, 2019)
 * Edited by: 
 * @author Muhammad Farooq (UCID: 30016276)
 * @author Alexander Gorkoff (UCID: 30020570)
 * @author Matteo Messana (UCIDL 30020933)
 *
 */
public class Inventory {
	
	/**
	 * List containing all the items in the inventory
	 */
	private ArrayList <Item> itemList;
	
	/**
	 * Order generated during a program session
	 */
	private Order myOrder;
	
	/**
	 * Constructor for class inventory
	 * @param itemList : An ArrayList containing all items in the inventory
	 */
	public Inventory (ArrayList <Item> itemList) {
		this.itemList = itemList;
		myOrder = new Order ();
	}

	/**
	 * Returns a reference to the inventory Item List
	 * @return itemList: an ArrayList containing all items in the inventory
	 */
	public ArrayList <Item> getItemList() {
		return itemList;
	}

	/**
	 * Sets the inventory Item List
	 * @param itemList : an ArrayList containing teh inventory for the store
	 */
	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}
	
	/**
	 * Invokes method to decrease item (if prompted by user)
	 * If item quantity decreases below 40, automatically places an order for said item
	 * @param name : The name of the item inputed by the user
	 * @return theItem : An instance of the item (if the quantity has decreased)
	 */
	public Item manageItem (String name){
		Item theItem = decreaseItem (name);
		
		if (theItem != null){
			placeOrder (theItem);
		}
		return theItem;
	}
	
	/**
	 * Invokes method to place an order for a given Item
	 * If an item is returned, an OrderLine for that Item is created and added to the day's Order
	 * @param theItem : The item to be ordered
	 */
	public void placeOrder (Item theItem){
		OrderLine ol = theItem.placeOrder();
		if (ol !=null){
			myOrder.addOrderLine(ol);
		}
	}
	
	/**
	 * Searches for an Item as indicated by the user
	 * Attempts to decrease the quantity of the item (if applicable)
	 * @param name : The name of the item to be searched
	 * @return theItem (if the item was found and quantity was decreased) or null (if the item was not located or could not be decreased
	 */
	private Item decreaseItem (String name) {
		
		Item theItem = searchForItem (name);
		
		if (theItem == null)
			return null;
		
		if (theItem.decreaseItemQuantity() == true){
			
			return theItem;
		}
		return null;
		
	}
	
	/**
	 * Returns the quantity of an Item as indicated by the user
	 * @param name : The name of the item to be searched
	 * @return -1 if the item could not be located or the quantity of the item that has been located
	 */
	public int getItemQuantity (String name){
		Item theItem = searchForItem (name);
		if (theItem == null)
			return -1;
		else
			return theItem.getItemQuantity();
	}
	
	/**
	 * Search for an Item in the inventory by name
	 * @param name : The name of the item to be searched
	 * @return theItem (if located in search) or null (if not located in search)
	 */
	public Item searchForItem (String name) {
		for (Item i: itemList) {
			if (i.getItemName().equals(name))
				return i;
		}
		return null;
	}
	
	public String toString () {
		String str = "";
		for (Item i: itemList) {
			str += i;
		}
		return str;
	}

	/**
	 * Search for an Item in the inventory by ID
	 * @param id : The ID of the item to be searched
	 * @return the Item (if located in the search) or null (if not located in the search)
	 */
	public Item searchForItem(int id) {
		// TODO Auto-generated method stub
		for (Item i: itemList) {
			if (i.getItemId() == id)
				return i;
		}
		return null;
	}

	/**
	 * Prints the Order for the day
	 * @return the Order (as a String)
	 */
	public String printOrder() {
		// TODO Auto-generated method stub
		return myOrder.toString();
	}

}
