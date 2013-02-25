package members;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Philip & Andrée
 * 
 */
public class Competitor implements Comparable<Competitor> {

	public static final int NO_STATION = -1;

	private class Station implements Comparable<Station> {
		// public Station(Time t) {
		// time = t;
		// nr = -1;
		// }
		public Station(Time t, int nr) {
			time = t;
			this.nr = nr;
		}

		private Time time;
		private int nr;

		@Override
		public int compareTo(Station other) {
			return time.compareTo(other.time);
		}
	}

	private int index;
	private List<Station> startTimes;
	private List<Station> finishTimes;
	private String name;
	private String classType;
	private int plac;

	/**
	 * The last-time is NOT a lap, in other words: laps = number of finish times
	 * - 1.
	 * 
	 * @return a list representing the laps this competitor has done
	 */
	public List<Lap> getLaps() {
		List<Lap> laps = new ArrayList<Lap>();
		/*
		 * If no start or no more than one finish times, then competitor have no
		 * finished laps.
		 */
		if (finishTimes.isEmpty()) {
			return laps;
		} else {
			// First lap time is first finish time - start time
			if (!startMissing())
				laps.add(new Lap(startTimes.get(0).time,
						finishTimes.get(0).time));

			for (int i = 1; i < finishTimes.size(); i++) {
				laps.add(new Lap(finishTimes.get(i - 1).time, finishTimes
						.get(i).time));
			}
		}

		return laps;
	}

	private class StationTimes {
		private ArrayList<Time> start;
		private ArrayList<Time> finish;

		public StationTimes() {
			start = new ArrayList<Time>();
			finish = new ArrayList<Time>();
		}
	}

	/**
	 * Big laps, defined by one start and one end.
	 * 
	 * @return List of laps
	 */
	public List<Lap> getBinaryLaps() {
		List<Lap> laps = new ArrayList<Lap>();
		Map<Integer, StationTimes> stations = getStationsMatrix();

		// Take the first start and end from each station and put them into a
		// list
		for (StationTimes ts : stations.values()) {
			Time a = new NullTime();
			Time b = new NullTime();
			if (!ts.start.isEmpty()) {
				a = ts.start.get(0);
			}
			if (!ts.finish.isEmpty()) {
				b = ts.finish.get(0);
			}
			laps.add(new Lap(a, b));

		}
		return laps;
	}

	public List<Station>[] getExtraLapsBinary(){

		Map<Integer, StationTimes> stations = getStationsMatrix();
		for (Entry<Integer, StationTimes> ts : stations.entrySet()) {
			
		}
		return null;
		
	}

	/**
	 * Puts all times into a map of arrays with the stations as keys
	 * @return
	 */
	private Map<Integer, StationTimes> getStationsMatrix() {
		// Put all the stations into 2D matrixes so they can be used properly.
		Map<Integer, StationTimes> stations = new HashMap<Integer, StationTimes>();
		for (Station s : startTimes) {
			if (stations.get(s.nr) == null) {
				stations.put(s.nr, new StationTimes());
			}
			stations.get(s.nr).start.add(s.time);
		}
		for (Station s : finishTimes) {
			if (stations.get(s.nr) == null) {
				stations.put(s.nr, new StationTimes());
			}
			stations.get(s.nr).finish.add(s.time);
		}
		return stations;
	}
	
	/**
	 * @return true if start is missing
	 */
	public boolean startMissing() {
		return startTimes.isEmpty();
	}

	/**
	 * @param index
	 *            index of the competitor
	 */
	public Competitor(int index) {
		plac = 0;
		name = "";
		classType = "";
		this.index = index;
		startTimes = new ArrayList<Station>();
		finishTimes = new ArrayList<Station>();
	}

	/**
	 * Adds start time to list of start times.
	 * 
	 * @param t
	 *            the start time to add
	 */
	public void addStartTime(Time t, int nr) {
		startTimes.add(new Station(t, nr));
		Collections.sort(startTimes);
	}

	public void addStartTime(Time t) {
		addStartTime(t, NO_STATION);
	}

	/**
	 * Adds finish time to list of finish times.
	 * 
	 * @param t
	 *            the finish time to add
	 */
	public void addFinishTime(Time t, int nr) {
		finishTimes.add(new Station(t, nr));
		Collections.sort(finishTimes);
	}

	public void addFinishTime(Time t) {
		addFinishTime(t, NO_STATION);
	}

	/**
	 * @return the list of start times
	 */
	public List<Time> getStartTimes() {
		List<Time> ret = new ArrayList<Time>();
		for (Station s : startTimes) {
			ret.add(s.time.clone());
		}
		return ret;
	}

	/**
	 * @return number of laps
	 * @see #getLaps()
	 */
	public int numberOfLaps() {
		return getLaps().size();
	}

	/**
	 * Adds a name to the competitor
	 * 
	 * @param name
	 */
	public void addName(String name) {
		this.name = name;
	}

	/**
	 * @return the list of finish times
	 */
	public List<Time> getFinishTimes() {
		List<Time> ret = new ArrayList<Time>();
		for (Station s : finishTimes) {
			ret.add(s.time.clone());
		}
		return ret;
	}

	/**
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return classType
	 */
	public String getClassType() {
		return classType;
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException("Use CompetitorPrinter plz.");
	}

	public void setPlac(int p) {
		plac = p;
	}

	public int getPlac() {
		return plac;
	}

	/**
	 * Returns the total time of this competitor with a type of lap specified by
	 * the caller. If missing start or finish time no total time exist. If laps
	 * or binary laps exist, the total time is calculated as sum of those times.
	 * 
	 * @return the total time
	 */
	public Time getTotalTime(List<Lap> laps) {
		// No total time exists if start or finish times are missing
		if (startTimes.isEmpty() || finishTimes.isEmpty()) {
			return new NullTime();
		}

		Time total = new Time(0);

		if (!laps.isEmpty()) {
			for (Lap lap : laps) {
				total.add(lap.getTotal());
			}
		} else {
			/*
			 * If no laps exists, total time is difference between first start
			 * and first finish time
			 */
			return startTimes.get(0).time.difference(finishTimes.get(0).time);
		}

		return total;
	}

	/**
	 * Compares this competito's class type with comp's.
	 * 
	 * @param o
	 *            The competitor to compare with.
	 * @return Positive if this competitors class type and then startNr is
	 *         greater than comp's. 0 if the class types and startNr match.
	 *         Negative if this competitors class type or startNr is less than
	 *         comp's.
	 */
	@Override
	public int compareTo(Competitor comp) {
		int i = classType.compareTo(comp.classType);

		if (i == 0) {
			return index - comp.index;
		}

		return i;
	}

	public int getNumberOfLaps() {
		return numberOfLaps();
	}

	public int getNumberOfBinaryLaps() {
		return getBinaryLaps().size();
	}

	public void setClassType(String type) {
		classType = type;
	}
}
