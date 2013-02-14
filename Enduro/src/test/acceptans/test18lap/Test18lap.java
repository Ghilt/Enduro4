package test.acceptans.test18lap;

import io.printer.LapCompetitorPrinter;
import io.printer.SortLapCompetitorPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import members.Competitor;
import members.Sorter;

import org.junit.Before;
import org.junit.Test;

import test.TestUtil;



public class Test18lap {

	private static final String SORT_RESULT_PATH = "src/test/tmp/test18_lap_sort.txt";
	private static final String RESULT_PATH = "src/test/tmp/test18_lap.txt";
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
		Map<Integer, Competitor> competitors;

		reader = new CvsReader("src/test/acceptans/test18lap/maltider1.txt");
		ArrayList<ArrayList<String>> input = reader.readAll();
		competitors = parser.parse(input);

		reader = new CvsReader("src/test/acceptans/test18lap/maltider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		reader = new CvsReader("src/test/acceptans/test18lap/namnfil.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		reader = new CvsReader("src/test/acceptans/test18lap/starttider.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		ArrayList<Competitor> list = new ArrayList<Competitor>(competitors.values());
		Collections.sort(list);
		
		LapCompetitorPrinter printer = new LapCompetitorPrinter();
		printer.printResults(list,
				RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test18lap/resultat.txt", RESULT_PATH);
		
		
		Collections.sort(list, new Sorter.CompetitorComparator());
		
		printer = new SortLapCompetitorPrinter();
		printer.printResults(list,
				SORT_RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test18lap/sortresultat.txt", SORT_RESULT_PATH);
	}

	
}
