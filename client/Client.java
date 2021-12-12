package client;

import utilities.Mediator.ServerParametersMediator;

import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Client {
	private ServerParametersMediator parameters;
	private ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>> gateJobs;
	private BlockingDeque<Pair<Byte,Byte>> directionIdJobs;
	protected volatile boolean notReceivedPortGame = true;
	private ManageRequestDirection gest;
	public static Client instance;

	static {
		try {
			instance = new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Client() throws Exception{
		parameters = new ServerParametersMediator();
		launchingListener((short) 5959, readBufferWaitPlayerServer(5656));
	}

	public static Client getInstance() throws Exception {
		if(instance == null) {
			instance = new Client();
		}
		return instance;
	}

	private void launchingListener(short listeningPort, short sendPort) throws Exception {
		gateJobs = new ArrayBlockingQueue<>(1);
		new Thread(new Client_listener(gateJobs, listeningPort, this)).start();
		envoieServer(listeningPort, sendPort, parameters.getServer());
	}
	

	void lancerAffichage(byte number) {
		directionIdJobs = new LinkedBlockingDeque<Pair<Byte,Byte>>(5);
		ArrayBlockingQueue<Byte> directionJobs = new ArrayBlockingQueue<Byte>(5);
		gest = new ManageRequestDirection(directionIdJobs, directionJobs);
		parameters.setWindow(new DisplayManagement(parameters.getServerName(), number, directionJobs));
		new Thread(new ManagementBackgate(gateJobs, parameters.getWindow(), number, gest)).start();
	}


	void lancerSpeaker(byte number, short gamePort) {
		new Thread(new Client_sender(parameters.getServer(), directionIdJobs, gamePort, number)).start();
	}
	
	void lanceradministratorDirection(){
		new Thread(gest).start();
	}
	
	public void print(String string) {
		//text.setText(string);
		if (parameters.getWindow() != null)
			parameters.getWindow().print(string);
	}

	private void envoieServer(short listeningPort, short portConnection,
			InetSocketAddress server) throws Exception {

		DatagramChannel speakerChannel = DatagramChannel.open();
		speakerChannel.socket().bind(new InetSocketAddress(0));
		InetSocketAddress remote = new InetSocketAddress(server.getAddress(), portConnection);
		

		ByteBuffer iWantToPlay = clientConnection(listeningPort);
		while(notReceivedPortGame){

			speakerChannel.send(iWantToPlay, remote);

			iWantToPlay.position(0);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}

		speakerChannel.close();
	}

	private ByteBuffer clientConnection(short listeningPort) {
		ByteBuffer res = ByteBuffer.allocate(3);
		res.put((byte) 0);
		res.putShort(listeningPort);
		res.flip();
		return res;
	}

	private short readBufferWaitPlayerServer(int portServer)
			throws Exception {

		DatagramChannel clientSocket = DatagramChannel.open();
		InetSocketAddress local = new InetSocketAddress(portServer);
		clientSocket.socket().bind(local);

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		InetSocketAddress server = (InetSocketAddress) clientSocket
				.receive(buffer);

		parameters.setServer(server);

		buffer.flip();
		try {

			byte nbChar = buffer.get();
			String serverName = "";
			for (int i = 0; i < nbChar; i++)
				serverName += (char) buffer.get();

			parameters.setServerName(serverName);

			short portConnection = buffer.getShort();

			clientSocket.close();
			return portConnection;
		} catch (BufferUnderflowException e) {
			clientSocket.close();
			throw new Exception("Server message is corrupted");
		}
	}

}

class Pair<E,V>{
	E a;
	V b;
	Pair(E a1, V b1){
		a = a1;
		b = b1;
	}
}

class Triplet<E,V,D,G>{
	E a;
	V b;
	D c;
	G d;
	Triplet(E a1, V b1, D c1, G d1){
		a = a1;
		b = b1;
		c = c1;
		d = d1;
	}
}