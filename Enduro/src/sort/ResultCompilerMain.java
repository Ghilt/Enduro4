package sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
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
	public static final String EXTENSION = ".txt";
	private final static String LAP_RACE = "laprace";
	private final static String STANDARD = "standard";
	private final static String YES = "yes";
	private final String NO = "no";
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
    		//save properties to project root folder
    		prop.store(new FileOutputStream("config.properties"), null);
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		
		
		
		// Detects the location of the executable.
		CodeSource codeSource = ResultCompilerMain.class.getProtectionDomain()
				.getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();

		Parser p = new Parser();

		String startPath = jarDir + File.separator + STARTTIMES + EXTENSION;
		String namePath = jarDir + File.separator + NAMEFILE + EXTENSION;
		String finishPathPart = jarDir + File.separator + FINISHTIMES;
		String resultPath = jarDir + File.separator + RESULTFILE + EXTENSION;

		CvsReader startReader = new CvsReader(startPath);
		CvsReader nameReader = new CvsReader(namePath);

		// Reads multiple end files for laps.
		ArrayList<CvsReader> endReaderList = new ArrayList<CvsReader>();
		for (int i = 0; new File(finishPathPart + i + EXTENSION).exists(); i++) {
			endReaderList.add(new CvsReader(finishPathPart + i + EXTENSION));
		}

		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();

		try {
			// Read starts.
			map = p.parse(startReader.readAll(), map);
			// Read ends
			for (CvsReader end : endReaderList) {
				map = p.parse(end.readAll(), map);
			}
			// Read Names
			map = p.parse(nameReader.readAll(), map);

			ArrayList<Competitor> list = new ArrayList<Competitor>(map.values());
			
			sortList(prop, list);
			
			Printer printer = getPrinter(prop);
			
			printer.printResults(list,
					resultPath);
			
			
		} catch (FileNotFoundException e) {
			errorMessage(e.getMessage());
		} catch (ParserException e) {
			errorMessage(e.getMessage());
			System.exit(-1);
		}
	}

	private static void sortList(Properties prop, ArrayList<Competitor> list) {
		if(prop.contains("sorted") && prop.getProperty("sorted").equals(YES)) {
//			Collection.sort(list, new ...)
		} else {
			Collections.sort(list);
		}
	}

	private static Printer getPrinter(Properties prop) {
		Printer printer = null;
		
		String printerType = prop.getProperty("racetype");
		
		if(printerType.equals(STANDARD)) {
			printer = new StdCompetitorPrinter();
		} else if (printerType.equals(LAP_RACE)) {
			printer = new LapCompetitorPrinter();
			
//			if (prop.containsKey("sorted")
//					&& prop.getProperty("sorted").equals(YES)) {
//				printer = new SortCompetitorPrinter();
//			}
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
