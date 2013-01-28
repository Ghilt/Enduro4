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
	private String name;
	
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";

	/**
	 * @param index		index of the competitor
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
	 * Adds a name to the competitor
	 * @param name
	 */
	public void addName(String name) {
		this.name=name;
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
	
	public String getName() {
		return name;
	}
	
	/**
	 * @return Total time elapsed, or Null time string
	 */
	private String totalTimeToString() {
		return (startTimes.isEmpty() || finishTimes.isEmpty()) ? Time.NULL_TIME : 
			startTimes.get(0).difference(finishTimes.get(0)).toString();
	}
	
	/**	
	 * Is called when a competitor has multiple start/finishtimes
	 * @param msg	The errormessage
	 * @param list	The list of times	
	 * @return	The errormessage followed by the times seperated by a colon
	 */
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
	
	/**
	 * Prints times for the competitor in the following format:
	 * Index; Totaltime; Starttime; Finishtime; Errormessage (if any)
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SorterMain.formatColumns(index, name , 
				totalTimeToString(), 
				(startTimes.isEmpty() ? NO_START : startTimes.get(0).toString()),
				(finishTimes.isEmpty() ? NO_END : finishTimes.get(0).toString())));
		
		if (startTimes.size() > 1) {
			sb.append(addTimes("Flera starttider? ", startTimes));
		}
		
		if (finishTimes.size() > 1) {
			sb.append(addTimes("Flera måltider? ", finishTimes));	
		}
		
		return sb.toString();
	}
	
	/**
	 * Compares the total time of this competitor with the competitor o
	 * @param o		The competitor to compare with.
	 * @return 		1 if this competitors total time is less than o's
	 * 				0 if the total times are equal
	 * 				-1 if this competitors total time is larger than o's
	 */
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
