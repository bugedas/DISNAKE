package launcher;

import client.Client;

public class LaunchClient {
	public static void main(String[] args) {
		try {
			client.Client.getInstance();
		} catch (Exception ignored) {
		}
	}
}
