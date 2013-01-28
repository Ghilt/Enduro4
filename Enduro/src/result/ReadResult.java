package result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import sort.Competitor;

public class ReadResult {
	
	private Scanner reg;
	private Scanner start;
	private Scanner end;
	
	
	public ReadResult(File fileReg, File fileStart, File fileEnd){

		try {
			reg = new Scanner(fileReg);
			start = new Scanner(fileStart);
			//end = new Scanner(fileEnd);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public HashMap<Integer, Competitor> openResultFile(){
		HashMap<Integer,Competitor> list = new HashMap<Integer, Competitor>();
		while(reg.hasNext()){
			String index = reg.next();
			index = index.substring(0, index.length()-1);
			int i=Integer.parseInt(index);
			list.put(i, new Competitor(i));
		}
		
		
		return list;
	}
	
	
}
