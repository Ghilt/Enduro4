package members;

import java.util.ArrayList;
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
	
	
	
	/**
	 * The last-time is NOT a lap, in other words: laps = number of
	 * finishtimes - 1.
	 * 
	 * @return a list representing the laps this competitor has done
	 */
	public List<Lap> getLaps() {
		List<Lap> laps = new ArrayList<Lap>();
		if (startTimes.isEmpty() || finishTimes.isEmpty())
			return laps;
		
		if (finishTimes.size() == 1) {
			laps.add(new Lap(startTimes.get(0),finishTimes.get(0)));
		} else {
			for (int i = 0; i < finishTimes.size(); i++) {
				if (i == 0) 
					laps.add(new Lap(startTimes.get(0), finishTimes.get(0)));
				else
					laps.add(new Lap(finishTimes.get(i - 1), finishTimes.get(i)));
			}
		}
		
		return laps;
	}
	
	/**
	 * @param index		index of the competitor
	 */
	public Competitor(int index) {
		name= "";
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
	 * @return the list of start times
	 */
	public List<Time> getStartTimes() {
		return startTimes;
	}
	
	
	/**
	 * @return number of laps
	 */
	public int numberOfLaps(){
		return finishTimes.size()-1;
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
	 * @return the list of finish times
	 */
	public List<Time> getFinishTimes() {
		return finishTimes;
	}

	/**
	 * 
	 * @return	the index
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
	
	public String toString() {
		throw new UnsupportedOperationException("Use CompetitorPrinter plz.");
	}
	
	/**
	 * Compares the total time of this competitor with the competitor o
	 * @param o		The competitor to compare with.
	 * @return 		1 if this competitors total time is less than o's
	 * 				0 if the total times are equal
	 * 				-1 if this competitors total time is larger than o's
	 */
	@Override
	public int compareTo(Competitor comp) {
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
