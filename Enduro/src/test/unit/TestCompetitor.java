package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import members.Competitor;
import members.Lap;
import members.NullTime;
import members.Time;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.StdCompetitorPrinter;

public class TestCompetitor {

	private Competitor c;
	private CompetitorPrinter cp;

	@Before
	public void setup() {
		cp = new StdCompetitorPrinter();
		c = new Competitor(1);
	}

	@Test
	public void testGoodTimes() {
		Time s = Time.parse("00.00.15"), f = Time.parse("00.45.00");
		c.addStartTime(s);
		c.addFinishTime(f);
		assertEquals(
				Formater.formatColumns(1, c.getName(), s.difference(f), s, f),
				cp.row(c));
	}

	@Test
	public void testBadStart() {
		Time f = new Time(45);
		c.addFinishTime(f);
		assertEquals(Formater.formatColumns(1, c.getName(),
				new NullTime().toString(), StdCompetitorPrinter.NO_START, f),
				cp.row(c));
	}

	@Test
	public void testBadEnd() {
		Time s = new Time(10);
		c.addStartTime(s);
		assertEquals(Formater.formatColumns(1, c.getName(),
				new NullTime().toString(), s, StdCompetitorPrinter.NO_END),
				cp.row(c));
	}

	@Test
	public void testImpossibleTotalTime() {
		Time s = new Time(5), f = new Time(10);
		c.addStartTime(s);
		c.addFinishTime(f);
		assertEquals(Formater.formatColumns(1, c.getName(), s, s, f,
				StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}

	@Test
	public void testMultipleEndTimes() {
		Time s1 = Time.parse("00.00.05");
		c.addStartTime(s1);
		Time f1 = Time.parse("00.25.00");
		c.addFinishTime(f1);
		Time f2 = Time.parse("00.26.00");
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1),
				s1, f1, StdCompetitorPrinter.MULTIPLE_ENDS + " " + f2),
				cp.row(c));
	}

	@Test
	public void testMultipleStartTimes() {
		Time s1 = Time.parse("00.00.05");
		c.addStartTime(s1);
		Time s2 = Time.parse("00.00.06");
		c.addStartTime(s2);
		Time f1 = Time.parse("00.20.06");
		c.addFinishTime(f1);

		assertEquals(Formater.formatColumns(1, c.getName(), s1.difference(f1),
				s1, f1, StdCompetitorPrinter.MULTIPLE_STARTS + " " + s2),
				cp.row(c));
	}

	@Test
	public void testAllPossibleImpossibles() {
		Time s1 = new Time(5), s2 = new Time(6), f1 = new Time(15), f2 = new Time(
				16);
		c.addStartTime(s1);
		c.addStartTime(s2);
		c.addFinishTime(f1);
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(), new Time(10), s1,
				f1, StdCompetitorPrinter.MULTIPLE_STARTS + " " + s2 + " "
						+ StdCompetitorPrinter.MULTIPLE_ENDS + " " + f2 + " "
						+ StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME),
				cp.row(c));
	}

	/*
	 * Does not sort after total time at the moment, only sorts after class
	 * type.
	 */
	@Ignore
	public void testCompareTo() {
		Competitor c2 = new Competitor(2);
		Competitor c3 = new Competitor(3);

		c.addStartTime(Time.parse("00.00.10"));
		c2.addStartTime(Time.parse("00.01.00"));
		c3.addStartTime(Time.parse("00.02.00"));

		c.addFinishTime(Time.parse("01.00.10"));
		c2.addFinishTime(Time.parse("02.00.10"));
		c3.addFinishTime(Time.parse("02.01.10"));

		assertTrue(c.compareTo(c2) < 0);
		assertEquals(0, c2.compareTo(c3));
		assertTrue(c3.compareTo(c) > 0);
	}

	/*
	 * Does not sort after total time at the moment, only sorts after class
	 * type.
	 */
	@Ignore
	public void testCompareToWithoutStarttime() {
		Competitor c2 = new Competitor(2);

		c.addFinishTime(Time.parse("01.00.10"));

		c2.addStartTime(Time.parse("00.01.00"));
		c2.addFinishTime(Time.parse("02.00.10"));

		assertEquals(1, c.compareTo(c2));
		assertEquals(-1, c2.compareTo(c));
	}

	/*
	 * Does not sort after total time at the moment, only sorts after class
	 * type.
	 */
	@Ignore
	public void testCompareToWithoutFinishtime() {
		Competitor c2 = new Competitor(2);

		c.addStartTime(Time.parse("00.00.10"));

		c2.addStartTime(Time.parse("00.01.00"));
		c2.addFinishTime(Time.parse("02.00.10"));

		assertEquals(1, c.compareTo(c2));
		assertEquals(-1, c2.compareTo(c));
	}

	@Test
	public void testGetLaps() {
		List<Lap> laps;

		c.addStartTime(Time.parse("00.01.00"));

		c.addFinishTime(Time.parse("01.00.00"));
		c.addFinishTime(Time.parse("02.00.00"));
		c.addFinishTime(Time.parse("03.00.00"));
		c.addFinishTime(Time.parse("04.00.00"));

		laps = c.getLaps();

		assertEquals(Time.parse("00.59.00"), laps.get(0).getTotal());
		assertEquals(Time.parse("01.00.00"), laps.get(1).getTotal());
		assertEquals(Time.parse("01.00.00"), laps.get(2).getTotal());
	}

	@Test
	public void testEmptyLaps() {
		assertEquals(0, c.getLaps().size());
	}

	@Test
	public void testFinishTimesSorted() {

		c.addStartTime(Time.parse("00.01.00"));

		c.addFinishTime(Time.parse("03.00.00"));
		c.addFinishTime(Time.parse("05.00.00"));
		c.addFinishTime(Time.parse("10.00.00"));
		c.addFinishTime(Time.parse("01.00.00"));

		List<Lap> laps = c.getLaps();

		assertEquals(Time.parse("00.59.00"), laps.get(0).getTotal());
		assertEquals(Time.parse("02.00.00"), laps.get(1).getTotal());
		assertEquals(Time.parse("02.00.00"), laps.get(2).getTotal());
		assertEquals(Time.parse("05.00.00"), laps.get(3).getTotal());
	}

}
