package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class BinaryLapPrinter extends Printer {

	private final static String FIRST_ROW = "StartNr; Namn; Totaltid; #Etapper; ";

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		addString(sb, c.getIndex() + "");
		addString(sb, c.getName() + "");
		addString(sb, c.getTotalBinaryTime() + "");

		List<Lap> etapper = c.getBinaryLaps();

		addString(sb, etapper.size() + "");

		appendEtapper(sb, etapper);
		appendTimes(sb, etapper);

		sb.setLength(sb.length() - Formater.COLUMN_SEPARATOR.length());
		return sb.toString();
	}

	private void appendTimes(StringBuilder sb, List<Lap> etapper) {
		for (Lap l : etapper) {
			addString(sb, l.getStart() + "");
			addString(sb, l.getEnd() + "");
		}
	}

	private void appendEtapper(StringBuilder sb, List<Lap> etapper) {
		for (Lap l : etapper) {
			addString(sb, l.getTotal() + "");
		}
	}

	@Override
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		// TODO Auto-generated method stub

	}

	protected void addString(StringBuilder sb, String s) {
		sb.append(s);
		sb.append(Formater.COLUMN_SEPARATOR);
	}

}
