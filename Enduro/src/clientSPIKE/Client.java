package clientSPIKE;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {

		
		Socket socket = null;

		String socketAddress = "localhost";
		int socketPort = 27015;

		try {
			socket = new Socket(socketAddress, socketPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OutputStream outStream = null;

		try {
			outStream = socket.getOutputStream();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Output out = new Output(outStream);

		new Gui(out);

	}
}