package test;

import org.junit.*;

import sort.Competitor;
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
		c.addStartTime(new Time(10));
		c.addFinishTime(new Time(45));
		assertEquals(SorterMain.formatColumns(1, new Time(35), new Time(10), new Time(45)), c.toString());
	}
	
	@Test
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(SorterMain.formatColumns(1, Time.NULL_TIME, Competitor.NO_START, new Time(45)), c.toString());
	}
	
	@Test
	public void testBadEnd() {
		c.addStartTime(new Time(10));		
		assertEquals(SorterMain.formatColumns(1, Time.NULL_TIME, new Time(10), Competitor.NO_END), c.toString());
	}
	
	@Test
	public void testImpossibleTotalTime() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(10));
		assertEquals(SorterMain.formatColumns(1, new Time(5), new Time(5), new Time(10), Competitor.IMPOSSIBLE_TOTAL_TIME), c.toString());
	}

	@Test
	public void testMultipleEndTimes() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(25));
		c.addFinishTime(new Time(26));
		
		assertEquals(SorterMain.formatColumns(1, new Time(20), new Time(5), new Time(25), Competitor.MULTIPLE_ENDS + " " + new Time(26)), c.toString());
	}
	
	@Test
	public void testMultipleStartTimes() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(26));
		
		assertEquals(SorterMain.formatColumns(1, new Time(21), new Time(5), new Time(26), Competitor.MULTIPLE_STARTS + " " + new Time(6)), c.toString());
	}
	
	@Test
	public void allPossibleImpossibles() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(15));
		c.addFinishTime(new Time(16));
		
		assertEquals(SorterMain.formatColumns(1, new Time(10), new Time(5), new Time(15), Competitor.MULTIPLE_STARTS + " " + new Time(6),
				Competitor.MULTIPLE_ENDS + " " + new Time(16),
				Competitor.IMPOSSIBLE_TOTAL_TIME), c.toString());
	}
	
	
}
