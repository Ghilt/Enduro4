package main;

import io.Formater;
import io.printer.BinaryLapPrinter;
import io.printer.Converter;
import io.printer.HtmlConverter;
import io.printer.LapPrinter;
import io.printer.NullConverter;
import io.printer.Printer;
import io.printer.SortBinaryLapPrinter;
import io.printer.SortLapPrinter;
import io.printer.SortStdPrinter;
import io.printer.StdPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.Parser.FileIdentifier;
import io.reader.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import members.Competitor;
import members.Sorter;

public class InputFileHandler {

	private static final String CFG_NAMEFILES = "namefiles";
	private static final String CFG_FINISHFILES = "finishfiles";
	private static final String CFG_STARTFILES = "startfiles";
	private static final String CFG_RESULTFILE = "resultfile";
	private static final String CFG_RACETYPE = "racetype";
	private static final String CFG_HTMLRESULTFILE = "htmlresultfile";
	private static final String CFG_HTML = "html";
	private static final String CFG_SORTEDRESULTFILE = "sortedresultfile";
	private static final String CFG_SORTED = "sorted";
	public static final String CFG_NUMBER_BINARY = "antalstationer";

	public final static String STARTTIMES = "start";
	public final static String NAMEFILE = "namn";
	public final static String FINISHTIMES = "slut";
	public final static String RESULTFILE = "resultat";
	public final static String SORTRESULTFILE = "sortresultat";
	public final static String EXTENSION = ".txt";
	private final static String LAP_RACE = "laprace";
	private final static String STANDARD = "standard";
	private final static String BINARY_LAPS = "etapprace";
	public final static String YES = "yes";
	public final static String NO = "no";

	private String jarDir;

	public InputFileHandler(String jarDir) {
		this.jarDir = jarDir;
	}

	/**
	 * Reads the filenames of the files with names or times in the config-file
	 * and puts them in a list
	 * 
	 * @param prop
	 *            contains the values in the config file
	 * @return HashMap with filenames as keys and type of file as value
	 * @throws IOException
	 */
	public List<FileHeader> getInputFiles(Properties prop) throws IOException {

		List<FileHeader> inputFiles = new ArrayList<FileHeader>();

		addInputFile(prop, CFG_NAMEFILES, inputFiles,
				Parser.FileIdentifier.name_file, Competitor.NO_STATION);

		if (prop.containsKey(CFG_NUMBER_BINARY)
				&& prop.getProperty(CFG_RACETYPE).equalsIgnoreCase(BINARY_LAPS)) {
			addBinaryLapInfo(prop, inputFiles);

		} else {
			addDefaultInfo(prop, inputFiles);
		}

		return inputFiles;
	}

	/**
	 * Adds the filenames to the list
	 * 
	 * @param prop
	 *            contains the values in the config file property the filename
	 *            we are currently adding inputFiles the list of file and
	 *            headers fileIdentity type of the file
	 * @return ArrayList with files and headers
	 * @throws IOException
	 *             if the config property is missing
	 */
	private void addInputFile(Properties prop, String property,
			List<FileHeader> inputFiles, FileIdentifier fileIdentity,
			int stationNr) throws IOException {

		String startPath = "";
		if (prop.containsKey(property)) {
			startPath = prop.getProperty(property);
		} else {
			throw new IOException("Property " + property + " is missing,"
					+ Formater.LINE_BREAK + "or config is not found.");
		}
		String[] startFiles = startPath.split(" ");
		for (String s : startFiles) {
			s = jarDir + s;
			if (!new File(s).exists()) {
				throw new FileNotFoundException("Filepath: " + s
						+ " not found.");
			}
			inputFiles.add(new FileHeader(s, stationNr, fileIdentity));
		}
	}

	private void addBinaryLapInfo(Properties prop, List<FileHeader> inputFiles)
			throws IOException {
		try {
			int e = 1;
			e = Integer.parseInt(prop.getProperty(CFG_NUMBER_BINARY));
			for (int i = 1; i <= e; i++) {
				addInputFile(prop, CFG_STARTFILES + "_" + i, inputFiles,
						Parser.FileIdentifier.start_file, i);
				addInputFile(prop, CFG_FINISHFILES + "_" + i, inputFiles,
						Parser.FileIdentifier.finish_file, i);
			}
		} catch (NumberFormatException e1) {
			throw e1;
		}
	}

	private void addDefaultInfo(Properties prop, List<FileHeader> inputFiles)
			throws IOException {
		addInputFile(prop, CFG_STARTFILES, inputFiles,
				Parser.FileIdentifier.start_file, Competitor.NO_STATION);
		addInputFile(prop, CFG_FINISHFILES, inputFiles,
				Parser.FileIdentifier.finish_file, Competitor.NO_STATION);
	}

	/**
	 * Parses the content of each file in the list of inputfiles to the list of
	 * competitors.
	 * 
	 * @param inputFiles
	 *            list of inputfiles nad their headers
	 * @param prop
	 * @return a hashmap with the competitors information
	 * @throws FileNotFoundException
	 * @throws ParserException
	 */
	public Map<Integer, Competitor> parseInputFiles(
			List<FileHeader> inputFiles, Properties prop)
			throws FileNotFoundException, ParserException {

		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();

		Parser p = new Parser();
		for (FileHeader header : inputFiles) {
			p.setStationNr(header.station);
			map = p.parse(new CvsReader(header.file).readAll(), map, header.id);

		}
		return map;
	}

	/**
	 * Return a printer. Appropriate to what type of race is used.
	 */
	private Printer getPrinter(Properties prop) {
		Printer printer = null;

		String printerType = prop.getProperty(CFG_RACETYPE);

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
	 * Return a printer for sorted result files. Appropriate to what type of
	 * race is used.
	 */
	private Printer getSortPrinter(Properties prop) {
		Printer printer = null;

		String printerType = prop.getProperty(CFG_RACETYPE);

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
	public void printResults(Properties prop, ArrayList<Competitor> competitors) {
		String filepath = jarDir + prop.getProperty(CFG_RESULTFILE);
		Printer printer = getPrinter(prop);
		Sorter sorter = new Sorter();
		sorter.sortList(false, competitors, "");
		printer.printResults(competitors, filepath);

		print(prop, CFG_SORTED, CFG_SORTEDRESULTFILE, competitors, sorter,
				new NullConverter());
		print(prop, CFG_HTML, CFG_HTMLRESULTFILE, competitors, sorter,
				new HtmlConverter());
	}

	private void print(Properties prop, String printType, String resultfile,
			ArrayList<Competitor> competitors, Sorter sorter, Converter conv) {
		Printer printer;
		if (prop.containsKey(printType) && prop.get(printType).equals(YES)
				&& prop.containsKey(resultfile)) {
			String resultfilepath = jarDir + prop.getProperty(resultfile);
			printer = getSortPrinter(prop);
			sorter.sortList(true, competitors, prop.getProperty(CFG_RACETYPE));
			printer.printResults(competitors, resultfilepath, conv);
		}
	}

	/**
	 * A class for holding several variables for use in this class.
	 */
	public static class FileHeader {
		@Override
		public String toString() {
			return "FileHeader [file=" + file + ", id=" + id + ", station="
					+ station + "]";
		}

		public FileHeader(String filePath, int stationNr,
				FileIdentifier fileIdentity) {
			id = fileIdentity;
			station = stationNr;
			file = filePath;
		}

		String file;
		FileIdentifier id;
		int station;
	}
}
