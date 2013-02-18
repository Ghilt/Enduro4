package test.acceptans.test16;

import io.printer.LapPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;

import org.junit.Before;
import org.junit.Test;

import test.TestUtil;

public class Test16 {

	private static final String RESULT_PATH = "src/test/tmp/test16.txt";
	private Parser parser;

	@Before
	public void initialize() {
		parser = new Parser();
	}

	@Test
	public void testResult() throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> competitors = new HashMap<Integer, Competitor>();

		competitors = parser.parse(new CvsReader(
				"src/test/acceptans/test16/starttider.txt").readAll(), competitors, Parser.FileIdentifier.start_file);
		competitors = parser.parse(new CvsReader(
				"src/test/acceptans/test16/maltider.txt").readAll(),
				competitors, Parser.FileIdentifier.finish_file);
		competitors = parser
				.parse(new CvsReader("src/test/acceptans/test16/namnfil.txt")
						.readAll(), competitors, Parser.FileIdentifier.name_file);

		LapPrinter printer = new LapPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				RESULT_PATH);
		TestUtil.testResultFiles(RESULT_PATH,
				"src/test/acceptans/test16/resultat.txt");
	}

}
