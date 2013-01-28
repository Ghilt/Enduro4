package sort;

public class NullTime extends Time {
	
	public NullTime() {
		super(0);
	}
	
	/**
	 * Pretty straight forward...
	 */
	@Override
	public String toString() {
		return "--.--.--";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	/**
	 * Returns a number greater than 0, to make the comparisation with an
	 * ordinary time object work (needen when checking for ie impossible time)
	 */
	@Override
	public int compareTo(Time time){
		return 1;
	}
	
	/**
	 * The null time is considered as 0.
	 */
	@Override
	public Time difference(Time t) {
		return t;
	}
}
