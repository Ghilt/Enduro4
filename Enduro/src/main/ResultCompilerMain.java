package main;

import java.io.File;
import java.io.FileInputStream;
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

	private static final String CONFIG = "config.properties";
	private static final String ERROR = "FEL";
	private static final String RESULT = "SLUT";
	public final static String PROGRAM_DONE = "Program klart";

	/**
	 * 
	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * 
	 */
	public static void main(String[] args) {

		try {
			compile();
			infoMessage(PROGRAM_DONE);
		} catch (Exception e) {
			errorMessage(e.getMessage());
			System.exit(-1);
		}
	}

	protected static void compile() throws Exception {
		// Load the directory
		CodeSource codeSource = ResultCompilerMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath() + File.separator;

		Properties prop = new Properties();
		// load properties from config.properties
		prop.load(new FileInputStream(jarDir + CONFIG));

		// Perform program as instructed by config file
		InputFileHandler in = new InputFileHandler(jarDir);
		Map<Integer, Competitor> map = null;
		map = in.parseInputFiles(in.getInputFiles(prop), prop);
		ArrayList<Competitor> list = new ArrayList<Competitor>(map.values());
		in.printResults(prop, list);

		// Profit!!!
	}

	private static void infoMessage(String e) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, e, RESULT,
				JOptionPane.PLAIN_MESSAGE);
		frame.dispose();
	}

	/**
	 * If an error is caught during the parsing, a message is shown. See
	 * ParserException class for information of what errors or
	 * FileNotFoundException.
	 */
	private static void errorMessage(String e) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, e, ERROR,
				JOptionPane.ERROR_MESSAGE);
		frame.dispose();
	}

}
