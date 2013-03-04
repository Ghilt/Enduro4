package clientSPIKE;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.CodeSource;

import main.RegisterGuiMain;

public class Client {

	public static void main(String[] args) throws URISyntaxException {

		
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
		
		CodeSource codeSource = RegisterGuiMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();

		String str = jarDir + File.separator + "tider.txt";

		new Gui(out, str);

	}
}