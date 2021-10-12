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
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gateJobs;
	// créée dans lancerAffichage et utile pour lancerSpeaker, directionIdJobs est remplie par le administrator de direction et vidée par le sender
	private BlockingDeque<Pair<Byte,Byte>> directionIdJobs;
	// pour envoyer le message je veux joueur tant qu'on ne recoit pas de message du serveur
	protected volatile boolean notReceivedPortGame = true;
	private String serverName;
	private ManageRequestDirection gest;
	private DisplayManagement window = null;
//	public static Client instance = new Client();
	
	// on recupere sur le port 5656, le serveur et le port avec lequel on
	// communique avec le serveur, on dit au serveur de nous parler sur 5959
	public Client() throws Exception{
			launchingListener((short) 5959, readBufferWaitPlayerServer(5656));
	}

//	public static Client getInstance() {
//		if(instance == null)
//			instance = new Client();
//		return instance;
//	}
	// On lance un client listener sur le port listeningPort et on envoie au serveur le port sur lequel on va ecouter
	private void launchingListener(short listeningPort, short sendPort) throws Exception {
		gateJobs = new ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>>(1);
		new Thread(new Client_listener(gateJobs, listeningPort, this)).start();
		envoieServer(listeningPort, sendPort, server);
	}
	
	// appelé par le Client_listener
	void lancerAffichage(byte number) {
		directionIdJobs = new LinkedBlockingDeque<Pair<Byte,Byte>>(5);
		ArrayBlockingQueue<Byte> directionJobs = new ArrayBlockingQueue<Byte>(5);
		gest = new ManageRequestDirection(directionIdJobs, directionJobs);
		window = new DisplayManagement(serverName, number, directionJobs);
		new Thread(new ManagementBackgate(gateJobs, window, number, gest)).start();
	}

	// appelé par le Client_listener, on lance un speaker sur le port gamePort
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
		// on ouvre une nouvelle connexion avec le serveur sur le port de connexion
		DatagramChannel speakerChannel = DatagramChannel.open();
		speakerChannel.socket().bind(new InetSocketAddress(0));
		InetSocketAddress remote = new InetSocketAddress(server.getAddress(), portConnection);
		
		// on envoie le port de jeu du client
		ByteBuffer iWantToPlay = clientConnection(listeningPort);
		while(notReceivedPortGame){
			// On envoie un message je veux jouer sur le port portConnection
			speakerChannel.send(iWantToPlay, remote);
			// permet de reenvoyer le buffer
			iWantToPlay.position(0);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
		// on ferme la communication
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
		// on ouvre une communication avec le serveur sur le port indiqué dans la rfc (5656)
		DatagramChannel clientSocket = DatagramChannel.open();
		InetSocketAddress local = new InetSocketAddress(portServer);
		clientSocket.socket().bind(local);
		// on cree un buffer pour recevoir le message, on attend une réponse du serveur
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		// on recupere l'adresse du serveur
		server = (InetSocketAddress) clientSocket
				.receive(buffer);
		buffer.flip();
		try {
			// on a déjà le nom du serveur codé comme pour DNS
			byte nbChar = buffer.get();
			serverName = "";
			for (int i = 0; i < nbChar; i++)
				serverName += (char) buffer.get();
			// on récupère le port de connexion
			short portConnection = buffer.getShort();
			// On joue sur serverName nom qui contient nbChar caracteres. On se connecte sur portConnection
			// on ferme la connexion avec le serveur sur le port 5656
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
