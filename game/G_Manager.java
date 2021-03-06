package game;

import utilities.Client;
import utilities.Job;
import utilities.Runnable_Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class G_Manager implements Runnable {
	public Game thisGame;

	public volatile boolean sendGameInfo;
	public volatile boolean sendTimer;
	public volatile boolean gameToBeOver;
	public volatile boolean gameOver;
	public volatile boolean sendScore;

	private Thread input;
	public int inputPort;
	public ArrayBlockingQueue<Job> in_communicator;

	public HashMap<Client, ArrayBlockingQueue<Job>> out_communicators;

	public G_Manager(Game g, int inputPort, long multicastTimeInterval)
			throws IOException {
		/**
		 * A manager for 1 game: - listening each player moves on port inputPort
		 * (using 1 Thread) - multicasting everyones position every
		 * multicastTimeInterval (ms) on outputPort (using another Thread)
		 */
		System.out.println("G_Manager has been initialized:");

		thisGame = g;
		in_communicator = new ArrayBlockingQueue<Job>(100);
		this.inputPort = inputPort;
		input = new Thread(new Runnable_Input(inputPort, in_communicator, "G"));
		System.out.println("\t> input Thread initialized on port " + inputPort);
		out_communicators = new HashMap<Client, ArrayBlockingQueue<Job>>();
		System.out.println("\t> output Thread initialized");

		System.out.println("\t> END");

		sendGameInfo = true;
		sendTimer = false;
		gameToBeOver = false;
		gameOver = false;
		sendScore = false;
	}

	@Override
	public void run() {
		System.out.println("G_Manager has been started");
		input.start();

		try {
			sendGameInfo = true;
			while (sendGameInfo) {
				// at this point all players asked to play and were displayed
				// the info
				sendGameInfo = thisGame.hasRoom();
				// Thread.sleep(2000);
			}

			sendTimer = true;
			G_ManagerFacade.SendTimer(out_communicators);

			sendTimer = false;

			Thread.sleep(1000);
			gameOver = false;

			G_ManagerFacade.SendPositions(out_communicators, inputPort);

			Thread moveSnakes;
			moveSnakes = new Thread(new G_MoveSnakes(thisGame));
			moveSnakes.start();

			byte id = -1;
			gameToBeOver = false;
			while (!gameOver) {
				
				Job j = in_communicator.take();
				if(gameToBeOver){
					break;
				}

				if (id == j.jobId()
						|| !thisGame.snakes.containsKey(j.id() & 255)) {
					continue;
				}

				else
					System.out.println(j.jobId());
				id = j.jobId();

				G_ManagerFacade.SendJobType(thisGame.snakes, j);

			}

			gameOver=true;
			System.out.println(">>>>>>>>>>>>>> GameOver <<<<<<<<<<<<<<<");

			sendScore = true;
			G_ManagerFacade.SendScores(out_communicators, thisGame);

			Thread.sleep(2000);
			sendScore = false;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
