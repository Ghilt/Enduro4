package test.unit.html;

import io.printer.Converter;
import io.printer.HtmlConverter;
import io.printer.LapPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;

import org.junit.Test;

import test.TestUtil;

public class TestHtmlConverter {
	private static final String OUTPUT = "src/test/tmp/htmltest.html";

	private Converter htmlConv = new HtmlConverter();
	private Parser parser = new Parser();
	
	// Hej Henrik! 
	// Detta kommer att breaka at some point!
	// Mvh Andrée & Philip

	@Test
	public void testLap() throws FileNotFoundException, ParserException {
		testSomeLap("src/test/unit/html/lap/");

	}

	@Test
	public void testLap2() throws FileNotFoundException, ParserException {
		testSomeLap("src/test/unit/html/lap2/");
	}

	private void testSomeLap(String folder) throws FileNotFoundException,
			ParserException {
		Map<Integer, Competitor> competitors = new HashMap<Integer, Competitor>();

		competitors = parser.parse(
				new CvsReader(folder + "starttider.txt").readAll(),
				competitors, Parser.FileIdentifier.start_file);
		competitors = parser.parse(
				new CvsReader(folder + "maltider.txt").readAll(), competitors,
				Parser.FileIdentifier.finish_file);
		competitors = parser.parse(
				new CvsReader(folder + "namnfil.txt").readAll(), competitors,
				Parser.FileIdentifier.name_file);

		LapPrinter printer = new LapPrinter();
		printer.printResults(new ArrayList<Competitor>(competitors.values()),
				OUTPUT, htmlConv);
		TestUtil.testResultFiles(OUTPUT, folder + "expected.html");
	}

}
