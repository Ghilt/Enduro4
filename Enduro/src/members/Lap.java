package members;

/**
 * @author Andrée & Victor
 * 
 *         Data structure for handling laps.
 * 
 */
public class Lap {
	private final Time start, end;

	/**
	 * @param s
	 *            Start time
	 * @param e
	 *            End time or lap moment
	 */
	public Lap(Time s, Time e) {
		start = s;
		end = e;
	}

	/**
	 * @return Start time
	 */
	public Time getStart() {
		return start;
	}

	/**
	 * @return End time
	 * @see #getLapMoment()
	 */
	public Time getEnd() {
		return end;
	}

	/**
	 * @return Lap time
	 */
	public Time getTotal() {
		if(start.isNull() || end.isNull()){
			return new NullTime();
		}
		return start.difference(end);
	}

	@Override
	public String toString() {
		return getTotal().toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Lap other = (Lap) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
}
