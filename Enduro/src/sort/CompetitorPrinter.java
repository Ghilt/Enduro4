package sort;

import java.util.List;

import members.Competitor;

public interface CompetitorPrinter {
	public String row(Competitor c);
	public void printResults(List<Competitor> competitors, String filepath);

}
