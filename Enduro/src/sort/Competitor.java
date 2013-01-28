package sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip & Andrée
 * 
 */
public class Competitor implements Comparable {

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
	
	private String addTimes(String msg, List<Time> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(msg);
		for(int i = 1; i < list.size(); i++) {
			sb.append(list.get(i));
			sb.append(", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Sorter.formatColumns(index, 
				totalTimeToString(), 
				(startTimes.isEmpty() ? NO_START : startTimes.get(0).toString()),
				(finishTimes.isEmpty() ? NO_END : finishTimes.get(0).toString())));
		
		if(startTimes.size() > 1) {
			sb.append(addTimes("Flera starttider? ", startTimes));
		}
		
		if(finishTimes.size() > 1) {
			sb.append(addTimes("Flera måltider? ", finishTimes));	
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
