package launcher;

public class LaunchClient {
	public static void main(String[] args) {
		try {
			client.Client.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
