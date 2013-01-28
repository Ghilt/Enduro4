package sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip & Andrée
 * 
 */
public class Competitor {

	private int index;
	private List<Time> startTimes;
	private List<Time> finishTimes;

	/**
	 * @param index
	 */
	public Competitor(int index) {
		this.index = index;
		startTimes = new ArrayList<Time>();
		finishTimes = new ArrayList<Time>();
	}

	/**
	 * Adds start time to list of start times.
	 * 
	 * @param t		the start time to add
	 */
	public void addStartTime(Time t) {
		startTimes.add(t);
	}

	/**
	 * Return lists of start times.
	 * 
	 * @return the list of start times
	 */
	public List<Time> getStartTimes() {
		return startTimes;
	}

	/**
	 * Adds finish time to list of finish times.
	 * 
	 * @param t		the finish time to add
	 */
	public void addFinishTime(Time t) {
		finishTimes.add(t);
	}

	/**
	 * Return the list of finish times.
	 * 
	 * @return the list of finish times
	 */
	public List<Time> getFinishTimes() {
		return finishTimes;
	}

	/**
	 * Return the index.
	 * 
	 * @return	the index
	 */
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return null;
	}

}
