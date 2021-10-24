package game;

import utilities.Client;
import utilities.Job;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class G_ManagerFacade {
    public static void SendTimer(HashMap<Client, ArrayBlockingQueue<Job>> out_communicators) throws InterruptedException {
        for (byte timer = 5; timer > 0; timer--) {
            for (Client c : out_communicators.keySet()) {
                Job j = new Job(Job.Type.SEND_TIMER);
                j.timer(timer);
                out_communicators.get(c).put(j);
                System.out.println("G_Manager sent job \"" + j.type()
                        + "\" to Runnable_Output for Client " + c.id);
            }
            Thread.sleep(950);// a bit less than a second
        }
    }

    public static void SendPositions (HashMap<Client, ArrayBlockingQueue<Job>> out_communicators, int inputPort) throws InterruptedException {
        for (Client c : out_communicators.keySet()) {
            Job j = new Job(Job.Type.SEND_POSITIONS);
            j.id(c.id);
            j.port(inputPort);
            out_communicators.get(c).put(j);
            System.out.println("G_Manager sent job \"" + j.type()
                    + "\" to Runnable_Output for Client " + c.id);
        }
    }

    public static void SendScores(HashMap<Client, ArrayBlockingQueue<Job>> out_communicators, Game thisGame) throws InterruptedException {
        for (Client c : out_communicators.keySet()) {
            Job j = new Job(Job.Type.SEND_SCORES);
            j.id(c.id);
            j.snakes = thisGame.snakesAtStart;
            out_communicators.get(c).put(j);
            System.out.println("G_Manager sent job \"" + j.type()
                    + "\" to Runnable_Output for Client " + c.id);
        }
    }
}
