package clientController;

import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import interfaces.*;
import serverModel.*;

/**
 * The Client on the server that will serve as the user of the toolshop system.
 * Sending messages back and forth between the server to communicate
 * 
 * @author Muhammad Farooq, Alex Gorkoff, Matteo Messana
 * @version 1.2.0
 * @since March 29, 2019
 */
public class ToolShopClient implements SessionID {
	/**
	 * The SocketPack of the client that it will use to communicate with the server
	 */
	private SocketPack clientSockets;
	/**
	 * The controller that will be used as part of the client to control the GUI
	 */
	private ClientController clientController;

	/**
	 * Constructor for the ToolShop Client to connec to the server
	 * 
	 * @param portName   to connect to
	 * @param portNumber to connect to
	 */
	public ToolShopClient(String portName, int portNumber) {

		clientSockets = new SocketPack(portName, portNumber);
		clientController = new ClientController(clientSockets);

	}

	/**
	 * synchronized method that will be used to send messages back and forth from
	 * the server and will respond according to the message received from the server
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void communicateServer() throws InterruptedException {
		try {
			while (true) {

				String sessionLabel = "";

				sessionLabel = clientSockets.getSocketIn().readLine();
				System.out.println(sessionLabel);

				switch (sessionLabel) {

				case GET_ITEM_QUANTITY:
					clientSockets.sendString(clientController.getToolNameUser());
					clientSockets.getSocketIn().readLine(); // Have no idea where this space is being read from
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;

				case DECREASE_QUANTITY:
					clientSockets.sendString(clientController.getToolNameUser());
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;
				case SEARCH_ITEM_NAME:
					clientSockets.sendString(clientController.getView().getSearch());
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;
				case SEARCH_ITEM_ID:
					clientSockets.sendString(clientController.getView().getSearch());
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;
				case LIST_ALL_ITEMS:

					String printLine = clientSockets.getSocketIn().readLine();
					clientController.getView().getTableModel().setRowCount(0);
					while (printLine.contentEquals("") != true) {

						clientController.insertDataToolTable(printLine);
						printLine = clientSockets.getSocketIn().readLine();

					}
					break;

				case QUIT:
					return;

				default:
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSockets.getStdIn().close();
				clientSockets.getASocket().close();
				clientSockets.getSocketOut().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException {

		ToolShopClient toolShopClient = new ToolShopClient("10.13.77.106", 8099);
		toolShopClient.communicateServer();
	}

}
