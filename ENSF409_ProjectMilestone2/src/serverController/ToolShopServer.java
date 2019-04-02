package serverController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import serverModel.*;

public class ToolShopServer {
	
	private ServerSocket serverSocket;
	private ExecutorService pool;
	

	public ToolShopServer(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
