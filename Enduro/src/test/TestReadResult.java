package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import result.ReadResult;
import sort.Competitor;

public class TestReadResult {
	private ReadResult result;
	
	@Before
	public void initialize(){
		result = new ReadResult(new File("register_simple.txt"), new File("start_simple.txt"), new File("end_simple"), new File ("names.txt"));
	}


	@Test
	public void testAddRegToResult() {
		assertFalse(result.openResultFile().size()== 0);
	}
	
	@Test
	public void testAddStartToResult(){
		assertTrue(result.openResultFile().get(1).getStartTimes().get(0)!=null);
	}
	@Test
	public void testAddEndToResult(){
		assertTrue(result.openResultFile().get(1).getFinishTimes().get(0)!=null);
	}
	
	@Test
	public void testStartTimeAdded(){
		assertEquals(result.openResultFile().get(1).getStartTimes().get(0).toString(), "00.00.00");
	}
	
	@Ignore
	public void testFinishTimeAdded(){
		assertEquals(result.openResultFile().get(1).getFinishTimes().get(0).toString(), "01.40.40");
	}
	
	@Test
	public void testNameAdded(){
		HashMap<Integer,Competitor> list = result.openResultFile();
		
		assertEquals(list.get(1).getName(), "Anders Asson");
		assertEquals(list.get(2).getName(), "Berit Bsson");
		assertEquals(list.get(15).getName(), "Zlatan Ibra");
	}

}	
