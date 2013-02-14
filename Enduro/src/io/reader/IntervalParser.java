package io.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser Class for GUI. Parses a string to an Interval of numbers.
 * 
 * @author Oskar, Andrée! unzunz
 */
public class IntervalParser {
	public static final String INTERVAL_DELIMITER = ",";
	public static final String INTERVAL_RANGE = "-";

	/**
	 * Represents an interval
	 */
	public static class Interval {
		private final int start, end;

		/**
		 * An interval with a start number and an end number
		 */
		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}

		/**
		 * An interval with just one number
		 */
		public Interval(int one) {
			this.start = one;
			this.end = one;
		}

		/**
		 * @return returns the last number in the interval
		 */
		public int getEnd() {
			return end;
		}

		/**
		 * @return returns the first number in the interval
		 */
		public int getStart() {
			return start;
		}

		/**
		 * @return returns the total amount of numbers in an interval
		 */
		public int size() {
			return end - start + 1;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + end;
			result = prime * result + start;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Interval other = (Interval) obj;
			if (end != other.end)
				return false;
			if (start != other.start)
				return false;
			return true;
		}
	}

	private List<String> intervals(String s) {
		return Arrays.asList(s.split(INTERVAL_DELIMITER));
	}

	private Interval interval(String s) {
		String[] t = s.split(INTERVAL_RANGE);

		int first = Integer.valueOf(t[0]);

		if (t.length == 1)
			return new Interval(first);

		int second = Integer.valueOf(t[1]);
		return new Interval(first, second);
	}

	private final List<Interval> intervals;
	private final boolean isValid;

	/**
	 * Parses a string of intervals
	 * 
	 * @param str
	 *            intervals
	 */
	public IntervalParser(String str) {
		intervals = new ArrayList<Interval>();
		boolean valid = true;
		try {
			List<String> xs = intervals(str);

			for (String x : xs)
				intervals.add(interval(x));

		} catch (Exception e) {
			valid = false;
		}

		isValid = valid;
	}

	// TODO: STRING SOM FAILAR!!!

	/**
	 * @return If String was valid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @return returns a list with the intervals
	 */
	public List<Interval> getIntervals() {
		return intervals;
	}

}
