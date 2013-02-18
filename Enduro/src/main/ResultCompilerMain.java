package main;

import io.printer.*;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.Parser.FileIdentifier;
import io.reader.ParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import members.Competitor;
import members.Sorter;

/**
 * @author tf08vo5
 * 
 */
public class ResultCompilerMain {

	public static final String STARTTIMES = "start";
	public static final String NAMEFILE = "namn";
	public static final String FINISHTIMES = "slut";
	public static final String RESULTFILE = "resultat";
	public static final String SORTRESULTFILE = "sortresultat";
	public static final String EXTENSION = ".txt";
	private final static String LAP_RACE = "laprace";
	private final static String STANDARD = "standard";
	private final static String BINARY_LAPS = "etapprace";
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

		// Detects the location of the executable. Keep as comment if needed
		// later.
		//
		// CodeSource codeSource =
		// ResultCompilerMain.class.getProtectionDomain()
		// .getCodeSource();
		// File jarFile = new File(codeSource.getLocation().toURI().getPath());
		// String jarDir = jarFile.getParentFile().getPath();
		//

		Map<String, FileIdentifier> inputFiles = getInputFiles(prop);

		Map<Integer, Competitor> map = null;
		try {
			map = parseInputFiles(inputFiles, prop);
		} catch (FileNotFoundException e) {
			errorMessage(e.getMessage());
		} catch (ParserException e) {
			errorMessage(e.getMessage());
		}

		ArrayList<Competitor> list = new ArrayList<Competitor>(map.values());

		printResults(prop, list);

	}

	/**
	 * Prints the result to the file specified in the config file under
	 * 'resultfile'. If 'sorted' in config file is set to 'yes' is also prints a
	 * sorted result to the file specified in 'sortedresultfile' in the config
	 * file.
	 * 
	 * @param prop
	 *            contains the values in the config file
	 * @param competitors
	 *            the list of competitors to print
	 */
	private static void printResults(Properties prop,
			ArrayList<Competitor> competitors) {
		String filepath = prop.getProperty("resultfile");
		Printer printer = getPrinter(prop);
		Sorter sorter = new Sorter();
		sorter.sortList(false, competitors);
		printer.printResults(competitors, filepath);

		boolean sorted = false;
		if (prop.containsKey("sorted")) {
			sorted = prop.get("sorted").equals(YES);
		}
		if (sorted) {
			filepath = prop.getProperty("sortedresultfile");
			printer = getSortPrinter(prop);
			sorter.sortList(true, competitors);
			printer.printResults(competitors, filepath);
		}
	}

	/**
	 * Parses the content of each file in the list of inputfiles to the list of
	 * competitors.
	 * 
	 * @param inputFiles
	 *            list of inputfiles
	 * @param prop
	 * @return a hashmap with the competitors information
	 * @throws FileNotFoundException
	 * @throws ParserException
	 */
	private static Map<Integer, Competitor> parseInputFiles(
			Map<String, FileIdentifier> inputFiles, Properties prop)
			throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();
		Parser p = new Parser();
		for (String file : inputFiles.keySet()) {
			map = p.parse(read(file), map, inputFiles.get(file));
		}
		return map;
	}

	/**
	 * Reads the filenames of the files with names or times in the config-file
	 * and puts them in an hashmap with filenames as keys and file type as
	 * values.
	 * 
	 * @param prop
	 *            contains the values in the config file
	 * @return HashMap with filenames as keys and type of file as value
	 */
	private static Map<String, Parser.FileIdentifier> getInputFiles(
			Properties prop) {
		Map<String, Parser.FileIdentifier> inputFiles = new HashMap<String, Parser.FileIdentifier>();
		if (prop.containsKey("starttimes")) {
			inputFiles.put(prop.getProperty("starttimes"),
					Parser.FileIdentifier.start_file);
		}
		if (prop.containsKey("namefile")) {
			inputFiles.put(prop.getProperty("namefile"),
					Parser.FileIdentifier.name_file);
		}
		String finishPath = "";
		if (prop.containsKey("finishfiles")) {
			finishPath = prop.getProperty("finishfiles");
		}
		String[] finishFiles = finishPath.split(" ");
		for (String s : finishFiles) {
			inputFiles.put(s, Parser.FileIdentifier.finish_file);
		}
		return inputFiles;
	}

	/**
	 * Reads the content of the file
	 * 
	 * @param file
	 * @return an arraylist of strings with all the lines in file
	 * @throws FileNotFoundException
	 */
	private static ArrayList<ArrayList<String>> read(String file)
			throws FileNotFoundException {
		CvsReader reader = new CvsReader(file);

		return reader.readAll();
	}

	/**
	 * Return a printer. Which type depends on the status of 'racetype' in the
	 * config file. If 'racetype' status does not exist in config file, a
	 * StdPrinter is returned. Else if 'racetype' status is set to 'laprace', a
	 * LapPrinter is returned.
	 * 
	 * @param prop
	 *            properties to get status in config file
	 * @return a printer, which type depends on the status in the config file
	 */
	private static Printer getPrinter(Properties prop) {
		Printer printer = null;

		String printerType = prop.getProperty("racetype");

		if (printerType.equals(STANDARD)) {
			printer = new StdPrinter();
		} else if (printerType.equals(LAP_RACE)) {
			printer = new LapPrinter();
		} else if (printerType.equals(BINARY_LAPS)) {
			printer = new BinaryLapPrinter();
		}
		return printer;
	}

	/**
	 * Return a Sortprinter. Which type depends on the status of 'racetype' in
	 * the config file. If 'racetype' status does not exist in config file, a
	 * SortStdPrinter is returned. Else if 'racetype' status is set to
	 * 'laprace', a SortLapPrinter is returned.
	 * 
	 * @param prop
	 *            properties to get status in config file
	 * @return a Sortprinter, which type depends on the status in the config
	 *         file
	 */
	private static Printer getSortPrinter(Properties prop) {
		Printer printer = null;

		String printerType = prop.getProperty("racetype");

		if (printerType.equals(STANDARD)) {
			printer = new SortStdPrinter();
		} else if (printerType.equals(LAP_RACE)) {
			printer = new SortLapPrinter();
		} else if (printerType.equals(BINARY_LAPS)) {
			printer = new SortBinaryLapPrinter();
		}
		return printer;
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
