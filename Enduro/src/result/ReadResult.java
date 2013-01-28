package result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sort.Competitor;

public class ReadResult {
	private Scanner reg;
	private Scanner start;
	private Scanner end;
	
	
	public ReadResult(File fileReg, File fileStart, File fileEnd){

		try {
			reg = new Scanner(fileReg);
			//start = new Scanner(fileStart);
			//end = new Scanner(fileEnd);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public ArrayList<Competitor> openResultFile(){
		ArrayList<Competitor> list = new ArrayList<Competitor>();
		while(reg.hasNext()){
			String index = reg.next();
			index = index.substring(0, index.length()-1);
			list.add(new Competitor(Integer.parseInt(index)));
			
		}
		
		return list;
	}
	
	
}
