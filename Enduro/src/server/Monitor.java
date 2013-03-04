package server;

public class Monitor {

	public Monitor() {
	}

	public synchronized void register(int clientIdentifier, byte[] msg) {
		String s = new String(msg);
		System.out.println("MESSAGE RECIEVED FROM " + clientIdentifier + ": " +s);
	}

}
