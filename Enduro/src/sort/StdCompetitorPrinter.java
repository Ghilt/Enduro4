package sort;

public class StdCompetitorPrinter implements CompetitorPrinter {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera sluttider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig tid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time("00.15.00");
	
	/**
	 * @return Total time elapsed, or Null time string
	 */
	private Time totalTime(Competitor c) {
		return (c.getStartTimes().isEmpty() || c.getFinishTimes().isEmpty()) ? new NullTime() : 
			c.getStartTimes().get(0).difference(c.getFinishTimes().get(0));
	}

	/**	
	 * Is called when a competitor has multiple start/finish times
	 * 
	 * @param msg	The errormessage
	 * @param list	The list of times	
	 * @return	The errormessage followed by the times seperated by a colon
	 */
	private static String addTimes(String msg, Object... list) {
		StringBuilder sb = new StringBuilder();
		sb.append(msg + (list.length == 0 ? "" : " "));
		for (int i = 1; i < list.length; i++) {
			sb.append(list[i]);
			sb.append(", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Formater.formatColumns(c.getIndex(), c.getName(),
				totalTime(c), 
				(c.getStartTimes().isEmpty() ? NO_START : c.getStartTimes().get(0).toString()),
				(c.getFinishTimes().isEmpty() ? NO_END : c.getFinishTimes().get(0).toString())));
		sb.append(";");
		if (c.getStartTimes().size() > 1) {
			sb.append(" ");
			sb.append(addTimes(MULTIPLE_STARTS, c.getStartTimes().toArray()));
		}
		
		if (c.getFinishTimes().size() > 1) {
			sb.append(" ");
			sb.append(addTimes(MULTIPLE_ENDS, c.getFinishTimes().toArray()));	
		}
		
		if (totalTime(c).compareTo(MINIMUM_TOTAL_TIME) <= 0) {
			sb.append(" ");
			sb.append(IMPOSSIBLE_TOTAL_TIME);
		}
		
		return sb.toString();
	}

}
