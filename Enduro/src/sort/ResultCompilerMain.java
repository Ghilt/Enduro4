package sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import members.Competitor;
import result.CvsReader;
import result.Parser;
import result.ParserException;

public class ResultCompilerMain {

	public static final String STARTTIMES = "start";
	public static final String NAMEFILE = "namn";
	public static final String FINISHTIMES = "slut";
	public static final String RESULTFILE = "resultat";
	public static final String SORTRESULTFILE = "sortresultat";
	public static final String EXTENSION = ".txt";
	private final static String LAP_RACE = "laprace";
	private final static String STANDARD = "standard";
	public final static String YES = "yes";
	public final static String NO = "no";

	/**
	 * 
	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * 
	 * @throws URISyntaxException
	 * 
	 */
	public static void main(String[] args) throws URISyntaxException {
		Properties prop = new Properties();

		try {
			// load properties from config.properties
			prop.load(new FileInputStream("config.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Detects the location of the executable.
		// <<<<<<<------- Do we need this?? ------->>>>>>>
		CodeSource codeSource = ResultCompilerMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();
		//<<<<<<<------- Do we need this?? ------->>>>>>>
		
		ArrayList<String> inputFiles = getInputFiles(prop);
		
		Map<Integer, Competitor> map = null;
		try {
			map = parseInputFiles(inputFiles);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			errorMessage(e.getMessage());
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			errorMessage(e.getMessage());
		}

		ArrayList<Competitor> list = new ArrayList<Competitor>(map.values());
		
		printResults(prop, list);

	}

	/**
	 * Prints the result to the file specified in the config file under 'resultfile'.
	 * If 'sorted' in config file is set to 'yes' is also prints a sorted result to the file 
	 * specified in 'sortedresultfile' in the config file.
	 * 
	 * @param prop	contains the values in the config file
	 * @param competitors	the list of competitors to print
	 */
	private static void printResults(Properties prop, ArrayList<Competitor> competitors) {
		String filepath = prop.getProperty("resultfile");
		Printer printer = getPrinter(prop);
		Sorter sorter = new Sorter();
		sorter.sortList(false, competitors);
		printer.printResults(competitors, filepath);
		
		boolean sorted = false;
		if(prop.containsKey("sorted")) {
			sorted = prop.get("sorted").equals(YES);
		}
		if(sorted) {
			filepath = prop.getProperty("sortedresultfile");
			printer = new SortLapCompetitorPrinter();
			sorter.sortList(true, competitors);
			printer.printResults(competitors, filepath);
		}
	}

	/**
	 * Parses the content of each file in the list of inputfiles to the list of competitors.
	 * 
	 * @param inputFiles	list of inputfiles
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParserException
	 */
	private static Map<Integer, Competitor> parseInputFiles(
			ArrayList<String> inputFiles) throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();
		Parser p = new Parser();
		for(String file : inputFiles) {
			map = p.parse(read(file), map);
		}
		return map;
	}

	private static ArrayList<String> getInputFiles(Properties prop) {
		ArrayList<String> inputFiles = new ArrayList<String>();
		if (prop.containsKey("starttimes")) {
			inputFiles.add(prop.getProperty("starttimes"));
		}
		if (prop.containsKey("namefile")) {
			inputFiles.add(prop.getProperty("namefile"));
		}
		String finishPath = "";
		if (prop.containsKey("finishfiles")) {
			finishPath = prop.getProperty("finishfiles");
		}
		String[] finishFiles = finishPath.split(" ");
		for(String s : finishFiles) {
			inputFiles.add(s);
		}
		return inputFiles;
	}

	private static ArrayList<ArrayList<String>> read(String file) throws FileNotFoundException {
		CvsReader reader = new CvsReader(file);
		
		return reader.readAll();
	}

	/**
	 * Return a printer. Which type depends on the status of 'racetype' in the
	 * config file. If 'racetype' status does not exist in config file, a
	 * stdCompetitorPrinter is returned. Else if 'racetype' status is set to
	 * 'laprace', a lapCompetitorPrinter is returned, and finally if 'racetype'
	 * status is set to 'laprace' and the 'sorted' status is set to 'yes' a
	 * SortCompetitorPrinter is returned.
	 * 
	 * @param prop
	 *            properties to get status in config file
	 * @return a printer, which type depends on the status in the config file
	 */
	private static Printer getPrinter(Properties prop) {
		Printer printer = null;

		String printerType = prop.getProperty("racetype");

		if (printerType.equals(STANDARD)) {
			printer = new StdCompetitorPrinter();
		} else if (printerType.equals(LAP_RACE)) {
			printer = new LapCompetitorPrinter();
		}
		return printer;
	}

	private static void errorMessage(String e) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, e, "FEL",
				JOptionPane.ERROR_MESSAGE);
		frame.dispose();
	}

	

}
