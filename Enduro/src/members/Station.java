package members;

/**
 * @author Henrik
 *
 * Class for holding station nr and the attached time. The relevant class must keep track which time it is.
 */
public class Station implements Comparable<Station> {
	public Station(Time t, int nr) {
		time = t;
		this.nr = nr;
	}

	public final Time time;
	public final int nr;

	@Override
	public int compareTo(Station other) {
		return time.compareTo(other.time);
	}
}
