package clientController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import serverModel.*;

public class ToolShopClient {
	
	private SocketPack clientSockets;
	private ClientController clientController;
	
	public ToolShopClient(String portName, int portNumber) {
		
		clientSockets = new SocketPack(portName, portNumber);
		clientController = new ClientController(clientSockets);
		
	}
	
	public void communicateServer() {
		try {
			while(true) {

				String read = "";
				read = clientSockets.getSocketIn().readLine();
				System.out.println(read);
				
				switch(read) {
				
				case "name":
					clientSockets.sendString(clientController.getToolNameUser());
					break;
				
				case "id":
					clientSockets.sendString(clientController.getToolIDUser());
					break;
				
				case "OutputQuantity":
					clientController.outputClientGUI(clientSockets.getSocketIn().readLine());
					break;
				
				case "QUIT": 
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
	
	public static void main(String [] args) throws UnknownHostException {
		ToolShopClient toolShopClient = new ToolShopClient("localhost", 8099);
		toolShopClient.communicateServer();
	}
	
}
