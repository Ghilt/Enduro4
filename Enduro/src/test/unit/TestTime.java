package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import members.NullTime;
import members.Time;

import org.junit.Test;

public class TestTime {

	@Test
	public void testTimeDifference() {
		Time t1 = Time.parse(0, "00.01.00");
		Time t2 = Time.parse(0, "01.11.00");
		Time df = Time.parse(0, "01.10.00");

		Time ddddd = t1.difference(t2);
		assertEquals(df, ddddd);
	}

	@Test
	public void testBiggerTimeDiff() {
		Time t1 = Time.parse(0, "00.20.15");
		Time t2 = Time.parse(0, "01.45.00");
		Time df = Time.parse(0, "01.24.45");

		Time ddddd = t1.difference(t2);
		assertEquals(df, ddddd);
	}

	@Test
	public void testAnotherBiggerTimeDiff() {
		Time t1 = Time.parse("00.00.15");
		Time t2 = Time.parse("00.45.00");
		Time df = Time.parse("00.44.45");

		Time ddddd = t1.difference(t2);
		assertEquals(df, ddddd);
		assertEquals("00.44.45", df.toString());
	}

	@Test
	public void testTimeDifferenceOverMidnight() {
		Time t1 = Time.parse(1, "00.01.00");
		Time t2 = Time.parse(2, "00.01.00");
		Time df = Time.parse(1, "00.00.00");

		assertEquals(df, t1.difference(t2));
	}

	@Test
	public void testAnotherDifferenceTime() {
		Time t1 = Time.parse(1, "12.00.00");
		Time t2 = Time.parse(2, "13.23.34");
		Time df = Time.parse(1, "01.23.34");

		assertEquals(df, t1.difference(t2));
	}

	@Test
	public void testAdd() {
		Time t1 = Time.parse(0, "12.00.00");
		Time t2 = Time.parse(0, "01.24.25");
		Time df = Time.parse(0, "13.24.25");

		t1.add(t2);
		assertEquals(df, t1);
	}

	@Test
	public void testAddDay() {
		Time t1 = Time.parse(1, "12.00.00");
		Time t2 = Time.parse(1, "01.24.25");
		Time df = Time.parse(2, "13.24.25");

		t1.add(t2);
		assertEquals(df, t1);
	}

	@Test
	public void testAddSimple() {
		Time t1 = Time.parse(0, "00.10.00");
		Time t2 = Time.parse(0, "00.20.00");
		Time df = Time.parse(0, "00.30.00");

		t1.add(t2);
		assertEquals(df, t1);
	}

	@Test
	public void testCreateObject() {
		assertFalse(new Time(5000) == null);
	}

	@Test
	public void testConvertFromStringSimple() {
		assertEquals(new Time(3600), Time.parse("01.00.00"));
	}

	@Test
	public void testConvertFromStringComplicated() {
		assertEquals(new Time(19381), Time.parse("05.23.01"));
	}

	@Test
	public void testConvertFromInvalidStringGives0() {
		assertEquals(new NullTime(), Time.parse("bajs"));
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
		assertTrue(new Time(100).compareTo(new Time(200)) < 0);
	}

}
