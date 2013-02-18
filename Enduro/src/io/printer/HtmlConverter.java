package io.printer;

import io.Formater;

public class HtmlConverter implements Converter {

	@Override
	public String convert(String s) {
		StringBuilder sb;
		
		String[] lines = s.split("\n");
		
		String[] column = lines[0].split(Formater.COLUMN_SEPARATOR);
		sb = new StringBuilder();
		for(int i = 0; i < column.length; i++) {
			sb.append("<b>" + column[i] + "</b>");
		}
		lines[0] = sb.toString();
		
		
		sb = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		return sb.toString();
	}
	
}
