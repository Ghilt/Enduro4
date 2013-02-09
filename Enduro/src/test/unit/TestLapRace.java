package test.unit;

import static org.junit.Assert.assertEquals;
import members.Competitor;
import members.Time;

import org.junit.Before;
import org.junit.Test;

public class TestLapRace {
	private Competitor c;
	private Time t1, t2, t3, t4;

	@Before
	public void setup() {
		c = new Competitor(1);
		t1 = new Time(1223);
		t2 = new Time(2232);
		t3 = new Time(1500);
		t4 = new Time(2000);
		c.addStartTime(t1);
	}

	@Test
	public void TestNoStartorFinish() {
		Competitor c2 = new Competitor(2);
		assertEquals(0, c2.getFinishTimes().size());
	}

	@Test
	public void TestZeroLaps() {
		c.addFinishTime(t2);
		assertEquals(0, c.getLaps().size());
	}

	@Test
	public void TestOneLap() {
		c.addFinishTime(t3);
		c.addFinishTime(t2);
		assertEquals(t1.difference(t3), c.getLaps().get(0).getTotal());
		assertEquals(t3.difference(t2), c.getLaps().get(1).getTotal());
	}

	@Test
	public void TestNumberOfLaps() {
		c.addFinishTime(t3);
		c.addFinishTime(t4);
		c.addFinishTime(t2);
		assertEquals(3, c.numberOfLaps());
	}

	@Test
	public void TestTwoLaps() {
		c.addFinishTime(t3);
		c.addFinishTime(t4);
		c.addFinishTime(t2);
		assertEquals(t1.difference(t3), c.getLaps().get(0).getTotal());
		assertEquals(t3.difference(t4), c.getLaps().get(1).getTotal());
		assertEquals(t4.difference(t2), c.getLaps().get(2).getTotal());
	}

}
