package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.NullTime;
import members.Time;

public class LapCompetitorPrinter implements CompetitorPrinter {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera sluttider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig totaltid?";
	public static final String IMPOSSIBLE_LAP_TIME = "Omöjlig varvtid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time("00.15.00");
	private int maxLaps;
	
	/**
	 * @return Total time elapsed, or Null time string
	 */
	private Time totalTime(Competitor c) {
		return (c.getStartTimes().isEmpty() || c.getFinishTimes().isEmpty()) ? new NullTime() : 
			c.getStartTimes().get(0).difference(c.getFinishTimes().get(c.getFinishTimes().size()-1));
	}

	/**	
	 * Is called when a competitor has multiple start/finish times
	 * 
	 * @param msg	The errormessage
	 * @param list	The list of times	
	 * @return	The errormessage followed by the times seperated by a colon
	 */
//	private static String addTimes(String msg, Object... list) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(msg + (list.length == 0 ? "" : " "));
//		for (int i = 1; i < list.length; i++) {
//			sb.append(list[i]);
//			sb.append(", ");
//		}
//		String res = sb.toString();
//		return res.substring(0, res.length() - 2);
//	}

	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();
		sb.append(Formater.formatColumns(c.getIndex(), c.getName(), c.getNumberOfLaps(),
				totalTime(c))+"; ");
		for(int i=0;i<maxLaps;i++){
			sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal())+"; ");
		}
		sb.append(c.getStartTimes().get(0)+ "; ");
		for(int i=0;i<maxLaps-1;i++){
			sb.append(Formater.formatColumns(c.getLaps().get(i).getEnd())+"; ");
		}
		sb.append(Formater.formatColumns(c.getFinishTimes().get(c.getFinishTimes().size()-1)));
		return sb.toString();
	}	
	
	/**
	 * Prints the result in the list with competitors to the output file.
	 * 
	 * @param competitors	list of competitors
	 * @param filepath		the file to write to
	 */
	public void printResults(List<Competitor> competitors, String filepath) {
		try {
			maxLaps = getMaxLaps(competitors);
			File outputFile = new File(filepath);
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.append("StartNr; Namn; #Varv; TotalTid; ");
			for(int i = 1; i <maxLaps+1;i++){
				fileWriter.append("Varv"+i+"; ");
			}
			fileWriter.append("StartTid; ");
			for(int i = 1; i <maxLaps;i++){
				fileWriter.append("Varvning"+i+"; ");
			}
			fileWriter.append("Måltid\n");
			for(Competitor comp : competitors) {
				fileWriter.append("" + row(comp) + "\n");
			}
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getMaxLaps(List<Competitor> competitors){
		int a = competitors.get(0).getNumberOfLaps();
		for(Competitor c : competitors){
			if(c.getNumberOfLaps()>a){
				a= c.getNumberOfLaps();
			}
		}
		return a;
	}
}
