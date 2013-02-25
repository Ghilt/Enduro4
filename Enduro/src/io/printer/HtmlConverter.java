package io.printer;

import io.Formater;

/**
 * Converter that converts to HTML.
 */
public class HtmlConverter implements Converter {
	private static final String HTML_START_TAG = "<HTML><HEAD><TITLE>Result</TITLE><META charset=utf-8></HEAD>";
	private static final String HTML_END_TAG = "</BODY></HTML>";
	private static final String FONT = "<FONT SIZE=\"-1\" FACE=\"Helvetica\">";
	private static final String TABLE_START_TAG = "<P>" + FONT
			+ "<TABLE BORDER=1>" + Formater.LINE_BREAK;
	private static final String TABLE_END_TAG = "</TABLE></FONT></P>"
			+ Formater.LINE_BREAK;
	private static final String ROW_START_TAG = "<TR>" + Formater.LINE_BREAK;
	private static final String ROW_END_TAG = "</TR>" + Formater.LINE_BREAK;

	private static final String COLUMN_START_TAG = "<TD><P>" + FONT;
	private static final String COLUMN_END_TAG = "</FONT></P></TD>";
	private static final String FIRST_ROW_FONT_COLOR = "#FFFF99";
	private static final String FIRST_ROW_START_TAG = "<TR BGCOLOR="
			+ FIRST_ROW_FONT_COLOR + ">" + Formater.LINE_BREAK;
	private static final String CLASS_HEADER_START_TAG = "<h3>";
	private static final String CLASS_HEADER_END_TAG = "</h3>";

	@Override
	public String convert(String s) {
		StringBuilder sb;

		String[] lines = s.split(Formater.LINE_BREAK);
		sb = new StringBuilder();

		sb.append(HTML_START_TAG);

		String rowStartTag = ROW_START_TAG;
		for (int i = 0, classes = 0; i < lines.length; ++i) {
			String[] columns = lines[i].split(Formater.COLUMN_SEPARATOR);

			if (columns.length == 1) {
				if (++classes % 2 == 0) {
					sb.append(TABLE_END_TAG);
				}
				sb.append(CLASS_HEADER_START_TAG + columns[0]
						+ CLASS_HEADER_END_TAG);
				sb.append(TABLE_START_TAG);
				rowStartTag = FIRST_ROW_START_TAG;
			} else {
				if (i == 0) {
					sb.append(TABLE_START_TAG);
					rowStartTag = FIRST_ROW_START_TAG;
				}
				sb.append(rowStartTag);
				rowStartTag = ROW_START_TAG;

				for (int j = 0; j < columns.length; ++j) {
					sb.append(COLUMN_START_TAG);
					sb.append(columns[j]);
					sb.append(COLUMN_END_TAG);
				}
				sb.append(ROW_END_TAG);
			}

		}
		sb.append(TABLE_END_TAG);

		sb.append(HTML_END_TAG);

		return sb.toString();
	}
}
