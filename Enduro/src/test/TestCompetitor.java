package test;

import org.junit.*;

import sort.Competitor;
import sort.Formater;
import sort.SorterMain;
import sort.Time;
import static org.junit.Assert.*;

public class TestCompetitor {
	
	private Competitor c;
	
	
	@Before
	public void setup() {
		c = new Competitor(1);
	}
	
	@Test
	public void testGoodTimes() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(Formater.formatColumns(1, new Time("00.00.15").difference(new Time("00.45.00")), new Time("00.00.15"), new Time("00.45.00") + ";"), c.toString());
	}
	
	@Test
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(Formater.formatColumns(1, Time.NULL_TIME, Competitor.NO_START, new Time(45) + ";"), c.toString());
	}
	
	@Test
	public void testBadEnd() {
		c.addStartTime(new Time(10));		
		assertEquals(Formater.formatColumns(1, Time.NULL_TIME, new Time(10), Competitor.NO_END + ";"), c.toString());
	}
	
	@Test
	public void testImpossibleTotalTime() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(10));
		assertEquals(Formater.formatColumns(1, new Time(5), new Time(5), new Time(10), Competitor.IMPOSSIBLE_TOTAL_TIME), c.toString());
	}

	@Test
	public void testMultipleEndTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time f1 = new Time("00.25.00");
		c.addFinishTime(f1);
		Time f2 = new Time("00.26.00");
		c.addFinishTime(f2);
		
		assertEquals(Formater.formatColumns(1, s1.difference(f1), s1, f1, Competitor.MULTIPLE_ENDS + " " + f2), c.toString());
	}
	
	@Test
	public void testMultipleStartTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time s2 = new Time("00.00.06");
		c.addStartTime(s2);
		Time f1 = new Time("00.20.06");
		c.addFinishTime(f1);
		
		assertEquals(Formater.formatColumns(1, s1.difference(f1), s1, f1, Competitor.MULTIPLE_STARTS + " " + s2), c.toString());
	}
	
	@Test
	public void allPossibleImpossibles() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(15));
		c.addFinishTime(new Time(16));
		
		assertEquals(Formater.formatColumns(1, new Time(10), new Time(5), new Time(15), Competitor.MULTIPLE_STARTS + " " + new Time(6) + " " +
				Competitor.MULTIPLE_ENDS + " " + new Time(16) + " " +
				Competitor.IMPOSSIBLE_TOTAL_TIME), c.toString());
	}
	
	
}
