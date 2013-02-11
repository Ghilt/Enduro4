package spike;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Before
	public void setup() {
	}

	@Test
	public void testPrint() {
		Calendar date = new GregorianCalendar(2005, Calendar.MARCH, 25, 14, 00,
				21);
		assertEquals("2005/03/25 14:00:21", df.format(date.getTime()));
	}

	@Test
	public void testDiff() {
		Calendar date1 = new GregorianCalendar(2005, Calendar.MARCH, 25, 18,
				00, 00), date2 = new GregorianCalendar(2005, Calendar.MARCH,
				25, 20, 20, 21);

		long diff = date2.getTime().getTime() - date1.getTime().getTime();

		assertEquals(8421000, diff);
	}

	@Test
	public void timeConstructor() throws ParseException {
		Calendar date1 = new GregorianCalendar();
		date1.set(Calendar.HOUR, 18);
		date1.set(Calendar.MINUTE, 21);
		date1.set(Calendar.SECOND, 00);

		Calendar date2 = new GregorianCalendar();
		SimpleDateFormat pf = new SimpleDateFormat("HH:mm:ss");

		date2.setTime(pf.parse("18:21:00"));

		assertEquals(date1.get(Calendar.HOUR), date2.get(Calendar.HOUR));
		assertEquals(date1.get(Calendar.MINUTE), date2.get(Calendar.MINUTE));
		assertEquals(date1.get(Calendar.SECOND), date2.get(Calendar.SECOND));
		// This does not mean that the objects are equal!
		assertThat(date1, not(date2));
	}

}
