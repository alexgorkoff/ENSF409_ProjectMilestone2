import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketPack {
	private PrintWriter socketOut;
	private Socket aSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	
	public SocketPack(String serverName, int portNumber) {
		
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
	
	public void setSocketOut(PrintWriter socketOut) {
		this.socketOut = socketOut;
	}
	
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	public void setASocket(Socket aSocket) {
		this.aSocket = aSocket;
	}
	
	public Socket getASocket() {
		return aSocket;
	}
	
	public void setStdIn(BufferedReader stdIn) {
		this.stdIn = stdIn;
	}
	
	public BufferedReader getStdIn() {
		return stdIn;
	}
	
	public void setSocketIn(BufferedReader socketIn) {
		this.socketIn = socketIn;
	}
	
	public BufferedReader getSocketIn() {
		return socketIn;
	}
}