package test.unit;

import static org.junit.Assert.assertEquals;
import io.Formater;
import io.printer.LapPrinter;
import io.printer.Printer;
import members.Competitor;
import members.NullTime;
import members.Time;

import org.junit.Before;
import org.junit.Test;



public class TestLapRacePrinter {

	private Competitor c;
	private Printer cp;

	@Before
	public void setup() {
		c = new Competitor(1);
		cp = new LapPrinter();
	}

	@Test
	public void testOneLap() {
		Time s1 = Time.parse("00.10.00");
		Time f1 = Time.parse("00.40.00");
		c.addStartTime(s1);
		c.addFinishTime(f1);

		assertEquals(
				Formater.formatColumns(1, c.getName(), 1, s1.difference(f1),
						s1.difference(f1), s1, f1), cp.row(c));

	}

	@Test
	public void manyLaps() {
		Time s1 = Time.parse("00.00.15"), f1 = Time.parse("00.30.00"), f2 = Time
				.parse("01.00.00"), f3 = Time.parse("02.00.00");
		c.addStartTime(s1);
		c.addFinishTime(f1);
		c.addFinishTime(f2);
		c.addFinishTime(f3);
		assertEquals(
				Formater.formatColumns(1, c.getName(), 3, s1.difference(f3),
						Time.parse("00.29.45"), Time.parse("00.30.00"),
						Time.parse("01.00.00"), s1, f1, f2, f3), cp.row(c));
	}

	@Test
	public void testBadStart() {
		c.addFinishTime(Time.parse("00.45.00"));
		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), new NullTime().toString(), "",
				Printer.NO_START, Time.parse("00.45.00")), cp.row(c));
	}

	@Test
	public void testBadEnd() {
		Time t1 = Time.parse("00.10.00");
		c.addStartTime(t1);
		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), new NullTime().toString(), t1,
				Printer.NO_END), cp.row(c));
	}

	@Test
	public void testImpossibleLapTime() {
		Time s1 = Time.parse("00.00.05");
		c.addStartTime(s1);
		Time f1 = Time.parse("00.25.00");
		c.addFinishTime(f1);
		Time f2 = Time.parse("00.26.00");
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), s1.difference(f2), s1.difference(f1),
				f1.difference(f2), s1, f1, f2, Printer.IMPOSSIBLE_LAP_TIME),
				cp.row(c));
	}

	@Test
	public void testMultipleStartTimes() {
		Time s1 = Time.parse("00.10.00");
		c.addStartTime(s1);
		Time s2 = Time.parse("00.20.00");
		c.addStartTime(s2);
		Time f1 = Time.parse("00.45.00");
		c.addFinishTime(f1);
		Time f2 = Time.parse("01.15.00");
		c.addFinishTime(f2);

		assertEquals(Formater.formatColumns(1, c.getName(),
				c.getNumberOfLaps(), s1.difference(f2), Time.parse("00.35.00"),
				Time.parse("00.30.00"), s1, f1, f2, Printer.MULTIPLE_STARTS
						+ " " + s2), cp.row(c));
	}

	@Test
	public void testMultipleStartTimesAndImpossibleLapTime() {
		Time s1 = Time.parse("00.00.05");
		c.addStartTime(s1);
		Time s2 = Time.parse("00.00.06");
		c.addStartTime(s2);
		Time f1 = Time.parse("00.20.06");
		c.addFinishTime(f1);
		Time f2 = Time.parse("00.55.06");
		c.addFinishTime(f2);
		Time f3 = Time.parse("00.57.00");
		c.addFinishTime(f3);

		assertEquals(
				Formater.formatColumns(1, c.getName(), c.getNumberOfLaps(),
						s1.difference(f3), s1.difference(f1),
						f1.difference(f2), f2.difference(f3), s1, f1, f2, f3,
						Printer.MULTIPLE_STARTS + " " + s2,
						Printer.IMPOSSIBLE_LAP_TIME), cp.row(c));

	}

}
