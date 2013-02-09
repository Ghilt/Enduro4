package test.unit;

import static org.junit.Assert.assertEquals;
import members.Competitor;
import members.NullTime;
import members.Time;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import sort.CompetitorPrinter;
import sort.Formater;
import sort.LapCompetitorPrinter;
import sort.Printer;

public class TestLapRacePrinter {

	private Competitor c;
	private CompetitorPrinter cp;

	@Before
	public void setup() {
		c = new Competitor(1);
		cp = new LapCompetitorPrinter();
	}

	@Test
	public void testOneLap() {
		Time t1 = new Time("00.00.15"), t2 = new Time("00.45.00");
		c.addStartTime(t1);
		c.addFinishTime(t2);
		assertEquals(Formater.formatColumns(1, c.getName(), 1, t1.difference(t2), t1, t2), cp.row(c));
	}

	@Test
	public void emptyLaps() {
		c.addStartTime(new Time("00.00.15"));
		c.addFinishTime(new Time("00.45.00"));
		assertEquals(0, c.getLaps().size());
	}

	@Test
	public void manyLaps() {
		Time s1 = new Time("00.00.15"),
				f1 = new Time("00.20.00"),
				f2 = new Time("00.35.00"),
				f3 = new Time("00.45.00");
		c.addStartTime(s1);
		c.addFinishTime(f1);
		c.addFinishTime(f2);
		c.addFinishTime(f3);
		assertEquals(Formater.formatColumns(1, c.getName(), 3, s1.difference(f3), new Time(
				"00.19.45"), new Time("00.15.00"), new Time("00.10.00"),
				s1, f1,
				f2, f3), cp.row(c));
	}

	@Ignore
	public void testBadStart() {
		c.addFinishTime(new Time(45));
		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), new NullTime().toString(), c.getLaps().get(0)
						.getTotal(), Printer.NO_START, new Time(45)), cp.row(c));
	}

	@Ignore
	public void testBadEnd() {
		Time t1 = new Time(10);
		c.addStartTime(t1);
		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), new NullTime().toString(), t1,
				Printer.NO_END + ";"), cp.row(c));
	}

	@Ignore
	public void testImpossibleTotalTime() {
		Time t1 = new Time(5), t2 = new Time(10);
		c.addStartTime(t1);
		c.addFinishTime(t2);
		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), t1, t1, t1,
				t2, Printer.IMPOSSIBLE_TOTAL_TIME), cp.row(c));
	}

	@Ignore
	public void testImpossibleLapTime() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time f1 = new Time("00.25.00");
		c.addFinishTime(f1);
		Time f2 = new Time("00.26.00");
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), s1.difference(f2), s1.difference(f1),
				f1.difference(f2), s1, f1, f2, Printer.IMPOSSIBLE_LAP_TIME),
				cp.row(c));
	}

	@Ignore
	public void testMultipleStartTimes() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time s2 = new Time("00.00.06");
		c.addStartTime(s2);
		Time f1 = new Time("00.20.06");
		c.addFinishTime(f1);
		Time f2 = new Time("00.55.06");
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), s1.difference(f2), s1.difference(f1), s1,
				f1, f2, Printer.MULTIPLE_STARTS + " " + s2), cp.row(c));
	}

	@Ignore
	public void testMultipleStartTimesAndImpossibleLapTime() {
		Time s1 = new Time("00.00.05");
		c.addStartTime(s1);
		Time s2 = new Time("00.00.06");
		c.addStartTime(s2);
		Time f1 = new Time("00.20.06");
		c.addFinishTime(f1);
		Time f2 = new Time("00.55.06");
		c.addFinishTime(f2);
		Time f3 = new Time("00.57.00");
		c.addFinishTime(f3);

		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), s1.difference(f3), s1.difference(f1),
				f2.difference(f3), s1, f1, f2, f3, Printer.MULTIPLE_STARTS
						+ " " + s2, Printer.IMPOSSIBLE_LAP_TIME), cp.row(c));

	}

}