package result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import sort.Competitor;
import sort.Time;

public class ReadResult {
	
	private Scanner reg;
	private Scanner start;
	private Scanner end;
	private Scanner sName;
	
	
	public ReadResult(File fileReg, File fileStart, File fileEnd, File names){

		try {
			reg = new Scanner(fileReg);
			start = new Scanner(fileStart);
			end = new Scanner(fileEnd);
			sName = new Scanner(names);
		} catch (FileNotFoundException e1) {
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
		while(start.hasNext()){
			String index = start.next();
			index = index.substring(0, index.length()-1);
			int i=Integer.parseInt(index);
			String starttime = start.next();
			list.get(i).addStartTime(new Time(starttime));
		}
		while(end.hasNext()){
			String index = end.next();
			index = index.substring(0, index.length()-1);
			int i=Integer.parseInt(index);
			String finishtime = end.next();
			list.get(i).addFinishTime(new Time(finishtime));
		}
		
		while(sName.hasNext()){
			String index = sName.next();
			index = index.substring(0, index.length()-1);
			int i= Integer.parseInt(index);
			System.out.println(i);
			String name = sName.next();
			name = name + " " + sName.next();
			//while(!sName.hasNextLine()){
			//System.out.println(name);
			//name = name + " " + sName.next();
			//}
			list.get(i).addName(name);
			System.out.println(list.get(i).getName());
		}
		return list;
	}
	
	

}

		