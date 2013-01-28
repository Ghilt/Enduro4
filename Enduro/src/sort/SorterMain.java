package sort;


public class SorterMain {

	
	/**
	 * @param args Arg0 = Input Registered, Arg1 = Output Result
	 */
	public static void main(String[] args) {
			
		Sorter s = new Sorter(null, "sorted_result.txt", "register_simple.txt");
		s.printResult();
	}
	
}
