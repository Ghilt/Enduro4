package test.acceptans.test9;

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

		LapPrinter printer = new LapPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				RESULT_PATH);
		TestUtil.testResultFiles("src/test/acceptans/test9/resultat.txt",RESULT_PATH);
	}


}