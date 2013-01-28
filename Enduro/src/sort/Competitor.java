package sort;

import java.util.ArrayList;
import java.util.List;

public class Competitor {

	private int index;
	private List<Object> startTimes;
	private List<Object> finishTimes;

	public Competitor(int index) {
		this.index = index;
		startTimes = new ArrayList<Object>();
		finishTimes = new ArrayList<Object>();
	}
	
	public void addStartTime(Object t) {
		startTimes.add(t);
	}

	public List<Object> getStartTimes() {
		return startTimes;
	}
	
	public void addFinishTime(Object t) {
		finishTimes.add(t);
	}

	public List<Object> getFinishTimes() {
		return finishTimes;
	}

	public int getIndex() {
		return index;
	}

}
