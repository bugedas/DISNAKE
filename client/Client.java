package client;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Client{
	// server est initialisé dans le readBufferWaitPlayerServer et utile dans lancerSpeaker
	private InetSocketAddress server;
	// créée dans launchingListener et utile pour lancerAffichage, gateJobs contient les serpents envoye par le serveur, la liste est partagée entre le client listener aui la remplit et le administrator de la gate qui la EMPTY
	private ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point>> gateJobs;
	// créée dans lancerAffichage et utile pour lancerSpeaker, directionIdJobs est remplie par le administrator de direction et vidée par le sender
	private BlockingDeque<Pair<Byte,Byte>> directionIdJobs;
	// pour envoyer le message je veux joueur tant qu'on ne recoit pas de message du serveur
	protected volatile boolean notReceivedPortGame = true;
	private String serverName;
	private ManageRequestDirection gest;
	private DisplayManagement window = null;
	public static Client instance;

	static {
		try {
			instance = new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// on recupere sur le port 5656, le serveur et le port avec lequel on
	// communique avec le serveur, on dit au serveur de nous parler sur 5959
	private Client() throws Exception{
			launchingListener((short) 5959, readBufferWaitPlayerServer(5656));
	}

	public static Client getInstance() throws Exception {
		if(instance == null) {
			instance = new Client();
		}
		return instance;
	}

	private void launchingListener(short listeningPort, short sendPort) throws Exception {
		gateJobs = new ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point>>(1);
		new Thread(new Client_listener(gateJobs, listeningPort, this)).start();
		envoieServer(listeningPort, sendPort, server);
	}
	

	void lancerAffichage(byte number) {
		directionIdJobs = new LinkedBlockingDeque<Pair<Byte,Byte>>(5);
		ArrayBlockingQueue<Byte> directionJobs = new ArrayBlockingQueue<Byte>(5);
		gest = new ManageRequestDirection(directionIdJobs, directionJobs);
		window = new DisplayManagement(serverName, number, directionJobs);
		new Thread(new ManagementBackgate(gateJobs, window, number, gest)).start();
	}


	void lancerSpeaker(byte number, short gamePort) {
		new Thread(new Client_sender(server, directionIdJobs, gamePort, number)).start();
	}
	
	void lanceradministratorDirection(){
		new Thread(gest).start();
	}
	
	public void print(String string) {
		//text.setText(string);
		if(window!=null)
			window.print(string);
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

		server = (InetSocketAddress) clientSocket
				.receive(buffer);
		buffer.flip();
		try {

			byte nbChar = buffer.get();
			serverName = "";
			for (int i = 0; i < nbChar; i++)
				serverName += (char) buffer.get();

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

class Triplet<E,V,D>{
	E a;
	V b;
	D c;
	Triplet(E a1, V b1, D c1){
		a = a1;
		b = b1;
		c = c1;
	}
}