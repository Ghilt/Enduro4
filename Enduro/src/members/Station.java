package members;

public class Station implements Comparable<Station> {
	// public Station(Time t) {
	// time = t;
	// nr = -1;
	// }
	public Station(Time t, int nr) {
		time = t;
		this.nr = nr;
	}

	public Time time;
	public int nr;

	@Override
	public int compareTo(Station other) {
		return time.compareTo(other.time);
	}
}
