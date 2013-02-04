package sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;
import result.CvsReader;
import result.Parser;
import result.ParserException;


public class ResultCompilerMain {
	
	public static final String STARTTIMES = "start.txt";
	public static final String NAMEFILE = "namn.txt";
	public static final String FINISHTIMES = "slut.txt";
	public static final String RESULTFILE = "resultat.txt";
	
	/**

	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * @throws URISyntaxException 
	 * 
	 */
	public static void main(String[] args) throws URISyntaxException {
		CodeSource codeSource = ResultCompilerMain.class.getProtectionDomain().getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();
		
		Parser p = new Parser();
		
		CvsReader startReader = new CvsReader(jarDir + File.separator + STARTTIMES);
		CvsReader finishReader = new CvsReader(jarDir + File.separator + FINISHTIMES);
		CvsReader nameReader = new CvsReader(jarDir + File.separator + NAMEFILE);
		
		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();
		
		try {
			p.parse(startReader.readAll(), map);
			p.parse(finishReader.readAll(), map);
			p.parse(nameReader.readAll(), map);
			StdCompetitorPrinter printer = new StdCompetitorPrinter();
			printer.printResults(new ArrayList<Competitor>(map.values()), jarDir + File.separator + RESULTFILE);
		} catch (FileNotFoundException e) {
			System.exit(-1);
		} catch (ParserException e) {
			System.exit(-1);
		}
	}
	
}
