package client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

class ManageRequestDirection implements Runnable { //gali but reikia public
	private BlockingDeque<Pair<Byte, Byte>> directionJobs;
	private ArrayBlockingQueue<Byte> requestDir;
	private byte direction, id;

	public ManageRequestDirection(BlockingDeque<Pair<Byte, Byte>> jobs, ArrayBlockingQueue<Byte> requestDir) {
		id = 0;
		directionJobs = jobs;
		this.requestDir = requestDir;
	}

	@Override
	public void run() {
		while (!requestDir.isEmpty()) {
			try {
				requestDir.take();
			} catch (InterruptedException e1) {
			}
		}
		while (true) {
			try {
				byte a = requestDir.take();
				ajouterrequestDirection(a);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setDirection(byte directionBis) {
		direction = directionBis;
		if (!directionJobs.isEmpty()) {
			byte maDirection = directionJobs.element().a;
			if (direction == maDirection)
				directionJobs.remove();
		}
	}

	void ajouterrequestDirection(byte a) throws InterruptedException {
		if (directionJobs.isEmpty()) {
			if (direction % 2 != a % 2) {
				directionJobs.put(new Pair<Byte, Byte>(a, id));
				id++;
				synchronized (directionJobs) {
					directionJobs.notify();
				}
			}
			return;
		}
		byte lastDir = directionJobs.peekLast().a;
		if (lastDir % 2 != a % 2) {
			directionJobs.put(new Pair<Byte, Byte>(a, id));
			id++;
		}
	}
}
