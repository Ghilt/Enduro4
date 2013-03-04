package client;


import gui.register.Gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.CodeSource;

import main.RegisterGuiMain;

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
		InputStream inputStream = null;
		try {
			outStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Output out = new Output(outStream);
		
		
		CodeSource codeSource = RegisterGuiMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jarDir = jarFile.getParentFile().getPath();

		String str = jarDir + File.separator + "tider.txt";
		
		new Gui(out, str);
	}
}