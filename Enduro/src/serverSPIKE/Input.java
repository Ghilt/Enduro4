package serverSPIKE;

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
			int msg;
			try {
				msg = in.read();
				monitor.register(msg);
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
