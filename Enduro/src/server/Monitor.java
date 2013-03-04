package server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.printer.Printer;
import io.printer.StdPrinter;
import io.reader.CvsReader;
import io.reader.Parser;
import io.reader.ParserException;
import members.Competitor;
import members.Sorter;
import members.Time;

public class Monitor {
	private Printer printer;
	private Map<Integer, Competitor> competitors;
	private String resultpath;
	private Sorter sorter;

	public Monitor(String resultpath, String namefilepath) {
		this.resultpath = resultpath;
		printer = new StdPrinter();
		readNameFile(namefilepath);
		sorter = new Sorter();
	}

	private void readNameFile(String namefilepath) {
//		competitors = new HashMap<Integer, Competitor>();
		Parser parser = new Parser();
		CvsReader reader = new CvsReader(namefilepath);
		try {
			competitors = parser.parse(reader.readAll(), Parser.FileIdentifier.name_file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void register(byte[] startNbr, byte[] msg) {
		String temp = new String(startNbr);
		String[] interval = temp.split("-");
		int firstNbr;
		int lastNbr;
		if (interval.length > 1) {
			firstNbr = Integer.valueOf(interval[0]);
			lastNbr = Integer.valueOf(interval[1]);
		} else {
			firstNbr = lastNbr = Integer.valueOf(interval[0]);
		}
		String s = new String(msg);

		Time time = Time.parse(s);
		for (int startNr = firstNbr; startNr <= lastNbr; startNr++) {
			registerCompetitor(startNr, time);
		}

		ArrayList<Competitor> list = new ArrayList<Competitor>(
				competitors.values());
		sorter.sortList(true, list, "standard");
		printer.printResults(list, resultpath);
	}

	private void registerCompetitor(int startNr, Time time) {
		System.out.println(startNr + " " + time.toString());
		Competitor comp = competitors.get(startNr);
		if (comp == null) {
			comp = new Competitor(startNr);
		}
		if (comp.getStartTimes().isEmpty()) {
			comp.addStartTime(time);
		} else {
			comp.addFinishTime(time);
		}
		competitors.put(startNr, comp);
	}

}
