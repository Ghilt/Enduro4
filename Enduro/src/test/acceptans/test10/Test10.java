package test.acceptans.test10;

import io.printer.LapPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import members.Competitor;

import org.junit.Before;
import org.junit.Test;

import test.TestUtil;

public class Test10 {
	private static final String RESULT_PATH = "src/test/tmp/test10.txt";
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

		reader = new CvsReader("src/test/acceptans/test10/maltider1.txt");
		ArrayList<ArrayList<String>> input = reader.readAll();
		competitors = parser.parse(input);

		reader = new CvsReader("src/test/acceptans/test10/maltider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		reader = new CvsReader("src/test/acceptans/test10/namnfil.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		reader = new CvsReader("src/test/acceptans/test10/starttider.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		LapPrinter printer = new LapPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test10/resultat.txt",
				RESULT_PATH);
	}

}
