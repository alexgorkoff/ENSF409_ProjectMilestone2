package serverModel;
import java.util.ArrayList;

/**
 * Supplier class that holds all the information for a supplier.
 * @author Matteo Messana, Alexander Gorkoff, Usman Farooq
 * @version 1.0
 * @since April 4th, 2019
 */

public class Supplier {
	
	/**
	 * Member variable that holds the supplier ID.
	 */
	private int supId;
	
	/**
	 * Member variable that holds the name of the supplier.
	 */
	private String supName;
	
	/**
	 * Member variable that holds the supplier's address.
	 */
	private String supAddress;
	
	/**
	 * Member variable that holds the contact name for the supplier.
	 */
	private String supContactName;
	
	/**
	 * Member variable that holds an item list of all items the supplier supplies.
	 */
	private ArrayList <Item> itemList;
	
	
	/**
	 * Constructor for the supplier
	 * @param id: id for the supplier
	 * @param name: name of the supplier
	 * @param address: address of the supplier
	 * @param contactName: contact of the supplier
	 */
	public Supplier (int id, String name, String address, String contactName) {
		
		supId = id;
		supName = name;
		supAddress = address;
		supContactName = contactName;
		itemList = new ArrayList <Item>();
	}


	/**
	 * getter for supplier ID
	 * @return int: supplier ID
	 */
	public int getSupId() {
		return supId;
	}

	/**
	 * setter for supplier ID
	 * @param supId: receives supplier ID
	 */
	public void setSupId(int supId) {
		this.supId = supId;
	}

	/**
	 * getter for supplier name
	 * @return String: supplier name
	 */
	public String getSupName() {
		return supName;
	}

	/**
	 * setter for supplier name
	 * @param supName: receives supplier name
	 */
	public void setSupName(String supName) {
		this.supName = supName;
	}

	/**
	 * getter for supplier address
	 * @return String: supplier address
	 */
	public String getSupAddress() {
		return supAddress;
	}

	/**
	 * setter for supplier address
	 * @param supAddress: receives supplier address
	 */
	public void setSupAddress(String supAddress) {
		this.supAddress = supAddress;
	}

	/**
	 * getter for supplier contact
	 * @return String: contact name
	 */
	public String getSupContactName() {
		return supContactName;
	}

	/**
	 * setter for supplier contact
	 * @param supContactName: receives contact name
	 */
	public void setSupContactName(String supContactName) {
		this.supContactName = supContactName;
	}
	
	/**
	 * toString method for supplier
	 */
	public String toString () {
		return supName + " Supplier ID: " + supId+ "\n";
		
	}

	/**
	 * getter for item list
	 * @return ArrayList<Item>: item list
	 */
	public ArrayList <Item> getItemList() {
		return itemList;
	}

	/**
	 * setter for item list
	 * @param itemList: receives an item list
	 */
	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}
	

}
