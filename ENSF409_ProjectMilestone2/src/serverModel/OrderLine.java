package serverModel;

/**
 * Class OrderLine
 * 
 * @author Dr. Mohamed Moshirpour (Retrieved Sunday March 31st, 2019)
 * Edited by: 
 * @author Muhammad Farooq (UCID: 30016276)
 * @author Alexander Gorkoff (UCID: 30020570)
 * @author Matteo Messana (UCIDL 30020933)
 *
 */

public class OrderLine {
	
	/**
	 * The item to be added to the Order 
	 */
	private Item theItem;
	
	/**
	 * The quantity of the Item to be ordered
	 */
	private int orderQuantity;
	
	/**
	 * Constructor of class OrderLine
	 * @param item : the Item to be added to the order
	 * @param quantity : the quantity of the item to be added to the order
	 */
	public OrderLine (Item item, int quantity) {
		theItem = item;
		setOrderQuantity(quantity); 
		
	}

	/**
	 * Retrieves the Item from the OrderLine
	 * @return theItem: the Item in the OrderLine
	 */
	public Item getTheItem() {
		return theItem;
	}

	/**
	 * Sets the Item for the OrderLine
	 * @param theItem : the Item in the OrderLine 
	 */
	public void setTheItem(Item theItem) {
		this.theItem = theItem;
	}

	/**
	 * Retrieves the quantity of the item ordered in the OrderLine
	 * @return orderQuantity : the amount of an item ordered
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}

	/**
	 * Sets the quantity of the item ordered in the OrderLine
	 * @param orderQuantity : the amount of an item ordered
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	public String toString (){
		return  "Item Name: " + theItem.getItemName() +
				", Item ID: " + theItem.getItemId()+ "\n" + 
				"Order Quantity: " + orderQuantity + "\n";
	}

}
