package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import result.ReadResult;

public class TestReadResult {
	private ReadResult result;


	@Test
	public void testAddRegToResult() {
		result = new ReadResult(new File("register_simple.txt"), new File("start_simple.txt"), new File("end_simple.txt"));
		assertFalse(result.openResultFile().size()== 0);
	}
	/**
	@Test
	public void testAddStartToResult(){
		result = new ReadResult(new File("register_simple.txt"), new File("start_simple.txt"), new File("end_simple.txt"));
		assertTrue(result.openResultFile().get(0).getStartTimes().get(0)!=null);
	}
	@Test
	public void testAddEndToResult(){
		result = new ReadResult(new File("register_simple.txt"), new File("start_simple.txt"), new File("end_simple.txt"));
		assertTrue(result.openResultFile().get(0).getFinishTimes().get(0)!=null);
	}
**/
}	
