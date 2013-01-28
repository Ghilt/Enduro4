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
		
		assertEquals(Sorter.formatColumns("1", "0;00;35", "0;00;10", "0;00;45"), c.toString());
	}

}
