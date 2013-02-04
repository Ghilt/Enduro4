package sort;

public class Formater {
	/**
	 * Return a string formated for a column
	 * @param s String
	 * @return String as column
	 */
	private static String formatColumn(Object o) {
		return o + "; ";
	}
	
	public static String formatColumns(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(formatColumn(o));
		}
		
		String s = sb.toString();
		return s.substring(0, s.length() - 2);
	}
}
