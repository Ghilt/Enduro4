package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import members.Competitor;
import members.Lap;
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
		c = new Competitor(1);
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
	
	@Test
	public void TestLapHashCodeWithoutFinishTime() {
		Time t = new Time(1233);
		Lap lap = new Lap(t, null);
		assertEquals(31*31 + t.hashCode(), lap.hashCode());
	}
	
	@Test
	public void TestLapHashCodeWithoutStartTime() {
		Time t = new Time(1233);
		Lap lap = new Lap(null, t);
		assertEquals(31 * (31 + t.hashCode()), lap.hashCode());
	}
	
	@Test
	public void TestToString() {
		Time t1 = new Time(1233);
		Time t2 = new Time(43221);
		Lap lap = new Lap(t1, t2);
		assertEquals(lap.toString(), "Lap [start=" + t1 + ", end=" + t2 + ", total=" + t1.difference(t2) + " ]");
	}
	
	
	@Test
	public void TestLapHashCodeWithNullTimes() {
		Lap lap = new Lap(null, null);
		assertEquals(31 * 31, lap.hashCode());
	}

	@Test
	public void TestLapEquals() {
		c.addFinishTime(t2);
		c.addFinishTime(t3);
		c.addFinishTime(t4);
		
		Competitor runar = new Competitor(1);
		t1 = new Time(1233);
		runar.addStartTime(t1);
		runar.addFinishTime(t2);
		runar.addFinishTime(t3);
		runar.addFinishTime(t4);
		Lap runarLap = runar.getLaps().get(0);
		
		Lap lap = c.getLaps().get(0);
		Lap nullLap = null;
		assertTrue(lap.equals(lap));
		assertFalse(lap.equals(nullLap));
		assertFalse(lap.equals(3));
		assertFalse(runarLap ==lap);
		
		assertFalse(lap.equals(new Lap(null, null)));
		
		lap = new Lap(t1, null);
		assertFalse(lap.equals(new Lap(null, null)));
		
		lap = new Lap(null, t1);
		assertFalse(lap.equals(new Lap(null, null)));
		
		lap = new Lap(t1, null);
		runarLap = new Lap(null, t1);
		assertFalse(lap.equals(runarLap));
		
		lap = new Lap(null, t1);
		runarLap = new Lap(t1, t1);
		assertFalse(lap.equals(runarLap));
	}
}
