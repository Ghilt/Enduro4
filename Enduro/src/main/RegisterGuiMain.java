package main;


import gui.register.Gui;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class RegisterGuiMain {

	/**
	 * Launches the Gui for entering race times.
	 * 
	 * @param args
	 *            Arg0 = Output file
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException {
		CodeSource codeSource = RegisterGuiMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();

		String str = jarDir + File.separator + "tider.txt";
		new Gui(str);
	}

}
