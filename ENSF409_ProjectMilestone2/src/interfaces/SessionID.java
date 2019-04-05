package interfaces;

/**
 * Interface containing codes that will be used to send messages to the client
 * 
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.2.0
 * @since April 2, 2019
 */
public interface SessionID {
	/**
	 * Code that will be used to encode GET_ITEM_QUANTITY to the client
	 */
	public final String GET_ITEM_QUANTITY = "1000";
	/**
	 * Code that will be used to encode DECREASE_QUANTITY to the client
	 */
	public final String DECREASE_QUANTITY = "1001";
	/**
	 * Code that will be used to encode LIST_ALL_ITEMS to the client
	 */
	public final String LIST_ALL_ITEMS = "1010";
	/**
	 * Code that will be used to encode SEARCH_ITEM_NAME to the client
	 */
	public final String SEARCH_ITEM_NAME = "1011";
	/**
	 * Code that will be used to encode SEARCH_ITEM_ID to the client
	 */
	public final String SEARCH_ITEM_ID = "1100";
	/**
	 * Code that will be used to encode PRINT_ORDER to the client
	 */
	public final String PRINT_ORDER = "1101";
	/**
	 * Code that will be used to encode QUIT to the client
	 */
	public final String QUIT = "1110";

}
