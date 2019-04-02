package serverModel;

/**
 * Class Item
 * 
 * @author Dr. Mohamed Moshirpour (Retrieved Sunday March 31st, 2019)
 * Edited by: 
 * @author Muhammad Farooq (UCID: 30016276)
 * @author Alexander Gorkoff (UCID: 30020570)
 * @author Matteo Messana (UCIDL 30020933)
 *
 */

public class Item {
	
	/**
	 * The ID number of the item
	 */
	private int itemId;
	
	/**
	 * The name of the item
	 */
	private String itemName;
	
	/**
	 * The quantity of the item in stock
	 */
	private int itemQuantity;
	
	/**
	 * The price of the item
	 */
	private double itemPrice;
	
	/**
	 * Status indicating whether an item has been ordered during that session or not
	 */
	private boolean alreadyOrdered;
	
	/**
	 * The supplier of the item
	 */
	private Supplier theSupplier;
	
	/**
	 * Default quantity to be ordered if an Item's stock decreases below MINIMUMUMBER
	 */
	private static final int ORDERQUANTITY = 40;
	
	/**
	 * Minimum stock of an item allowed before an order is placed for that item
	 */
	private static final int MINIMUMUMBER = 20; 	
	
	/**
	 * Constructor of class Item
	 * 
	 * @param id : the ID number of the item
	 * @param name : the name of the Item
	 * @param quanitiy : the quantity of the Item in stock
	 * @param price : the price of the item
	 * @param sup : the supplier of the item
	 */
	public Item (int id, String name, int quanitiy, double price, Supplier sup) {
		
		itemId = id;
		itemName = name;
		itemQuantity = quanitiy;
		itemPrice = price;
		sup = theSupplier; 
		setAlreadyOrdered(false);
	}
	
	/**
	 * Reduces the quantity of an item's stock, if it is less than zero
	 * @return true if the item's quantity has been decreased, false if it has not
	 */
	public boolean decreaseItemQuantity () {
		if (itemQuantity > 0) {
			itemQuantity--;
		    return true;	
		}
		else
			return false;
			
	}
	
	/**
	 * Generates an OrderLine 
	 * @return
	 */
	public OrderLine placeOrder (){
		OrderLine ol;
		if (getItemQuantity() < MINIMUMUMBER && alreadyOrdered == false){
			ol = new OrderLine (this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
	    return null;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public void setTheSupplier (Supplier sup) {
		theSupplier = sup;
	}
	public Supplier getTheSupplier () {
		return theSupplier;
	}
	
	public String toString () {
		return "Item ID: " + itemId + ", Item Name: " + itemName + ", Item Quantity: " + 
	           itemQuantity + "\n";
	}

	public boolean isAlreadyOrdered() {
		return alreadyOrdered;
	}

	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

}
