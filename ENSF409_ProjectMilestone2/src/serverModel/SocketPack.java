package serverModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Class used for socket communication between the client
 * and the server.
 * @author Matteo Messana, Alexander Gorkoff, Usman Farooq
 * @version 1.0
 * @since April 4th, 2019
 */

public class SocketPack {
	/**
	 * Printwriter object used for writing out of the socket.
	 */
	private PrintWriter socketOut;
	
	/**
	 * A socket object used for communication between client and server.
	 */
	private Socket aSocket;
	
	/**
	 * BuferedReader object used for writing into the socket.
	 */
	private BufferedReader socketIn;
	
	/**
	 * The constructor for the socket pack using the port name and port number.
	 * @param serverName: name for the server, usually an IP address
	 * @param portNumber: port number used to connect to server.
	 */
	public SocketPack(String serverName, int portNumber) {
		
		try {
			aSocket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * The constructor for the socket pack using the socket
	 * @param theSocket: the socket used for the client and server.
	 */
	public SocketPack(Socket theSocket) {
		
		aSocket = theSocket;
		
		try {
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter(theSocket.getOutputStream());
		} catch(Exception e) {
			System.err.println("Failed to initialize Front End");
		}
	}
	
	/**
	 * Sends string to the out socket
	 * @param toSend: string to send.
	 */
	public void sendString(String toSend) {
		socketOut.println(toSend);
		socketOut.flush();
	}
	
	/**
	 * Setter for SocketOut
	 * @param socketOut: takes a PrintWriter object
	 */
	public void setSocketOut(PrintWriter socketOut) {
		this.socketOut = socketOut;
	}
	
	/**
	 * Getter for SocketOut
	 * @return socketOut: returns a PrintWriter object
	 */
	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	/**
	 * Setter for ASocket
	 * @param ASocket: takes a Socket object
	 */
	public void setASocket(Socket aSocket) {
		this.aSocket = aSocket;
	}
	
	/**
	 * Getter for ASocket
	 * @return aSocket: returns a Socket object
	 */
	public Socket getASocket() {
		return aSocket;
	}
	
	/**
	 * Setter for SocketIn
	 * @param socketIn: takes a BrufferedReader object
	 */
	public void setSocketIn(BufferedReader socketIn) {
		this.socketIn = socketIn;
	}
	
	/**
	 * Getter for SocketIn
	 * @return socketIn: returns a BufferedReader object
	 */
	public BufferedReader getSocketIn() {
		return socketIn;
	}
}
