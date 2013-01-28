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

}
