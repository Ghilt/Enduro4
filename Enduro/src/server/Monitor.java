package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.printer.Printer;
import io.printer.StdPrinter;
import members.Competitor;
import members.Time;

public class Monitor {
	private Printer printer;
	private Map<Integer, Competitor> competitors;
	private String resultpath;
	private String timesFilepath;
	private BufferedWriter timeWriter;
	
	public Monitor(String resultpath, String timesFilepath) {
		this.resultpath = resultpath;
		this.timesFilepath = timesFilepath;
		printer = new StdPrinter();
		competitors = new HashMap<Integer, Competitor>();
		
		timeWriter = null;
		try {
			timeWriter = new BufferedWriter(new FileWriter(timesFilepath));
		} catch (IOException e) {
		} 
	}

	public synchronized void register(byte[] startNbr,
			byte[] msg) {
		String temp = new String(startNbr);
		int startNr = Integer.parseInt(temp);

		String s = new String(msg);
		// System.out.println("MESSAGE RECIEVED FROM " + clientIdentifier + ": "
		// +s);
		Time time = Time.parse(s);
		
		registerCompetitor(startNr, time);
		printTime(startNr, time);
		ArrayList<Competitor> list = new ArrayList<Competitor>(competitors.values());
		printer.printResults(list, resultpath);
	}
	
	private void printTime(int startNr, Time time) {
		try {
			timeWriter.write(startNr + "; " + time.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void registerCompetitor(int startNr, Time time) {
		Competitor comp = competitors.get(startNr);
		if(comp == null) {
			comp = new Competitor(startNr);
		}
		if(comp.getStartTimes().isEmpty()) {
			comp.addStartTime(time);
		} else {
			comp.addFinishTime(time);
		}
		competitors.put(startNr, comp);
	}


}
