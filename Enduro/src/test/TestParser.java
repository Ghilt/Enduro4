package test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;
import members.Time;

import org.junit.Before;
import org.junit.Test;

import result.CvsReader;
import result.Parser;
import result.ParserException;

public class TestParser {

	private Parser parser;
	private ArrayList<ArrayList<String>> input;

	@Before
	public void setUp() {
		parser = new Parser();
		input = new ArrayList<ArrayList<String>>();

	}
	
	@Test (expected=ParserException.class)
	public void testEmptyInput() throws ParserException {
		Map<Integer, Competitor> competitors = parser.parse(input);
	}

	@Test
	public void testParseSimple() throws ParserException {
		ArrayList<String> row1 = new ArrayList<String>();
		row1.add("StartNr");
		row1.add("StartTid");
		
		ArrayList<String> row2 = new ArrayList<String>();
		row2.add("1");
		row2.add(new Time("12.11.10").toString());
		
		ArrayList<String> row3 = new ArrayList<String>();
		row3.add("2");
		row3.add(new Time("05.11.19").toString());
		
		input.add(row1);
		input.add(row2);
		input.add(row3);
		
		Map<Integer, Competitor> competitors = parser.parse(input);
		assertEquals(competitors.get(1).getStartTimes().get(0), new Time("12.11.10"));
		assertEquals(competitors.get(2).getStartTimes().get(0), new Time("05.11.19"));
	}
	
	@Test
	public void testParseComplex() throws ParserException {
		ArrayList<String> row1 = new ArrayList<String>();
		row1.add("StartNr");
		row1.add("StartTid");
		row1.add("MålTid");
		
		ArrayList<String> row2 = new ArrayList<String>();
		row2.add("1");
		row2.add(new Time("12.11.10").toString());
		row2.add(new Time("18.11.10").toString());
		
		ArrayList<String> row3 = new ArrayList<String>();
		row3.add("2");
		row3.add(new Time("05.11.19").toString());
		row3.add(new Time("20.11.19").toString());
		
		input.add(row1);
		input.add(row2);
		input.add(row3);
		
		Map<Integer, Competitor> competitors = parser.parse(input);
		assertEquals(competitors.get(1).getStartTimes().get(0), new Time("12.11.10"));
		assertEquals(competitors.get(1).getFinishTimes().get(0), new Time("18.11.10"));
		
		assertEquals(competitors.get(2).getStartTimes().get(0), new Time("05.11.19"));
		assertEquals(competitors.get(2).getFinishTimes().get(0), new Time("20.11.19"));
	}
	
	@Test
	public void testParseManyFiles() throws ParserException {
		ArrayList<String> row1 = new ArrayList<String>();
		row1.add("StartNr");
		row1.add("StartTid");
		
		ArrayList<String> row2 = new ArrayList<String>();
		row2.add("1");
		row2.add(new Time("12.11.10").toString());
		
		ArrayList<String> row3 = new ArrayList<String>();
		row3.add("2");
		row3.add(new Time("05.11.19").toString());
		
		input.add(row1);
		input.add(row2);
		input.add(row3);
		
		Map<Integer, Competitor> competitors = parser.parse(input);
		
		
		input = new ArrayList<ArrayList<String>>();
		row1 = new ArrayList<String>();
		row1.add("StartNr");
		row1.add("MålTid");
		
		row2 = new ArrayList<String>();
		row2.add("1");
		row2.add(new Time("20.11.10").toString());
		
		row3 = new ArrayList<String>();
		row3.add("2");
		row3.add(new Time("22.11.19").toString());
		
		input.add(row1);
		input.add(row2);
		input.add(row3);
		
		competitors = parser.parse(input, competitors);
		
		assertEquals(competitors.get(1).getStartTimes().get(0), new Time("12.11.10"));
		assertEquals(competitors.get(1).getFinishTimes().get(0), new Time("20.11.10"));
		
		assertEquals(competitors.get(2).getStartTimes().get(0), new Time("05.11.19"));
		assertEquals(competitors.get(2).getFinishTimes().get(0), new Time("22.11.19"));
	}
	
	
	@Test
	public void testMultipleFinishTimes() throws ParserException {
		ArrayList<String> row1 = new ArrayList<String>();
		row1.add("StartNr");
		row1.add("Måltid");
		
		ArrayList<String> row2 = new ArrayList<String>();
		row2.add("1");
		row2.add(new Time("12.11.10").toString());
		
		ArrayList<String> row3 = new ArrayList<String>();
		row3.add("2");
		row3.add(new Time("05.11.19").toString());
		
		ArrayList<String> row4 = new ArrayList<String>();
		row4.add("1");
		row4.add(new Time("13.21.10").toString());
		
		
		ArrayList<String> row5 = new ArrayList<String>();
		row5.add("3");
		row5.add(new Time("14.11.10").toString());
		
		
		ArrayList<String> row6 = new ArrayList<String>();
		row6.add("2");
		row6.add(new Time("14.33.10").toString());
		
		input.add(row1);
		input.add(row2);
		input.add(row3);
		input.add(row4);
		input.add(row5);
		input.add(row6);
		
		Map<Integer, Competitor> competitors = parser.parse(input);
		
		assertEquals(competitors.get(1).getFinishTimes().size(), 2);
		assertEquals(competitors.get(1).getFinishTimes().get(0), new Time("12.11.10"));
		assertEquals(competitors.get(1).getFinishTimes().get(1), new Time("13.21.10"));
		
		assertEquals(competitors.get(2).getFinishTimes().size(), 2);
		assertEquals(competitors.get(2).getFinishTimes().get(0), new Time("05.11.19"));
		assertEquals(competitors.get(2).getFinishTimes().get(1), new Time("14.33.10"));
		
		assertEquals(competitors.get(3).getFinishTimes().size(),1);
		assertEquals(competitors.get(3).getFinishTimes().get(0), new Time("14.11.10"));
		
	}

}
