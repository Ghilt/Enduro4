package clientSPIKE;

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
	
	public void sendMessage(int msg) {
		try {
			out.write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
