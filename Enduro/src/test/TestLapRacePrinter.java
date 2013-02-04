package test;

import java.util.List;

import members.Competitor;
import members.Lap;
import members.Time;

import org.junit.*;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.LapCompetitorPrinter;
import sort.StdCompetitorPrinter;
import static org.junit.Assert.*;

public class TestLapRacePrinter {
	
	private Competitor c;
	private CompetitorPrinter cp;
	
	
	@Before
	public void setup() {
		c = new Competitor(1);
		cp = new LapCompetitorPrinter();
	}
	
	@Test
	public void testOneLap() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(Formater.formatColumns(1, c.getName(), 1 , new Time("00.00.15").difference(new Time("00.45.00")), new Time("00.00.15"), new Time("00.45.00")), cp.row(c));
	}
	
	@Test
	public void emptyLaps() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(c.getLaps().size(), 0);
	}
	
	@Test
	public void manyLaps() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.20.00"));
		c.addFinishTime(new Time("00.35.00"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(Formater.formatColumns(1, c.getName(), 3, new Time("00.00.15").difference(new Time("00.45.00")),  new Time("00.19.45"), new Time("00.15.00"), new Time("00.10.00"), new Time("00.00.15"),new Time("00.20.00"),new Time("00.35.00"), new Time("00.45.00")), cp.row(c));
	}
	
	@Ignore
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(Formater.formatColumns(1, c.getName(),c.getNumberOfLaps(), Time.NULL_TIME,  c.getLaps().get(0).getTotal(),StdCompetitorPrinter.NO_START, new Time(45)), cp.row(c));
	}
	
	@Ignore
	public void testBadEnd() {
		c.addStartTime(new Time(10));		
		assertEquals(Formater.formatColumns(1,c.getName(),  Time.NULL_TIME, new Time(10), StdCompetitorPrinter.NO_END + ";"), cp.row(c));
	}
	
	@Ignore
	public void testImpossibleTotalTime() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(10));
		assertEquals(Formater.formatColumns(1, c.getName(), new Time(5), new Time(5), new Time(10), StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}

	@Ignore
	public void testMultipleEndTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time f1 = new Time("00.25.00");
		c.addFinishTime(f1);
		Time f2 = new Time("00.26.00");
		c.addFinishTime(f2);
		
		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1), s1, f1, StdCompetitorPrinter.MULTIPLE_ENDS + " " + f2), cp.row(c));
	}
	
	@Ignore
	public void testMultipleStartTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time s2 = new Time("00.00.06");
		c.addStartTime(s2);
		Time f1 = new Time("00.20.06");
		c.addFinishTime(f1);
		
		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1), s1, f1, StdCompetitorPrinter.MULTIPLE_STARTS + " " + s2), cp.row(c));
	}
	
	@Ignore
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
