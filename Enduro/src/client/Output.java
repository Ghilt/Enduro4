package client;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sends commands to the server through OutputStreams.
 */
public class Output {

	private OutputStream out;

	public Output(OutputStream out) {
		this.out = out;
	}
	
	public void sendMessage(String msg) {
		try {
			byte[] bytes = msg.getBytes();
			int length = bytes.length;
			System.out.println(msg);
			out.write(length);
			out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
