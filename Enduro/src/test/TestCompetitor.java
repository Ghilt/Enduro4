package test;

import members.Competitor;
import members.Time;

import org.junit.*;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.SorterMain;
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
	public void allPossibleImpossibles() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(15));
		c.addFinishTime(new Time(16));
		
		assertEquals(Formater.formatColumns(1, c.getName(), new Time(10), new Time(5), new Time(15), StdCompetitorPrinter.MULTIPLE_STARTS + " " + new Time(6) + " " +
				StdCompetitorPrinter.MULTIPLE_ENDS + " " + new Time(16) + " " +
				StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}
	
	
}
