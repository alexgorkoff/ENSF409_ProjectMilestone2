package serverModel;
import java.util.ArrayList;

public class Shop {
	
	private Inventory theInventory;
	private ArrayList <Supplier> supplierList;
	
	public Shop (Inventory inventory, ArrayList <Supplier> suppliers) {
		
		theInventory = inventory;
		supplierList = suppliers;
		
	}
	
	public Inventory getTheInventory () {
		return theInventory;
	}
	public void setTheInventory (Inventory inventory) {
		theInventory = inventory;
	}
	public ArrayList<Supplier> getSupplierList (){
		return supplierList;
	}
	public void setSupplierList (ArrayList <Supplier> suppliers){
		supplierList = suppliers;
	}
	
	public void listAllItems(SocketPack customerSockets) {
		
		String item = theInventory.toString();
		//for(String s: item.split("\n")) {
			customerSockets.sendString(item);	
		//}
		
	}
	public String decreaseItem (String name) {
		if (theInventory.manageItem(name) == null)
			return "Couldn't decrease item quantity!";
		else
			return "Item quantity was decreased!";
	}

	public void listAllSuppliers(SocketPack customerSockets) {
		// TODO Auto-generated method stub
		for (Supplier s: supplierList) {
			customerSockets.sendString(s.toString());
		}
		
	}

	public String getItem(String name) {
		// TODO Auto-generated method stub
		Item theItem = theInventory.searchForItem(name);
		if (theItem == null)
		     return "Item " + name + " could not be found!";
		else
			 return outputItem (theItem);
			
	}

	public String getItem(int id) {
		// TODO Auto-generated method stub
		Item theItem = theInventory.searchForItem(id);
		if (theItem == null)
		     return "Item number " + id + " could not be found!";
		else
			return outputItem (theItem);
			 
		
	}
	
	private String outputItem (Item theItem){
		return "The item information is as follows: " + theItem.toString();
	}

	public String getItemQuantity(String name) {
		// TODO Auto-generated method stub
		int quantity = theInventory.getItemQuantity(name);
		
		if (quantity < 0)
		    return "\nItem " + name + " could not be found!";
		else
			return "\nThe quantity of Item " + name + " is: " + quantity;
	}

	public String printOrder() {
		// TODO Auto-generated method stub
		
		return theInventory.printOrder() + "\n";
	}

	

}
