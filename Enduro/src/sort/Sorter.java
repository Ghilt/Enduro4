package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import members.Competitor;

public class Sorter {

	public Sorter() {
		
	}
	
	/**
	 * Sorts the list of competitors. If the sorted status in config file is set
	 * to 'yes', then it sorts the competitors after class type, then number of
	 * laps and finally the total time. If sorted status does not exist in
	 * config file, or it set to 'no', then it sorts the competitors after class
	 * type only.
	 * 
	 * @param prop
	 *            properties to get status in config file
	 * @param list
	 *            list containing competitors to sort
	 */
	public void sortList(boolean sort, ArrayList<Competitor> list) {
		if (!sort) {
			Collections.sort(list);
		} else {
			Collections.sort(list, new CompetitorComparator());
		}
	}
	
	public static class CompetitorComparator implements Comparator<Competitor> {
		public CompetitorComparator() {
		}

		@Override
		public int compare(Competitor o1, Competitor o2) {
			int cmp = o1.getClassType().compareTo(o2.getClassType());
			if (cmp == 0) {
				cmp = o2.getNumberOfLaps() - o1.getNumberOfLaps();
				
				if (cmp == 0) {
					cmp = o1.getTotalTime().compareTo(o2.getTotalTime());
				}
			}
			return cmp;
		}
	}
}
