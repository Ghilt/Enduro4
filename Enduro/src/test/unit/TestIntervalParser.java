package test.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.reader.IntervalParser;
import io.reader.IntervalParser.Interval;

import org.junit.Test;

public class TestIntervalParser {
	private IntervalParser p;
	private Interval interval;

	@Test
	public void testSingle() {
		p = new IntervalParser("1");

		assertEquals(new IntervalParser.Interval(1), p.getIntervals().get(0));
	}

	@Test
	public void testGetters() {
		interval = new Interval(1,2);
		
		
		assertEquals(2, interval.size());
		assertEquals(1, interval.getStart());
		assertEquals(2, interval.getEnd());
		assertArrayEquals(new Integer[] {1,2}, interval.getNumbers().toArray());
	}
	
	@Test
	public void testHashCode() {
		interval = new Interval(1,2);

		assertEquals(1024, interval.hashCode());
	}
	
	@Test
	public void testEquals() {
		interval = new Interval(1,3);
		Interval interval2 = new Interval(5,9);
		Interval interval3 = new Interval(2,3);
		Interval nullInterval = null;
		int a = 1;
		
		assertFalse(interval.equals(interval2));
		assertFalse(interval.equals(interval3));
		assertFalse(interval.equals(nullInterval));
		assertTrue(interval.equals(interval));
		assertFalse(interval.equals(a));

	}
	
	
	@Test
	public void testMultipleSingles() {
		p = new IntervalParser("1,2");

		assertArrayEquals(
				new IntervalParser.Interval[] { new IntervalParser.Interval(1),
						new IntervalParser.Interval(2) }, p.getIntervals()
						.toArray());
	}

	@Test
	public void testRange() {
		p = new IntervalParser("1-4");

		assertArrayEquals(
				new IntervalParser.Interval[] { new IntervalParser.Interval(1,
						4) }, p.getIntervals().toArray());
	}

	@Test
	public void testMultipleRanges() {
		p = new IntervalParser("1-4,6-18");

		assertArrayEquals(new IntervalParser.Interval[] {
				new IntervalParser.Interval(1, 4),
				new IntervalParser.Interval(6, 18) }, p.getIntervals()
				.toArray());
	}

	@Test
	public void testmixed() {
		p = new IntervalParser("1-4,6-18,22,55,112-166");

		assertArrayEquals(new IntervalParser.Interval[] {
				new IntervalParser.Interval(1, 4),
				new IntervalParser.Interval(6, 18),
				new IntervalParser.Interval(22),
				new IntervalParser.Interval(55),
				new IntervalParser.Interval(112, 166) }, p.getIntervals()
				.toArray());
	}
	
	@Test
	public void testWeirdRange() {
		p = new IntervalParser("4-1");

		assertArrayEquals(
				new IntervalParser.Interval[] { new IntervalParser.Interval(1,
						4) }, p.getIntervals().toArray());		
	}

	@Test
	public void testTrailingComma() {
		p = new IntervalParser("1-4,");
		assertTrue(!p.isValid());
		assertTrue(p.getIntervals().isEmpty());
	}
	
	@Test
	public void testDoubleRange() {
		p = new IntervalParser("1-4-");
		assertTrue(!p.isValid());
		assertTrue(p.getIntervals().isEmpty());
	}
	
	@Test
	public void testDoubleRange2() {
		p = new IntervalParser("1--4");
		assertTrue(!p.isValid());
		assertTrue(p.getIntervals().isEmpty());
	}
	
	
}
