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

	@Test
	public void testGoodTimes() {
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
	public void testTwoGoodTimes() {
		Time start1 = Time.parse("00.00.15");
		Time finish1 = Time.parse("00.45.00");
		Time start2 = Time.parse("01.00.00");
		Time finish2 = Time.parse("01.40.00");

		c.addStartTime(start1);
		c.addStartTime(start2);
		c.addFinishTime(finish1);
		c.addFinishTime(finish2);

		Time etapp1 = start1.difference(finish1);
		Time etapp2 = start2.difference(finish2);
		Time total = etapp1.clone().add(etapp2);

		String formattedRow = Formater.formatColumns(1, c.getName(), total, 2,
				etapp1, etapp2, start1.clone(), finish1.clone(),
				start2.clone(), finish2.clone());

		assertEquals(formattedRow, cp.row(c));
	}

}
