package members;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	public void sortList(boolean sort, ArrayList<Competitor> list,
			String racetype) {
		if (!sort) {
			Collections.sort(list);
		} else {
			if (racetype.equals("laprace")) {
				Collections.sort(list, new CompetitorComparator());
			}
			if (racetype.equals("etapprace")) {
				Collections.sort(list, new CompetitorBinaryComparator());
			}
		}
	}

	/**
	 * Comparator used for sorting the competitors depending on their total time
	 * of their laps. First sorts after class type, then if the competitors has
	 * the same class type, sorts after total time.
	 */
	public static class CompetitorComparator implements Comparator<Competitor> {
		public CompetitorComparator() {
		}

		@Override
		public int compare(Competitor o1, Competitor o2) {
			int cmp = o1.getClassType().compareTo(o2.getClassType());
			if (cmp == 0) {
				cmp = o2.getNumberOfLaps() - o1.getNumberOfLaps();
				if (cmp == 0) {
					cmp = o1.getTotalTime(o1.getLaps()).compareTo(
							o2.getTotalTime(o2.getLaps()));
				}
			}
			return cmp;
		}
	}

	/**
	 * Comparator used for sorting the competitors depending on their total time
	 * of their binary laps. First sorts after class type, then if the
	 * competitors has the same class type, sorts after total time.
	 */
	public static class CompetitorBinaryComparator implements
			Comparator<Competitor> {
		public CompetitorBinaryComparator() {
		}

		@Override
		public int compare(Competitor o1, Competitor o2) {
			int cmp = o1.getClassType().compareTo(o2.getClassType());
			if (cmp == 0) {
				cmp = o2.getFullBinaryLaps() - o1.getFullBinaryLaps();

				if (cmp == 0) {
					cmp = o1.getTotalTime(
							new ArrayList<Lap>(o1.getBinaryLaps().values()))
							.compareTo(
									o2.getTotalTime(new ArrayList<Lap>(o2
											.getBinaryLaps().values())));
				}
			}
			return cmp;
		}
	}
}
