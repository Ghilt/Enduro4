package test.unit;

import static org.junit.Assert.assertEquals;
import io.Formater;
import io.printer.BinaryLapPrinter;
import io.printer.Printer;
import members.Competitor;
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

	@Ignore
	public void testGoodTimes() {
		String start1 = "00.00.15";
		String finish1 = "00.45.00";

		c.addStartTime(Time.parse(start1));
		c.addFinishTime(Time.parse(finish1));

		Time etapp1 = Time.parse(start1).difference(Time.parse(finish1));

		assertEquals(
				Formater.formatColumns(1, c.getName(), etapp1, 1, etapp1,
						Time.parse(start1), Time.parse(finish1)), cp.row(c));
	}

	@Ignore
	public void testTwoGoodTimes() {
		String start1 = "00.00.15";
		String finish1 = "00.45.00";
		String start2 = "01.00.00";
		String finish2 = "01.40.00";

		c.addStartTime(Time.parse(start1));
		c.addStartTime(Time.parse(start2));
		c.addFinishTime(Time.parse(finish1));
		c.addFinishTime(Time.parse(finish2));

		Time etapp1 = Time.parse(start1).difference(Time.parse(finish1));
		Time etapp2 = Time.parse(start2).difference(Time.parse(finish2));
		Time total = Time.parse(start1).difference(Time.parse(finish1))
				.add(etapp2);

		assertEquals(Formater.formatColumns(1, c.getName(), total, 2, etapp1,
				etapp2, Time.parse(start1), Time.parse(finish1),
				Time.parse(start2), Time.parse(finish2)), cp.row(c));
	}

}
