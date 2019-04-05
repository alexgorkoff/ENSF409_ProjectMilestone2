package serverModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class Order
 * 
 * @author Dr. Mohamed Moshirpour (Retrieved Sunday March 31st, 2019)
 * Edited by: 
 * @author Muhammad Farooq (UCID: 30016276)
 * @author Alexander Gorkoff (UCID: 30020570)
 * @author Matteo Messana (UCIDL 30020933)
 *
 */

public class Order {
	
	/**
	 * Date object indicating the date and time at which the Order was placed
	 */
	private Date today;
	
	/**
	 * ID tag used to identify an order
	 */
	private int orderId;
	
	/**
	 * Record of all OrderLines that are associated with an Order
	 */
	private ArrayList <OrderLine> orderLines;
	
	/**
	 * Constructor of class Order
	 */
	public Order () {
		today = Calendar.getInstance().getTime();
		orderLines = new ArrayList <OrderLine> ();
	}
	
	/**
	 * Adds a new OrderLine to the existing list of OrderLines pertaining to an Order
	 * @param ol : The OrderLine being added to the Order
	 */
	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
	
	/**
	 * Retrieves the list of OrderLines pertaining to an Order
	 * @return orderLines : a list containing all OrderLines in an Order
	 */
	public ArrayList <OrderLine> getOrderLines (){
		return orderLines;
	}
	
	/**
	 * Sets the list of OrderLines to associate it with an existing list from an Order
	 * @param lines
	 */
	public void setOrderLines (ArrayList <OrderLine> lines){
		orderLines = lines;
	}

	/**
	 * Retrieves the ID number associated with an Order. 
	 * @return orderID: the ID number for an Order.
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the ID number for a particular Order. 
	 * @param orderId : the ID number associated with an Order.
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
	public String toString (){
		String order = "\nOrder Date: " + today.toString() + "\n\n";
		String str = "";
		for (OrderLine ol: orderLines) {
			str += ol;
			str += "------------------------\n";
		}
		if (str == "")
			str = "There are currently no OrderLines to print.";
		
		order += str;
		//order += "\n";
		return order;
	}

}
