package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import members.Time;

import org.junit.Test;

public class TestTime {

	@Test
	public void testTimeDifference() {
		Time t1 = new Time(10);
		Time t2 = new Time(15);
		Time df = new Time(5);

		assertEquals(t1.difference(t2), df);
	}

	@Test
	public void testTimeDifferenceOverMidnight() {
		Time t1 = new Time(86000);
		Time t2 = new Time(400);
		Time df = new Time(800);

		assertEquals(t1.difference(t2), df);
	}

	@Test
	public void testCreateObject() {
		assertFalse(new Time(5000) == null);
	}

	@Test
	public void testConvertFromStringSimple() {
		assertEquals(new Time(3600), new Time("01.00.00"));
	}

	@Test
	public void testConvertFromStringComplicated() {
		assertEquals(new Time(19381), new Time("05.23.01"));
	}

	@Test
	public void testConvertFromInvalidStringGives0() {
		assertEquals(new Time(0), new Time("bajs"));
	}

	@Test
	public void testPrintSimpleTime() {
		assertEquals("01.00.00", new Time(3600).toString());
	}

	@Test
	public void testPrintComplicatedTime() {
		assertEquals("05.23.01", new Time(19381).toString());
	}

	@Test
	public void testTimeCompareTo() {
		assertEquals(-1, new Time(100).compareTo(new Time(200)));
	}

}
