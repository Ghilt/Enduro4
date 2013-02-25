package test.unit;

import static org.junit.Assert.assertEquals;
import io.Formater;
import io.printer.BinaryLapPrinter;
import io.printer.Printer;
import members.Competitor;
import members.NullTime;
import members.Time;

import org.junit.*;

public class TestBinaryLapPrinter {
	private Competitor c;
	private Printer cp;

	@Before
	public void setup() {
		c = new Competitor(1);
		cp = new BinaryLapPrinter();
	}

	@Test
	public void testOneEtapp() {
		String start1 = "00.00.15";
		String finish1 = "00.45.00";

		c.addStartTime(Time.parse(start1));
		c.addFinishTime(Time.parse(finish1));

		Time etapp1 = Time.parse(start1).difference(Time.parse(finish1));

		String formattedRow = Formater.formatColumns(1, c.getName(), etapp1, 1,
				etapp1, Time.parse(start1), Time.parse(finish1));

		assertEquals(formattedRow, cp.row(c));
	}

	@Test
	public void testTwoEttaps() {
		Time start1 = Time.parse("00.00.15");
		Time finish1 = Time.parse("00.45.00");
		Time start2 = Time.parse("01.00.00");
		Time finish2 = Time.parse("01.40.00");

		c.addStartTime(start1, 1);
		c.addStartTime(start2, 2);
		c.addFinishTime(finish1, 1);
		c.addFinishTime(finish2, 2);

		Time etapp1 = start1.difference(finish1);
		Time etapp2 = start2.difference(finish2);
		Time total = etapp1.clone().add(etapp2);

		String formattedRow = Formater.formatColumns(1, c.getName(), total, 2,
				etapp1, etapp2, start1.clone(), finish1.clone(),
				start2.clone(), finish2.clone());

		assertEquals(formattedRow, cp.row(c));
	}

	@Test
	public void testNoStartTimes() {
		Time finish1 = Time.parse(1, "00.45.00");
		Time finish2 = Time.parse(2, "01.40.00");

		c.addFinishTime(finish1, 1);
		c.addFinishTime(finish2, 2);

		String formattedRow = Formater.formatColumns(1, c.getName(),
				new NullTime().toString(), 2, new NullTime().toString(),
				new NullTime().toString(), Printer.NO_START, finish1,
				Printer.NO_START, finish2);

		assertEquals(formattedRow, cp.row(c));
	}

	@Test
	public void testNoFinishTimes() {
		Time start1 = Time.parse(1, "00.00.15");
		Time finish1 = Time.parse(1, "00.45.00");
		Time start2 = Time.parse(2, "01.00.00");

		c.addStartTime(start1, 1);
		c.addStartTime(start2, 2);
		c.addFinishTime(finish1, 1);

		String formattedRow = Formater.formatColumns(1, c.getName(),
				start1.difference(finish1), 2, start1.difference(finish1),
				new NullTime().toString(), start1, finish1, start2,
				Printer.NO_END);

		assertEquals(formattedRow, cp.row(c));
	}

	@Test
	public void testManyStartTimes() {
		Time start1 = Time.parse("00.00.15");
		Time finish1 = Time.parse("00.45.00");
		Time start2 = Time.parse("01.00.00");

		c.addStartTime(start1, 1);
		c.addStartTime(start2, 1);
		c.addFinishTime(finish1, 1);

		String formattedRow = Formater.formatColumns(1, c.getName(),
				start1.difference(finish1), 1, start1.difference(finish1),
				start1, finish1, Printer.MULTIPLE_STARTS_CLEAN + " "
						+ Formater.BINARY_LAP_TIME + 1 + " " + start2);

		assertEquals(formattedRow, cp.row(c));
	}

	@Test
	public void testManyEndTimes() {
		Time start1 = Time.parse(1, "00.00.15");
		Time finish1 = Time.parse(1, "00.45.00");
		Time finish2 = Time.parse(1, "01.00.00");

		c.addStartTime(start1, 1);
		c.addFinishTime(finish1, 1);
		c.addFinishTime(finish2, 1);

		String formattedRow = Formater.formatColumns(1, c.getName(),
				start1.difference(finish1), 1, start1.difference(finish1),
				start1, finish1, Printer.MULTIPLE_ENDS_CLEAN + " "
						+ Formater.BINARY_LAP_TIME + 1 + " " + finish2);

		assertEquals(formattedRow, cp.row(c));
	}

}
