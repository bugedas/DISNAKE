package client;

import Observer.Editor;
import Observer.LogListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

// TEST OK
public class Client_listener implements Runnable {
	// le serveur EMPTY la file avant de mettre un nouvel element
	private ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>> gateJobs;
	private DatagramChannel listenerChannel; // sur le portEcoute
	private Client client;
	private boolean pasLancerDir = true;

	// le serveur EMPTY la file avant de mettre un nouvel element, ne marche
	// parce qu'un seul thread remplit la file
	protected Client_listener(ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>> jobs, short listeningPort,
							  Client c) {
		gateJobs = jobs;
		client = c;
		try {
			listenerChannel = DatagramChannel.open();
			listenerChannel.socket().bind(new InetSocketAddress(listeningPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			boolean gameOver = false;
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				listenerChannel.receive(buffer);
				buffer.flip();
				byte type = buffer.get();
				switch (type) {
				case 0:
					if (client.notReceivedPortGame) {
						client.notReceivedPortGame = false;
						short gamePort = buffer.getShort();
						byte number = buffer.get();
						// On a port de jeu = gamePort et number de client =
						// number
						client.lancerAffichage(number);
						client.lancerSpeaker(number, gamePort);
					}
					break;
				case 1:
					client.print("<HTML><h2>The game starts in " + buffer.get() + " seconds</h2></HTML>");
					break;
				case 2:
					if (pasLancerDir) {
						client.lanceradministratorDirection();
						pasLancerDir = false;
						client.print("");
					}
					client.print(printScores(buffer));
					lireSerpents(buffer);
					break;
				case 3:
					if (!gameOver) {
						gameOver = true;
						client.print(lireBufferFinal(buffer));
					}
					break;
				default:
					throw new Exception("Le message du server de Jeu est corrompu");
				}
				buffer.clear();
			}
		} catch (Exception e) {
		}
	}

	private String lireBufferFinal(ByteBuffer buffer) throws IOException {
		String s = "<HTML><h2>The game is over. Scores :</h2>";
		byte nbSnakes = buffer.get();
		for (int i = 0; i < nbSnakes; i++) {
			byte num = buffer.get();
			short score = buffer.getShort();
			s += "<h3>Snake " + num + " has " + score + " points</h3>";
		}
		Editor editor = new Editor();
		editor.events.subscribe("update", new LogListener("Scores.txt"));

		try {
			editor.openFile("Observer/Scores.txt");
			editor.updateFile(s);
			editor.saveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String scores = Files.readString(Path.of("./Observer/Scores.txt"), StandardCharsets.US_ASCII);
		return scores+"</HTML>";
	}

	private String printScores(ByteBuffer buffer) throws IOException {
		String s = "<HTML><h2>Scores :</h2>";
		byte nbSnakes = buffer.get();
		for (int i = 0; i < nbSnakes; i++) {
			byte num = buffer.get();
			short score = buffer.getShort();
			s += "<h3>Snake " + num + " has " + score + " points</h3>";
		}

		return s+"</HTML>";
	}


	private void lireSerpents(ByteBuffer buffer) throws Exception {
		Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>> req = decodeBufferToGame(buffer);
		// ici on enleve tous les elements de la file pour ne prendre en compte
		// que le dernier, marche car un seul thread remplit la file
		while (gateJobs.size() > 0)
			gateJobs.poll();
		gateJobs.put(req);
	}

	// fonction decode
	private static Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>> decodeBufferToGame(ByteBuffer buf) throws Exception {
		HashMap<Byte, Snake> snakes = new HashMap<Byte, Snake>();
		try {
			byte nbSnakes = buf.get();
			for (int i = 0; i < nbSnakes; i++) {
				byte numSnake = buf.get();
				LinkedList<Point> curSnake = new LinkedList<Point>();
				Point cur = new Point(buf.get(), buf.get());
				curSnake.add(cur);
				byte nbDir = buf.get();
				byte dir = -1;
				for (int j = 0; j < nbDir; j++) {
					dir = buf.get();
					byte length = buf.get();
					int k = j == 0 ? 1 : 0;
					for (; k < length; k++) {
						Point tmp = new Point(cur.x + (dir % 2 == 0 ? dir - 1 : 0),
								cur.y + (dir % 2 == 1 ? dir - 2 : 0));
						cur = tmp;
						curSnake.add(cur);
					}
				}
				Snake c = new Snake(dir, numSnake, curSnake);
				snakes.put(numSnake, c);
			}
			Point FOOD = new Point(buf.get(), buf.get());
			Point POISON = new Point(buf.get(), buf.get());

			List<Point> wall = new ArrayList<>();
			int wallCount = buf.get();

			for(int i = 0; i < wallCount; i++){
				wall.add(new Point(buf.get(), buf.get()));
			}

			return new Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>(snakes, FOOD, POISON, wall);
		} catch (Exception e) {
		}
		throw new Exception("Le message du serveur est mal décodé");
	}
}