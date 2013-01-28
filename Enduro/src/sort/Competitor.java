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
	
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";

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
	
	/**
	 * @return Total time elapsed, or Null time string
	 */
	private String totalTimeToString() {
		return (startTimes.isEmpty() || finishTimes.isEmpty()) ? Time.NULL_TIME : 
			startTimes.get(0).difference(finishTimes.get(0)).toString();
	}
	
	private String startTimes() {
		StringBuilder sb = new StringBuilder();
		
		for(Time time : startTimes) {
			sb.append(time);
			sb.append(", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
	
	private String finishTimes() {
		StringBuilder sb = new StringBuilder();
		
		for(Time time : finishTimes) {
			sb.append(time);
			sb.append(", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
	
	public String toString() {
		return Sorter.formatColumns(index, 
				totalTimeToString(), 
				(startTimes.isEmpty() ? NO_START : startTimes()),
				(finishTimes.isEmpty() ? NO_END : finishTimes()));
	}

}
