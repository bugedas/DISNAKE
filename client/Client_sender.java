package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingDeque;

// A TESTER
public class Client_sender implements Runnable {
	public InetSocketAddress server;
	public BlockingDeque<Pair<Byte,Byte>> directionJobs;
	private short gamePort;
	private byte number;
	public byte id;

	public Client_sender(InetSocketAddress server, BlockingDeque<Pair<Byte,Byte>> jobs, short gP, byte num) {
		this.server = server;
		this.directionJobs = jobs;
		gamePort = gP;
		number = num;
		id = 0;
	}

	@Override
	public void run() {
		try {
			DatagramChannel speakerChannel = DatagramChannel.open();
			speakerChannel.socket().bind(new InetSocketAddress(0));
			InetSocketAddress remote = new InetSocketAddress(server.getAddress(), gamePort);
			while (true) {
				synchronized (directionJobs) {
					Pair<Byte,Byte> dirId = directionJobs.peek();
					if (dirId==null) {
						directionJobs.wait();
					}
					else speakerChannel.send(changeDirection(number, dirId), remote);
					Thread.sleep(5);
				}
			}
		} catch (IOException | InterruptedException e) {
		}
	}

	public ByteBuffer changeDirection(byte number, Pair<Byte,Byte> dirId) {
		ByteBuffer res = ByteBuffer.allocate(4);
		res.put((byte) 2);
		res.put(dirId.b);
		res.put(number);
		res.put(dirId.a);
		res.flip();
		return res;
	}
}
