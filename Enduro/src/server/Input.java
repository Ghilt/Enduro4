package server;

import java.io.IOException;
import java.io.InputStream;

public class Input extends Thread {
	private InputStream in;
	private Monitor monitor;
	private int clientIdentifier;

	public Input(InputStream inputStream, Monitor monitor, int identifier) {
		in = inputStream;
		this.monitor = monitor;
		clientIdentifier = identifier;
	}

	/**
	 * Listens to the inputstream for messages from client and pass forwards
	 * them to the monitor.
	 */
	public void run() {
		while (!isInterrupted()) {
			int size;
			try {
				size = in.read();
				if(size == -1) {
					close();
				} else {
					byte msg[] = new byte[size];
					in.read(msg, 0, size);
					System.out.println("size=" + size);
					monitor.register(clientIdentifier, msg);
				}
				
				sleep(10);
			} catch (IOException e) {
				interrupt();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close();
	}

	/**
	 * Closes the connection.
	 */
	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Exception when trying to close inputstream?");
		}
	}

	

}
