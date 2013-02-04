package gui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class GuiMain {

	/**
	 * Launches the Gui for entering race times.
	 * 
	 * @param args
	 *            Arg0 = Output file
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		CodeSource codeSource = GuiMain.class.getProtectionDomain().getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();
		
		String str = jarDir + File.separator + "tider.txt";
		new Gui(str);
	}

}
