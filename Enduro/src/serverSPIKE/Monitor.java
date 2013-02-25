package serverSPIKE;

public class Monitor {

	public Monitor() {
	}

	public synchronized void register(int msg) {
		System.out.println("MESSAGE RECIEVED: " + msg);
	}

}
