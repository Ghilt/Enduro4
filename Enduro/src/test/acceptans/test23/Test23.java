package test.acceptans.test23;

import io.printer.BinaryLapPrinter;
import io.printer.LapPrinter;
import io.printer.SortBinaryLapPrinter;
import io.printer.SortLapPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;
import members.Sorter;

import org.junit.Before;
import org.junit.Test;

import test.TestUtil;

public class Test23 {
	
	private static final String SORT_RESULT_PATH = "src/test/tmp/test23_sorted.txt";
	private static final String RESULT_PATH = "src/test/tmp/test23.txt";
	private CvsReader reader;
	private Parser parser;

	@Before
	public void initialize() {

		parser = new Parser();
	}

	/**
	 * Reads names from namnfil.txt, start times from starttider.txt and finish
	 * times from maltider.txt. Parses everything and prints result to
	 * resultat_2.txt and finally compares the expected output, result.txt with
	 * our generated results in resultat_2.txt.
	 * 
	 * @throws FileNotFoundException
	 *             If some of the files does not exist.
	 * @throws ParserException
	 *             If input from CvsReader is incorrect.
	 */
	@Test
	public void testResult() throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> competitors = new HashMap<Integer, Competitor>();

		reader = new CvsReader("src/test/acceptans/test23/maltider1.txt");
		ArrayList<ArrayList<String>> input = reader.readAll();
		competitors = parser.parse(input, competitors, Parser.FileIdentifier.finish_file);

		reader = new CvsReader("src/test/acceptans/test23/maltider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors, Parser.FileIdentifier.finish_file);

		reader = new CvsReader("src/test/acceptans/test23/namnfil.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors, Parser.FileIdentifier.name_file);

		reader = new CvsReader("src/test/acceptans/test23/starttider1.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors, Parser.FileIdentifier.start_file);
		
		reader = new CvsReader("src/test/acceptans/test23/starttider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors, Parser.FileIdentifier.start_file);

		ArrayList<Competitor> list = new ArrayList<Competitor>(competitors.values());
		Collections.sort(list);
		
		BinaryLapPrinter printer = new BinaryLapPrinter();
		printer.printResults(list,
				RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test23/resultat.txt", RESULT_PATH);
		
		
		Collections.sort(list, new Sorter.CompetitorBinaryComparator());
		
		printer = new SortBinaryLapPrinter();
		printer.printResults(list,
				SORT_RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test23/sortresultat.txt", SORT_RESULT_PATH);
	}

	
}
