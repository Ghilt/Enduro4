package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private Monitor monitor;
	private Input in;
	private Socket clientSocket;
	private ServerSocket socket;
	private int clientIdentifier;

	public static void main(String[] args) {
		new Server().handleRequests(27015);
	}

	private void handleRequests(int srvPort) {
		in = null;
		monitor = null;
		clientSocket = null;
		socket = null;
		clientIdentifier = 1;

		while (true) {
			try {
				try {
					socket = new ServerSocket(srvPort);
				} catch (IOException e1) {
				}
				// The 'accept' method waits for a client to connect, then
				// returns a socket connected to that client.
				System.out.println("Waiting for client to connect...");
				clientSocket = socket.accept();
				System.out.println("Client connected");

				monitor = new Monitor();
				in = new Input(clientSocket.getInputStream(), monitor, clientIdentifier++);

				in.start();
				
			} catch (IOException e) {
				System.out.println("Caught exception " + e);
			}
		}
	}

}
