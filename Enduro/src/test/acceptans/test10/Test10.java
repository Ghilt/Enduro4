package test.acceptans.test10;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import members.Competitor;

import org.junit.Before;
import org.junit.Test;

import result.CvsReader;
import result.Parser;
import result.ParserException;
import sort.LapCompetitorPrinter;

public class Test10 {
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

		LapCompetitorPrinter printer = new LapCompetitorPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				"src/test/acceptans/test10/resultat_2.txt");
		testResultFiles();
	}

	private void testResultFiles() throws FileNotFoundException {
		File file1 = new File("src/test/acceptans/test10/resultat.txt");
		File file2 = new File("src/test/acceptans/test10/resultat_2.txt");
		Scanner scan1 = new Scanner(file1);
		Scanner scan2 = new Scanner(file2);
		String line1, line2;
		while (scan1.hasNext() && scan2.hasNext()) {
			line1 = scan1.nextLine();
			line2 = scan2.nextLine();
			assertEquals("", line1, line2);
		}
	}

}
