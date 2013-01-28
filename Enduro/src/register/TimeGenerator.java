package register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class TimeGenerator {
	
	private File driverTimes;
	private FileWriter fileWriter;

	
	/**
	 * Create TimeGenerator
	 *
	 */
	public TimeGenerator() {
		driverTimes = new File("driver_times.txt");
		try {
			fileWriter = new FileWriter(driverTimes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Print the registered driver and its time to file  
	 * 
	 *@param driverNumber The number of the driver that is registered
	 *
	 */
	public void registerDriver(int driverNumber){
		

		try {
			fileWriter.append(driverNumber + "; 1.23.45\n");
			fileWriter.flush();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
