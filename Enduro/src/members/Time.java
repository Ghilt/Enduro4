package members;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time implements Comparable<Time> {
	private static final SimpleDateFormat BIG_FORMAT = new SimpleDateFormat(
			"d/MM/yy HH.mm.ss.ms z");
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"HH.mm.ss");

	private static Calendar defaultCalendar() {
		return new GregorianCalendar(1970, Calendar.JANUARY, 1, 00, 00, 00);
	}

	private Calendar cal;

	/**
	 * Create time from current system time.
	 * 
	 * @return Time
	 */
	public static Time fromCurrentTime() {
		Time t = new Time();
		t.cal = Calendar.getInstance();

		return t;
	}

	public static Time parse(String str) {
		try {
			Calendar cal = defaultCalendar();
			cal.setTime(FORMAT.parse(str));
			cal.set(Calendar.DAY_OF_MONTH, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return new Time(cal);
		} catch (ParseException e) {
			return new NullTime();
		}
	}

	public static Time parse(int dayofMonth, String time) {
		Time t = Time.parse(time);
		t.cal.set(Calendar.DAY_OF_MONTH, dayofMonth);

		return t;
	}

	/**
	 * Calculates t - this and returns the difference as a new Time object. If
	 * the difference is less than 0, one day is added to the time. May happen
	 * when start and finish time are in different days.
	 * 
	 * @param t
	 *            The later time
	 * @return Difference. A new Time object.
	 * 
	 */
	public Time difference(Time t) {
		long diff = t.cal.getTime().getTime() - cal.getTime().getTime();

		return new Time((int)diff / 1000);
	}

	/**
	 * Returns a string in the following format: hh.mm.ss
	 * 
	 * @return a string with the time
	 */
	@Override
	public String toString() {
		return FORMAT.format(cal.getTime());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if ((cal.getTimeInMillis() / 1000) != (other.cal.getTimeInMillis() / 1000))
			return false;
		return true;
	}

	/**
	 * Generates a time with seconds set to 0;
	 */
	public Time() {
		cal = defaultCalendar();
	}

	public Time(int seconds) {
		this();
		
		int left = 0;
		int ss = 0;
		int mm = 0;
		int hh = 0;
		int dd = 0;
		left = seconds;
		ss = left % 60;
		left = left / 60;
		mm = left % 60;
		left = left / 60;
		hh = left % 24;
		left = left / 24;
		dd = left;
		
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		cal.set(Calendar.HOUR_OF_DAY, hh);
		cal.set(Calendar.MINUTE, mm);
		cal.set(Calendar.SECOND, ss);
		cal.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Generates a time from a new long
	 * 
	 * @param seconds
	 */
	public Time(Calendar cal) {
		this.cal = cal;
	}

	public Time(Date date) {
		this();
		cal.setTime(date);
	}

	@Override
	public int compareTo(Time time) {
		if (time instanceof NullTime)
			return -1;

		return (int)(cal.getTime().getTime() - time.cal.getTime().getTime());
	}

	/**
	 * Add the time to this time.
	 * 
	 * @param time
	 *            the time to add
	 */
	public void add(Time time) {
		cal.setTimeInMillis(cal.getTime().getTime()
				+ time.cal.getTime().getTime());
	}

}
