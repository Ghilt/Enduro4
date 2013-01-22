package register;

import java.io.IOException;

public class RegisterMain {
	
	public static void main(String[] args) {
		
		TimeGenerator generator = new TimeGenerator();
		
		
		generator.registerDriver(72);
		generator.registerDriver(12);
		generator.registerDriver(22);
	}
}
