package test;

import java.util.ArrayList;
import java.util.List;

import members.Competitor;
import members.Lap;
import members.Time;

import org.junit.*;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.ResultCompilerMain;
import sort.StdCompetitorPrinter;
import static org.junit.Assert.*;

public class TestCompetitor {
	
	private Competitor c;
	private CompetitorPrinter cp;
	
	
	@Before
	public void setup() {
		cp = new StdCompetitorPrinter();
		c = new Competitor(1);
		c.addName("Niklas Svensson");
	}
	
	@Test
	public void testGoodTimes() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(Formater.formatColumns(1, c.getName(), new Time("00.00.15").difference(new Time("00.45.00")), new Time("00.00.15"), new Time("00.45.00")), cp.row(c));
	}
	
	@Test
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(Formater.formatColumns(1, c.getName(), Time.NULL_TIME, StdCompetitorPrinter.NO_START, new Time(45)), cp.row(c));
	}
	
	@Test
	public void testBadEnd() {
		c.addStartTime(new Time(10));		
		assertEquals(Formater.formatColumns(1, c.getName(), Time.NULL_TIME, new Time(10), StdCompetitorPrinter.NO_END), cp.row(c));
	}
	
	@Test
	public void testImpossibleTotalTime() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(10));
		assertEquals(Formater.formatColumns(1, c.getName(), new Time(5), new Time(5), new Time(10), StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}

	@Test
	public void testMultipleEndTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time f1 = new Time("00.25.00");
		c.addFinishTime(f1);
		Time f2 = new Time("00.26.00");
		c.addFinishTime(f2);
		
		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1), s1, f1, StdCompetitorPrinter.MULTIPLE_ENDS + " " + f2), cp.row(c));
	}
	
	@Test
	public void testMultipleStartTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time s2 = new Time("00.00.06");
		c.addStartTime(s2);
		Time f1 = new Time("00.20.06");
		c.addFinishTime(f1);
		
		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1), s1, f1, StdCompetitorPrinter.MULTIPLE_STARTS + " " + s2), cp.row(c));
	}
	
	@Test
	public void testAllPossibleImpossibles() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(15));
		c.addFinishTime(new Time(16));
		
		assertEquals(Formater.formatColumns(1, c.getName(), new Time(10), new Time(5), new Time(15), StdCompetitorPrinter.MULTIPLE_STARTS + " " + new Time(6) + " " +
				StdCompetitorPrinter.MULTIPLE_ENDS + " " + new Time(16) + " " +
				StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}
	
	@Test
	public void testCompareTo() {
		Competitor c2 = new Competitor(2);
		Competitor c3 = new Competitor(3);
		
		c.addStartTime(new Time("00.00.10"));
		c2.addStartTime(new Time("00.01.00"));
		c3.addStartTime(new Time("00.02.00"));
		
		c.addFinishTime(new Time("01.00.10"));
		c2.addFinishTime(new Time("02.00.10"));
		c3.addFinishTime(new Time("02.01.10"));
		
		assertEquals(c.compareTo(c2), -1);
		assertEquals(c2.compareTo(c3), 0);
		assertEquals(c3.compareTo(c), 1);
	}
	
	@Test
	public void testGetLaps() {
		List<Lap> laps;
		
		c.addStartTime(new Time("00.01.00"));
		
		c.addFinishTime(new Time("01.00.00"));
		c.addFinishTime(new Time("02.00.00"));
		c.addFinishTime(new Time("03.00.00"));
		c.addFinishTime(new Time("04.00.00"));
		
		laps = c.getLaps();
		
		assertEquals(new Time("00.59.00"), laps.get(0).getTotal());
		assertEquals(new Time("01.00.00"), laps.get(1).getTotal());
		assertEquals(new Time("01.00.00"), laps.get(2).getTotal());
	}
	
	@Test
	public void testEmptyLaps() {
		assertEquals(c.getLaps().size(), 0);
	}
	
	@Test
	public void testFinishTimesSorted() {
		
		c.addStartTime(new Time("00.01.00"));
		
		c.addFinishTime(new Time("03.00.00"));
		c.addFinishTime(new Time("05.00.00"));
		c.addFinishTime(new Time("10.00.00"));
		c.addFinishTime(new Time("01.00.00"));
		
		List<Lap> laps = c.getLaps();
		
		assertEquals(new Time("00.59.00"), laps.get(0).getTotal());
		assertEquals(new Time("02.00.00"), laps.get(1).getTotal());
		assertEquals(new Time("02.00.00"), laps.get(2).getTotal());
		assertEquals(new Time("05.00.00"), laps.get(3).getTotal());
	}
	
	
}
