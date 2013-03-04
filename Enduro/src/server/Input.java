package server;

import java.io.IOException;
import java.io.InputStream;

public class Input extends Thread {
	private InputStream in;
	private Monitor monitor;

	public Input(InputStream inputStream, Monitor monitor) {
		in = inputStream;
		this.monitor = monitor;
	}

	/**
	 * Listens to the inputstream for messages from client and pass forwards
	 * them to the monitor.
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				byte[] startNbr = readInput();
				byte[] msg = readInput();
				
				monitor.register(startNbr, msg);
				
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close();
	}

	private byte[] readInput() {
		byte[] bytes = null;
		try {
			int size = in.read();
			if (size == -1) {
				close();
			} else {
				bytes = new byte[size];
				in.read(bytes, 0, size);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
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
