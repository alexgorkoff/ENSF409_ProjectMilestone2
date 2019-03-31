import java.io.IOException;

public class ToolShopClient {
	
	private ClientSocket clientSockets;
	
	public ToolShopClient(String portName, int portNumber) {
		clientSockets = new ClientSocket(portName, portNumber);
	}
	
	public void communicateServer() {
		try {
			while(true) {
				//Read and print anything received by the Server (A null character will mark when to stop reading)
				String read = "";
				
				while(true) {
					read = clientSockets.getSocketIn().readLine();
					if(read.contains("\0")) {
						read = read.replace("\0", "");
						System.out.println(read);
						break;
					}
					if(read.contentEquals("QUIT")) {
						return;
					}
					
					System.out.println(read);
					
				}
				
				read = clientSockets.getStdIn().readLine();
				clientSockets.getSocketOut().println(read);
				clientSockets.getSocketOut().flush();

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
	
	public static void main(String [] args) {
		ToolShopClient toolShopClient = new ToolShopClient("localhost", 8099);
		toolShopClient.communicateServer();
	}
	
}
