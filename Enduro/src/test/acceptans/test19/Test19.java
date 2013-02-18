package test.acceptans.test19;

import io.printer.BinaryLapPrinter;
import io.printer.SortBinaryLapPrinter;
import io.printer.SortBinaryLapPrinter;
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

public class Test19 {

	private static final String SORT_RESULT_PATH = "src/test/tmp/test19_sorted.txt";
	private static final String RESULT_PATH = "src/test/tmp/test19.txt";
	private static final String TEST_PATH = "src/test/acceptans/test19/";
	private CvsReader reader;
	private Parser parser;

	@Before
	public void initialize() {

		parser = new Parser();
	}

	/**
	 * Acceptans test for story 19.
	 * 
	 * @throws FileNotFoundException
	 *             If some of the files does not exist.
	 * @throws ParserException
	 *             If input from CvsReader is incorrect.
	 */
	@Test
	public void testResult() throws FileNotFoundException, ParserException {
		Map<Integer, Competitor> competitors;

		reader = new CvsReader(TEST_PATH + "namnfil.txt");
		ArrayList<ArrayList<String>> input = reader.readAll();
		competitors = parser.parse(input);

		reader = new CvsReader(TEST_PATH + "maltider1.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors,
				Parser.FileIdentifier.finish_file);

		reader = new CvsReader(TEST_PATH + "maltider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors,
				Parser.FileIdentifier.finish_file);

		reader = new CvsReader(TEST_PATH + "starttider1.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors,
				Parser.FileIdentifier.start_file);

		reader = new CvsReader(TEST_PATH + "starttider2.txt");
		input = reader.readAll();
		competitors = parser.parse(input, competitors,
				Parser.FileIdentifier.start_file);

		ArrayList<Competitor> list = new ArrayList<Competitor>(
				competitors.values());
		Collections.sort(list);

		BinaryLapPrinter printer = new BinaryLapPrinter();
		printer.printResults(list, RESULT_PATH);
		TestUtil.testResultFiles(TEST_PATH + "resultat.txt", RESULT_PATH);

		Collections.sort(list, new Sorter.CompetitorComparator());

		printer = new SortBinaryLapPrinter();
		printer.printResults(list, SORT_RESULT_PATH);
		TestUtil.testResultFiles(TEST_PATH + "sort_resultat.txt",
				SORT_RESULT_PATH);
	}

}
