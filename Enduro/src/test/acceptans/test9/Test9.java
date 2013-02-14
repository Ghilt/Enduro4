package test.acceptans.test9;

import static org.junit.Assert.assertEquals;

import io.printer.LapCompetitorPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import members.Competitor;

import org.junit.Before;
import org.junit.Test;



public class Test9 {
	private static final String RESULT_PATH = "src/test/tmp/test9.txt";
	private Parser parser;

	@Before
	public void initialize() {
		parser = new Parser();
	}

	@Test
	public void testResult() throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> competitors;

		competitors = parser.parse(new CvsReader(
				"src/test/acceptans/test9/starttider.txt").readAll());
		competitors = parser.parse(
				new CvsReader("src/test/acceptans/test9/maltider.txt").readAll(),
				competitors);
		competitors = parser.parse(new CvsReader("src/test/acceptans/test9/namnfil.txt").readAll(),
				competitors);

		LapCompetitorPrinter printer = new LapCompetitorPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				RESULT_PATH);
		testResultFiles();
	}

	private void testResultFiles() throws FileNotFoundException {
		File file1 = new File("src/test/acceptans/test9/resultat.txt");
		File file2 = new File(RESULT_PATH);
		Scanner scan1 = new Scanner(file1);
		Scanner scan2 = new Scanner(file2);
		String line1, line2;
		while (scan1.hasNext() && scan2.hasNext()) {
			line1 = scan1.nextLine();
			line2 = scan2.nextLine();
			assertEquals("Wrong result.", line1, line2);
		}
	}

}