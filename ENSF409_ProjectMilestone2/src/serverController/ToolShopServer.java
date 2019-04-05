package serverController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import serverModel.*;

/**
 * Class ToolShopServer
 * 
 * @author Dr. Mohamed Moshirpour (Retrieved Sunday March 31st, 2019)
 * Edited by: 
 * @author Muhammad Farooq (UCID: 30016276)
 * @author Alexander Gorkoff (UCID: 30020570)
 * @author Matteo Messana (UCIDL 30020933)
 *
 */

public class ToolShopServer {
	
	/**
	 * Socket used to receive requests from the client or send requests from the client
	 * Allows for the server to perform actions based on the requests it receives
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Thread pool to allow the server to host multiple clients at once. 
	 */
	private ExecutorService pool;
	
	/**
	 * Constructor of type ToolShopServer
	 * @param portNumber : the port on which the ToolShopServer will be hosted on the machine
	 */
	public ToolShopServer(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deploys new clients to the server (thread pool allows it to host multiple clients)
	 * Indicates when a new client has been deployed to the server
	 * @throws IOException
	 */
	public void communicateWithClient() throws IOException {
		try {
			while(true) {
				ServerController customer = new ServerController(serverSocket.accept());
				
				System.out.println("New Customer! Welcome to the Store!");
				
				pool.execute(customer);
			}
		} catch(Exception e) {
			e.printStackTrace();
			pool.shutdown();
		}
	}
	
	
	public static void main(String [] args) throws IOException {
		ToolShopServer toolShopServer = new ToolShopServer(8099);
		System.out.println("The Tool Shop Server is up and running!");
		toolShopServer.communicateWithClient();
	}
}
