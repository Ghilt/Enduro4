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
	private final String resultpath = "result.txt";
	private final String timesFilepath = "times.txt";

	public static void main(String[] args) {
		new Server().handleRequests(27015);
	}

	private void handleRequests(int srvPort) {
		in = null;
		monitor = null;
		clientSocket = null;
		socket = null;

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

				monitor = new Monitor(resultpath, timesFilepath);
				in = new Input(clientSocket.getInputStream(), monitor);

				in.start();
				
			} catch (IOException e) {
				System.out.println("Caught exception " + e);
			}
		}
	}

}
