package sort;

public class Time {
	
	/**
	 * Calculates the difference between two times.
	 * @param t1 Start
	 * @param t2 End
	 * @return Difference
	 */
	public Time difference(Time t1, Time t2) {
		return new Time(t1.seconds - t2.seconds);
	}

	@Override
	public String toString() {
		long temp = seconds;
		long hours = temp/3600;
		temp -= hours * 3600;
		long minutes = temp/60;
		temp -= minutes * 60;
		String minString = minutes +"";
		if(minString.length()<2){
			minString = "0"+minString;
		}
		String secString = temp +"";
		if(secString.length()<2){
			secString = "0"+secString;
		}
		return hours+";"+minString+";"+secString;
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
		try{
			String[] stamps = stringTime.split(";");
			//System.out.println(stamps.length+" "+stamps[0]+" "+stamps[1]+" "+stamps[2]);
			Long hours = Long.parseLong(stamps[0]);
			Long minutes = Long.parseLong(stamps[1]);
			Long seconds = Long.parseLong(stamps[2]);
			
			i = hours * 3600 + minutes * 60 + seconds;
			
		}catch(NumberFormatException nfe){
			i = 0;
		}catch(NullPointerException nue){
			i = 0;
		}
		return i;
	}

	private long seconds;

}
