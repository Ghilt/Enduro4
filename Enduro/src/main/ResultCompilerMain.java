package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import members.Competitor;

/**
 * @author tf08vo5
 * 
 */
public class ResultCompilerMain {

	public final static String PROGRAM_DONE = "Programmet färdigt";

	/**
	 * 
	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * 
	 * @throws URISyntaxException
	 * @throws FileNotFoundException
	 * 
	 */
	public static void main(String[] args) throws URISyntaxException {

		// Load the directory
		CodeSource codeSource = ResultCompilerMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath() + File.separator;
		//System.out.println("DERP" + jarDir);

		// try to load properties
		Properties prop = new Properties();

		try {
			// load properties from config.properties
			prop.load(new FileInputStream(jarDir + "config.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Perform program from config file
		try {
			InputFileHandler in = new InputFileHandler(jarDir);
			Map<Integer, Competitor> map = null;
			map = in.parseInputFiles(in.getInputFiles(prop), prop);

			ArrayList<Competitor> list = new ArrayList<Competitor>(map.values());
			in.printResults(prop, list);
			infoMessage(PROGRAM_DONE);
		} catch (Exception e) {
			errorMessage(e.getMessage());
			System.exit(-1);
		}
		// Profit???
	}

	private static void infoMessage(String e) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, e, "Result",
				JOptionPane.PLAIN_MESSAGE);
		frame.dispose();
	}

	/**
	 * If an error is caught during the parsing, a message is shown. See
	 * ParserException class for information of what errors or
	 * FileNotFoundException.
	 * 
	 * @param e
	 *            exception
	 */
	private static void errorMessage(String e) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, e, "FEL",
				JOptionPane.ERROR_MESSAGE);
		frame.dispose();
	}

}
