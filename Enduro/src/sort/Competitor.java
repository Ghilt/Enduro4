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
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera sluttider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig tid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time(15);
	
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
	private Time totalTime() {
		return (startTimes.isEmpty() || finishTimes.isEmpty()) ? new NullTime() : 
			startTimes.get(0).difference(finishTimes.get(0));
	}
	
	private String addTimes(String msg, Object... list) {
		StringBuilder sb = new StringBuilder();
		sb.append(msg + (list.length == 0 ? "" : " "));
		for (int i = 1; i < list.length; i++) {
			sb.append(list[i]);
			sb.append(", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Sorter.formatColumns(index, 
				totalTime(), 
				(startTimes.isEmpty() ? NO_START : startTimes.get(0).toString()),
				(finishTimes.isEmpty() ? NO_END : finishTimes.get(0).toString())));
		
		if (startTimes.size() > 1) {
			sb.append(addTimes("; " + MULTIPLE_STARTS, startTimes.toArray()));
		}
		
		if (finishTimes.size() > 1) {
			sb.append(addTimes("; " + MULTIPLE_ENDS, finishTimes.toArray()));	
		}
		
		if (totalTime().compareTo(MINIMUM_TOTAL_TIME) <= 0) {
			sb.append("; " + IMPOSSIBLE_TOTAL_TIME);
		}
		
		return sb.toString();
	}
	
	@Override
	public int compareTo(Object o) {
		Competitor comp = (Competitor) o;
		if(comp.getStartTimes().isEmpty() || comp.getFinishTimes().isEmpty()) {
			return 1;
		} else if (startTimes.isEmpty() || finishTimes.isEmpty()) {
			return -1;
		}
		
		Time totalTime = startTimes.get(0).difference(finishTimes.get(0));
		Time totalTime2 = comp.getStartTimes().get(0).difference(comp.getFinishTimes().get(0));
		
		return totalTime.compareTo(totalTime2);
	}

}
