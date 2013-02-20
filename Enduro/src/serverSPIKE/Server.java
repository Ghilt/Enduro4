package serverSPIKE;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private Monitor monitor;
	private Input in;
	private Socket clientSocket;
	private ServerSocket socket;

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
					e1.printStackTrace();
				}
				// The 'accept' method waits for a client to connect, then
				// returns a socket connected to that client.
				System.out.println("Waiting for client to connect...");
				clientSocket = socket.accept();
				System.out.println("Client connected");

				monitor = new Monitor();
				in = new Input(clientSocket.getInputStream(), monitor);

				in.start();

				// While client is still connected
				while (clientSocket.isConnected() && !clientSocket.isClosed()
						&& !clientSocket.isInputShutdown()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				restart();
			} catch (IOException e) {
				System.out.println("Caught exception " + e);
				restart();
			}
		}
	}

	private void restart() {
		System.out.println("Client disconnected");
		try {
			socket.close();
			clientSocket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		in.close();

		in = null;
		monitor = null;
		clientSocket = null;
		socket = null;
	}

}
