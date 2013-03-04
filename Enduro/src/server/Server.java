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
	private String resultpath = "result.txt";
	private String namefilepath = "namnfil.txt";
	
	public static void main(String[] args) {
		new Server().handleRequests(27016);
	}

	private void handleRequests(int srvPort) {
		ClassLoader classLoader = Server.class.getClassLoader();
		String currentFilePath = classLoader.getResource("").getPath();
		resultpath = currentFilePath + "/" + resultpath;
		namefilepath = currentFilePath + "/" + namefilepath;
		monitor = null;
		clientSocket = null;
		socket = null;
		monitor = new Monitor(resultpath, namefilepath);
		ServerGui srvGui = new ServerGui();
		while (true) {
			try {
				try {
					socket = new ServerSocket(srvPort);
				} catch (IOException e1) {
				}
				// The 'accept' method waits for a client to connect, then
				// returns a socket connected to that client.
				srvGui.setText("Waiting for client to connect...");
				clientSocket = socket.accept();
				srvGui.setText("Client connected.");
				
				in = new Input(clientSocket.getInputStream(), monitor);
				in.start();
				
			} catch (IOException e) {
				System.out.println("Caught exception " + e);
			}
		}
	}

}
