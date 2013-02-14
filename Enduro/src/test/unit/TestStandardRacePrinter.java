package test.unit;

import static org.junit.Assert.assertEquals;
import members.Competitor;
import members.NullTime;
import members.Time;

import org.junit.Before;
import org.junit.Test;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.StdCompetitorPrinter;

public class TestStandardRacePrinter {

	private Competitor c;
	private CompetitorPrinter cp;

	@Before
	public void setup() {
		c = new Competitor(1);
		cp = new StdCompetitorPrinter();
	}

	@Test
	public void testGoodTimes() {
		c.addStartTime(Time.parse("00.00.15"));
		c.addFinishTime(Time.parse("00.45.00"));
		assertEquals(Formater.formatColumns(1, c.getName(),
				Time.parse("00.00.15").difference(Time.parse("00.45.00")),
				Time.parse("00.00.15"), Time.parse("00.45.00")), cp.row(c));
	}

	@Test
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(Formater.formatColumns(1, c.getName(),
				new NullTime().toString(), StdCompetitorPrinter.NO_START,
				new Time(45)), cp.row(c));
	}

	@Test
	public void testBadEnd() {
		c.addStartTime(new Time(10));
		assertEquals(Formater.formatColumns(1, c.getName(),
				new NullTime().toString(), new Time(10),
				StdCompetitorPrinter.NO_END), cp.row(c));
	}

	@Test
	public void testImpossibleTotalTime() {
		c.addStartTime(new Time(5));
		c.addFinishTime(new Time(10));
		assertEquals(Formater.formatColumns(1, c.getName(), new Time(5),
				new Time(5), new Time(10),
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
	public void allPossibleImpossibles() {
		c.addStartTime(new Time(5));
		c.addStartTime(new Time(6));
		c.addFinishTime(new Time(15));
		c.addFinishTime(new Time(16));

		assertEquals(Formater.formatColumns(1, c.getName(), new Time(10),
				new Time(5), new Time(15), StdCompetitorPrinter.MULTIPLE_STARTS
						+ " " + new Time(6) + " "
						+ StdCompetitorPrinter.MULTIPLE_ENDS + " "
						+ new Time(16) + " "
						+ StdCompetitorPrinter.IMPOSSIBLE_TOTAL_TIME),
				cp.row(c));
	}

}
