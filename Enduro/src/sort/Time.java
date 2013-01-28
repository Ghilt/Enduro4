package sort;

public class Time {
	private final static long ONE_DAY = 86400;
	private final long HOUR = 3600;
	/**
	 * Create time from current system time.
	 * 
	 * @return Time
	 */
	public static Time fromCurrentTime() {
		return new Time((System.currentTimeMillis() / 1000) % ONE_DAY);
	}

	private static final String SEPARATOR = ".";
	public static final String NULL_TIME = "--.--.--";
	private long seconds;

	/**
	 * Calculates the difference this time and t.
	 * 
	 * @param t
	 *            End
	 * @return Difference
	 */
	public Time difference(Time t) {
		Time ret = new Time(t.seconds - seconds);
		if (ret.seconds < 0) {
			ret.seconds = ONE_DAY + ret.seconds;
		}
		return ret;
	}

	@Override
	public String toString() {
		long temp = seconds;
		long hours = temp / HOUR;
		temp -= hours * HOUR;
		long minutes = temp / 60;
		temp -= minutes * 60;
		String minString = minutes + "";
		if (minString.length() < 2) {
			minString = "0" + minString;
		}
		String secString = temp + "";
		if (secString.length() < 2) {
			secString = "0" + secString;
		}
		return ((hours >= 10) ? hours : "0" + hours) + SEPARATOR + minString + SEPARATOR + secString;
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
		if (seconds != other.seconds)
			return false;
		return true;
	}

	/**
	 * Generates a time with seconds set to 0;
	 */
	public Time() {
		seconds = 0;
	}

	/**
	 * Generates a time from a new long
	 * 
	 * @param seconds
	 */
	public Time(long seconds) {
		this.seconds = seconds;
	}

	public Time(String stringTime) {
		this.seconds = convertToTime(stringTime);
	}

	private long convertToTime(String stringTime) {
		
		long i;
		try {
			String[] stamps = stringTime.split("\\" + SEPARATOR);
//			 System.out.println(stamps.length+" "+stamps[0]+" "+stamps[1]+" "+stamps[2]);
			Long hours = Long.parseLong(stamps[0]);
			Long minutes = Long.parseLong(stamps[1]);
			Long seconds = Long.parseLong(stamps[2]);

			i = hours * HOUR + minutes * 60 + seconds;

		} catch (NumberFormatException nfe) {
			i = 0;
		} catch (NullPointerException nue) {
			i = 0;
		}
		return i;
	}

	public int compareTo(Time time) {
		long diff = seconds - time.seconds; 
		if(diff < 0) {
			return -1;
		} else if (diff == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	
}
