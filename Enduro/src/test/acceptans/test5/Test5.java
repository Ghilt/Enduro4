package test.acceptans.test5;

import io.printer.StdPrinter;
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

public class Test5 {
	private static final String RESULT_PATH = "src/test/tmp/test5.txt";
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

		reader = new CvsReader("src/test/acceptans/test5/maltider.txt");
		ArrayList<ArrayList<String>> input = reader.readAll();
		competitors = parser.parse(input);

		reader = new CvsReader("src/test/acceptans/test5/namnfil.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		reader = new CvsReader("src/test/acceptans/test5/starttider.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors);

		StdPrinter printer = new StdPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				RESULT_PATH);
		TestUtil.testResultFiles(RESULT_PATH,
				"src/test/acceptans/test5/resultat.txt");
	}

}
