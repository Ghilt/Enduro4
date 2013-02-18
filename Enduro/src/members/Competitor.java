package members;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Philip & Andrée
 * 
 */
public class Competitor implements Comparable<Competitor> {

	private int index;
	private List<Time> startTimes;
	private List<Time> finishTimes;
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
				laps.add(new Lap(startTimes.get(0), finishTimes.get(0)));

			for (int i = 1; i < finishTimes.size(); i++) {
				laps.add(new Lap(finishTimes.get(i - 1), finishTimes.get(i)));
			}
		}

		return laps;
	}

	/**
	 * Big laps, defined by one start and one end.
	 * 
	 * @return List of laps
	 */
	public List<Lap> getBinaryLaps() {
		List<Lap> laps = new ArrayList<Lap>();
//		int startIndex = 0;
//		int finishIndex = 0;
//		while(startIndex < startTimes.size() && finishIndex < finishTimes.size()) {
//			Time startTime = startTimes.get(startIndex);
//			Time finishTime = finishTimes.get(finishIndex);
//			if(finishTime.compareTo(startTime) > 0) {
//				laps.add(new Lap(startTime, finishTime));
//				startIndex++;
//				finishIndex++;
//			} else {
//				finishIndex++;
//			}
//		}
//		while(startIndex < startTimes.size()) {
//			Time startTime = startTimes.get(startIndex);
//			laps.add(new Lap(startTime, new NullTime()));
//			startIndex++;
//		}
		for (int i = 0; i < startTimes.size() && i < finishTimes.size(); i++)
			laps.add(new Lap(startTimes.get(i), finishTimes.get(i)));

		return laps;
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
		startTimes = new ArrayList<Time>();
		finishTimes = new ArrayList<Time>();
	}

	/**
	 * Adds start time to list of start times.
	 * 
	 * @param t
	 *            the start time to add
	 */
	public void addStartTime(Time t) {
		startTimes.add(t);
		Collections.sort(startTimes);
	}

	/**
	 * @return the list of start times
	 */
	public List<Time> getStartTimes() {
		return startTimes;
	}

	/**
	 * @return number of laps
	 * @see #getLaps()
	 */
	public int numberOfLaps() {
		return getLaps().size();
	}

	/**
	 * Adds finish time to list of finish times.
	 * 
	 * @param t
	 *            the finish time to add
	 */
	public void addFinishTime(Time t) {
		finishTimes.add(t);
		Collections.sort(finishTimes);
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
		return finishTimes;
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
			return startTimes.get(0).difference(finishTimes.get(0));
		}

		return total;
	}

	/**
	 * Compares this competito's class type with comp's.
	 * 
	 * @param o
	 *            The competitor to compare with.
	 * @return Positive if this competitors class type and then startNr is greater than comp's. 0 if the
	 *         class types and startNr match. Negative if this competitors class type or startNr is
	 *         less than comp's.
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
