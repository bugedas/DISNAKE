package client;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

// OK : calcule la nouvelle gate a afficher a partir des hashmaps de serpent
class ManagementBackgate implements Runnable {
	private ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>> gateJobs;
	private DisplayManagement gameDisplay;
	private byte myNumber;
	private ManageRequestDirection administrator;
	private final int size = utilities.GameOptions.gridSize;
	
	protected ManagementBackgate(ArrayBlockingQueue<Triplet<HashMap<Byte, Snake>, Point, Point, List<Point>>> jobs, DisplayManagement display, byte number, ManageRequestDirection gest){
		this.gateJobs = jobs;
		this.gameDisplay = display;
		this.myNumber = number;
		administrator = gest;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>> req = (Triplet<HashMap<Byte, Snake>, Point, Point, List <Point>>) gateJobs.take();
				// System.out.print("On a recu un packet de serpents... ");
				byte[][] backgate = calculBackgate(req.a);
				backgate[req.b.x][req.b.y] = DisplayManagement.APPLE;
				backgate[req.c.x][req.c.y] = DisplayManagement.POISON;
				for(Point p : req.d){
					backgate[p.x][p.y] = DisplayManagement.WALL;
				}

				gameDisplay.swap(backgate);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private byte[][] calculBackgate(HashMap<Byte, Snake> req) {
		// System.out.print("On appele calculBackgate... ");
		byte[][] gate = new byte[size][size];
		for (Snake s : req.values())
			afficher(s, gate);
		return gate;
	}
	
	private void afficher(Snake s, byte[][] gate) {
		// System.out.print("On regarde le serpent number " + s.number + "... ");
		byte couleur = DisplayManagement.FULL;
		if(s.number ==myNumber){
			// System.out.println("je vais dans la direction " + s.direction);
			couleur = DisplayManagement.PERSO;
			administrator.setDirection(s.direction);
		}
		for (Point p : s.points)
			gate[p.x][p.y] = couleur;
	}
}
