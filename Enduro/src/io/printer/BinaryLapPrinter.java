package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class BinaryLapPrinter extends Printer {

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		addString(sb, c.getIndex() + "");
		addString(sb, c.getName() + "");
		addString(sb, c.getTotalBinaryTime() + "");

		List<Lap> etapper = c.getBinaryLaps();
		addString(sb, etapper.size() + "");
		for (Lap l : etapper) {
			addString(sb, l.getTotal() + "");
		}
		for (Lap l : etapper) {
			addString(sb, l.getStart() + "");
			addString(sb, l.getEnd() + "");
		}

		sb.setLength(sb.length() - 2);
		return sb.toString();
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
