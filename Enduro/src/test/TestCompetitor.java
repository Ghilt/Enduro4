package test;

import org.junit.*;

import sort.Competitor;
import sort.Sorter;
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
		
		assertEquals(Sorter.formatColumns("1", "00;00;35", "00;00;10", "00;00;45"), c.toString());
	}

}
