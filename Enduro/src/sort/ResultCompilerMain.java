package sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	 * 
	 */
	public static void main(String[] args) {
		Parser p = new Parser();
		
		CvsReader startReader = new CvsReader(STARTTIMES);
		CvsReader finishReader = new CvsReader(FINISHTIMES);
		CvsReader nameReader = new CvsReader(NAMEFILE);
		
		Map<Integer, Competitor> map = new HashMap<Integer, Competitor>();
		
		try {
			p.parse(startReader.readAll(), map);
			p.parse(finishReader.readAll(), map);
			p.parse(nameReader.readAll(), map);
			StdCompetitorPrinter printer = new StdCompetitorPrinter();
			printer.printResults(new ArrayList<Competitor>(map.values()), RESULTFILE);
		} catch (FileNotFoundException e) {
			System.exit(-1);
		} catch (ParserException e) {
			System.exit(-1);
		}
	}
	
}
