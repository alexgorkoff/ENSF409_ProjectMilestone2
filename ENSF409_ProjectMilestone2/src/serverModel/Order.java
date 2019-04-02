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
	
	
	private Date today;
	private int orderId;
	private ArrayList <OrderLine> orderLines;
	
	
	public Order () {
		today = Calendar.getInstance().getTime();
		orderLines = new ArrayList <OrderLine> ();
	}
	
	
	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
	
	public ArrayList <OrderLine> getOrderLines (){
		return orderLines;
	}
	public void setOrderLines (ArrayList <OrderLine> lines){
		orderLines = lines;
	}

	public int getOrderId() {
		return orderId;
	}


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
