package sort;

public class Formater {
	public static final String COLUMN_SEPARATOR = "; ";
	public static final String LIST_SEPARATOR = ", ";
	
	public final static String START_NR = "StartNr";
	public final static String START_TIME = "Starttid";
	public final static String FINISH_TIME = "Måltid";
	public final static String NAME = "Namn";
	public final static String TOTAL_TIME = "Totaltid";
	
	/**
	 * Return a string formated for a column
	 * @param s String
	 * @return String as column
	 */
	private static String formatColumn(Object o) {
		return o + COLUMN_SEPARATOR;
	}
	
	public static String formatColumns(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(formatColumn(o));
		}
		
		String s = sb.toString();
		return s.substring(0, s.length() - 2);
	}
	
	/**	
	 * Is called when a competitor has multiple start/finish times
	 * 
	 * @param msg	The errormessage
	 * @param list	The list of objects	
	 * @return	The error message followed by the times seperated by a colon
	 */
	public static String formatList(String msg, Object... list) {
		StringBuilder sb = new StringBuilder();
		sb.append(msg + (list.length == 0 ? "" : " "));
		for (int i = 1; i < list.length; i++) {
			sb.append(list[i]);
			sb.append(LIST_SEPARATOR);
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
}
