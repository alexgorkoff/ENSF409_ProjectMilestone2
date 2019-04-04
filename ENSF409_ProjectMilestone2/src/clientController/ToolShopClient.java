package clientController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import serverController.SessionID;
import serverModel.*;

public class ToolShopClient implements SessionID{
	
	private SocketPack clientSockets;
	private ClientController clientController;
	
	public ToolShopClient(String portName, int portNumber) {
		
		clientSockets = new SocketPack(portName, portNumber);
		clientController = new ClientController(clientSockets);
		
	}
	
	public synchronized void communicateServer() throws InterruptedException {
		try {
			while(true) {

				String sessionLabel = "";
				
					sessionLabel = clientSockets.getSocketIn().readLine();
					System.out.println(sessionLabel);
						
				switch(sessionLabel) {
				
				case GET_ITEM_QUANTITY:
					clientSockets.sendString(clientController.getToolNameUser());
					clientSockets.getSocketIn().readLine(); //Have no idea where this space is being read from
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;
				
				case DECREASE_QUANTITY:
					clientSockets.sendString(clientController.getToolNameUser());
					//clientSockets.getSocketIn().readLine(); //Have no idea where this space is being read from
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
					
					while(printLine.contentEquals("") != true) {
						
						clientController.displayList(printLine);
						printLine = clientSockets.getSocketIn().readLine();
						
					}
					break;
					
				case QUIT: 
					return;
				
				default:
					break;
				}

			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				clientSockets.getStdIn().close();
				clientSockets.getASocket().close();
				clientSockets.getSocketOut().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args) throws UnknownHostException, InterruptedException {
		ToolShopClient toolShopClient = new ToolShopClient("localhost", 8099);
		toolShopClient.communicateServer();
	}
	
}
