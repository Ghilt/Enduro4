package io.printer;

/**
 * Returns unconverted string.
 */
public class NullConverter implements Converter {

	public String convert(String s) {
		return s;
	}
}
