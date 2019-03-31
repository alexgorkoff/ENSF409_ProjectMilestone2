import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
	private PrintWriter socketOut;
	private Socket aSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	
	public ClientSocket(String serverName, int portNumber) {
		
		try {
			aSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void sendStringPrint(String toSend) {
		socketOut.print(toSend);
		socketOut.flush();
	}
	
	public void sendStringPrintln(String toSend) {
		socketOut.print(toSend);
		socketOut.flush();
	}
	
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	public Socket getASocket() {
		return aSocket;
	}
	
	public BufferedReader getStdIn() {
		return stdIn;
	}
	
	public BufferedReader getSocketIn() {
		return socketIn;
	}
}
