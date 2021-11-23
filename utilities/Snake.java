package utilities;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Snake implements Cloneable{

	@Override
	public Snake clone() {
		try {
			return (Snake) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public Snake clone(Point point, int size, byte id) {
		try {
			Snake clone = (Snake) super.clone();
			clone.id = id;
			clone.points = new LinkedList<Point>();
			for (int i = 0; i < size; i++) {
				Point tmp = new Point(point.x + i, point.y);
				clone.points.add(tmp);
			}
			clone.direction = Direction.East;
			clone.head = points.getLast();
			clone.score = 0;

			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	enum Direction {
		West, North, East, South
	}

	public Direction direction;
	public Point head;
	public byte id;
	public int score;
	LinkedList<Point> points; // last element is head

	public Snake(Point point, int size, byte id) {
		this.id = id;
		this.points = new LinkedList<Point>();
		for (int i = 0; i < size; i++) {
			Point tmp = new Point(point.x + i, point.y);
			this.points.add(tmp);
		}
		direction = Direction.East;
		head = points.getLast();
		score=0;
	}

	synchronized byte direction() {
		switch (this.direction) {
		case West:
			return (byte) 0;
		case North:
			return (byte) 1;
		case East:
			return (byte) 2;
		case South:
			return (byte) 3;

		default:
			break;
		}
		return (byte) -1;
	}

	synchronized public void direction(byte i) {
		if (direction() % 2 != i % 2) {
			switch (i) {
			case 0:
				this.direction = Direction.West;
				break;
			case 1:
				this.direction = Direction.North;
				break;
			case 2:
				this.direction = Direction.East;
				break;
			case 3:
				this.direction = Direction.South;
				break;

			default:
				break;
			}
		}
	}

	synchronized public void move() {
		points.removeFirst();
		int x = head.x;
		int y = head.y;
		MoveContext move = new MoveContext();
		switch (this.direction) {
		case North:
			move.setStrategy(new moveNorth());
			y = move.executeMove(y);
			break;
		case South:
			move.setStrategy(new moveSouth());
			y = move.executeMove(y);
			break;
		case East:
			move.setStrategy(new moveEast());
			x = move.executeMove(x);
			break;
		case West:
			move.setStrategy(new moveWest());
			x = move.executeMove(x);
			break;
		default:
			break;
		}
		Point newHead = new Point(x, y);
		points.addLast(newHead);
		head = newHead;
	}

	public interface StrategyMove{
		int move(int direction);
	}

	public static class moveNorth implements StrategyMove
	{
		@Override
		public int move(int direction) {
			return --direction;
		}
	}
	public static class moveSouth implements StrategyMove
	{
		@Override
		public int move(int direction) {
			return ++direction;
		}
	}
	public static class moveEast implements StrategyMove
	{
		@Override
		public int move(int direction) {
			return ++direction;
		}
	}
	public static class moveWest implements StrategyMove
	{
		@Override
		public int move(int direction) {
			return --direction;
		}
	}

	public class MoveContext
	{
		private StrategyMove strategy;

		public void setStrategy(StrategyMove strategy){
			this.strategy = strategy;
		}

		public int executeMove(int direction){
			return strategy.move(direction);
		}
	}


	synchronized public void grow() {
		points.addFirst(null);// will be immediately removed
		move();
	}

	synchronized public void growAmount(int amount) {
		for(int i=0; i<amount; i++){
			points.addFirst(null);// will be immediately removed
			move();
		}
	}

	synchronized public void shrinkAmount(int amount) {
		for(int i=0; i<amount; i++){
			points.removeFirst();// will be immediately removed
		}
//		move();
	}

	synchronized public boolean isInCollision(Point a) {
		if (a.equals(head) && a!=head)
			return true;
		return false;
	}

	synchronized public boolean isInCollisionWithWall(List<Point> wall) {
		for(Point w : wall){
			if (w.equals(head) && w!=head)
				return true;
		}
		return false;
	}

	synchronized public boolean isInCollision(Snake a) {
		for (Point b : a.points)
			if (isInCollision(b))
				return true;
		return false;
	}

	synchronized public Snake isInCollision(HashMap<Integer,Snake> a) {
		for (Snake b : a.values())
			if (isInCollision(b))
				return b;
		return null;
	}

	@Override
	synchronized public String toString() {
		String s = "Snake " + id + " [";
		for (Point p : points) {
			s += p.toString();
			if (!p.equals(head))
				s += ",";
		}
		s += "] goes " + direction;
		return s;
	}

	@Override
	public boolean equals(Object o) {
		Snake that = (Snake) o;
		return that.id == this.id;
	}
	
	synchronized static ByteBuffer encodeOneSnake(Snake sn){
		LinkedList<Point> s= new LinkedList<Point>();
		for(Point p: sn.points){
			s.addLast(p);
		}
		// s: queue > point > point > head
		
		
		LinkedList<Byte> d = new LinkedList<>();// collection of directions
		LinkedList<Byte> l = new LinkedList<>();// collection of lengths
		
		//Snake should be at least 2 Points long
		
		Point p = s.poll();
		Point q=p; // queue
		Point n = s.poll();
		byte direction=dir(p,n);
		byte length=1;
		
		while(n!=null){
			byte dir=dir(p,n);
			if(direction==dir)
				length++;
			else{
				d.addLast(direction);
				l.addLast(length);
				direction=dir;
				length=1;
			}
			direction=dir;	
			p=n;
			n=s.poll();
		}
		d.addLast(direction);
		l.addLast(length);
		
		length = (byte) l.size();
		
		ByteBuffer buf=ByteBuffer.allocate(length*2+4);
		
		// Then we prepare the buffer
		buf.put(sn.id);
		buf.put((byte) q.x);
		buf.put((byte) q.y);
		buf.put(length);
		while (!l.isEmpty()) {
			byte dir=d.poll();
			buf.put(dir);
			byte len =l.poll();
			buf.put(len);
			//System.out.print("["+dir+","+len+"]");
		}
		//System.out.println();
		
		return buf;
		
	}
	
	static byte dir(Point p, Point n){
		int mod =GameOptions.gridSize;
		if(p.x%mod == n.x%mod){
			//going North (1) or South (3)
			if((p.y+1)%mod==n.y%mod) return 3;
			return 1;
		}
		//going East (2) or West (0)
		if((p.x+1)%mod==n.x%mod) return 2;
		return 0;
	}

	synchronized static ByteBuffer encodeOneSnake2(Snake s) {
		/**
		 * We describe a snake more compactly : we only proEMPTY the points where
		 * the direction changes, and the length where it goes straight
		 */
		// we assume snakes's length is at least 2 in order to avoid NULL
		// Pointer Exception
		// we implement the new representation of snake before pushing it into
		// the buffer
		
		LinkedList<Point> obj= new LinkedList<Point>();
		for(Point p: s.points){
			obj.addLast(p);
		}
		
		ByteBuffer buf;
		LinkedList<Byte> length = new LinkedList<>();
		LinkedList<Byte> direction = new LinkedList<>();
		Point queue = obj.poll();
		System.out.print("La queue est " + queue);
		Point successeur = obj.poll();
		Point tmp = obj.poll();
		byte l = 1;
		byte dir = findDirection(queue, successeur);
		System.out.print(" allant vers " + dir);
		direction.push(dir);

		while (tmp != null) {
			byte tmpDir = findDirection(successeur, tmp);
			if (tmpDir == dir) {
				l++;
			} else {
				System.out.println(" de size " + l);
				length.addFirst(l);
				l = 1;
				dir = tmpDir;
				direction.addFirst(dir);
			}
			successeur = tmp;
			tmp = obj.poll();
		}
		System.out.println(" de size " + l);
		length.addFirst(l);
		l = (byte) length.size();
		
		buf=ByteBuffer.allocate(l*2+4);
		
		// Then we prepare the buffer
		buf.put(s.id);
		buf.put((byte) queue.x);
		buf.put((byte) queue.y);
		buf.put(l);
		while (!length.isEmpty()) {
			buf.put(direction.poll());
			buf.put(length.poll());
		}
		
		
		return buf;

	}

	synchronized static public ByteBuffer encodeAllSnakes(HashMap<Integer, Snake> S, List<Point> wall) {
		LinkedList<ByteBuffer> tmp = new LinkedList<ByteBuffer>();
		int size = 0;
		for (Snake s : S.values()) {
			// System.out.println("encodage du Snake  "+i);
			tmp.add(encodeOneSnake(s));
			size += tmp.getLast().capacity();
		}

		int wallSize = 1 + (wall.size() * 2);

		ByteBuffer buf = ByteBuffer.allocate(size + 2+2+2 +1+(3*Constants.playerNr) + wallSize);//+2 for the food; 1,1,3*x - for scores
		byte nb = (byte) S.size();
		buf.put((byte) 2);// TYPE
		buf = BufferHandler.sendScoresToCurrentBuff(S, buf);
		buf.put(nb);// NB SNAKES

		for (ByteBuffer b : tmp) {
			b.flip();
			buf.put(b);
		}

		return buf;
	}

	static byte findDirection(Point queue, Point succ) {
		// Attention, la direction va de A vers B
		if ((succ.x) % GameOptions.gridSize == (queue.x + 1)
				% GameOptions.gridSize) {
			return 2;
		}
		if ((succ.x+1) % GameOptions.gridSize == (queue.x)
				% GameOptions.gridSize) {
			return 0;
		}
		if ((succ.y) % GameOptions.gridSize == (queue.y + 1)
				% GameOptions.gridSize) {
			return 3;
		}
		if ((succ.y+1) % GameOptions.gridSize == (queue.y)
				% GameOptions.gridSize) {
			return 1;
		}
		System.out.println("ERROR : POINTS ARE NOT CLOSE TO EACH OTHER");
		return 4;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Creating a snake");
		Snake s = new Snake(new Point(10, 10), 5, (byte) 1);
		System.out.println(s);
		System.out.println();
		System.out.println("Testing directions and move()");
		s.direction((byte) 3);
		s.move();
		System.out.println(s);

		s.direction((byte) 2);
		s.move();
		System.out.println(s);

		System.out.println();
		System.out.println("Testing grow()");
		s.direction((byte) 1);
		s.grow();
		System.out.println(s);

		System.out.println();
		System.out.println("Testing collision");
		Snake s2 = new Snake(new Point(10, 9), 5, (byte) 2);
		System.out.println(s2);
		s2.move();
		

		System.out.println();
		System.out.println("Testing grid");
		Snake s3 = new Snake(new Point(120, 120), 10, (byte) 3);
		System.out.println(s3);
		s3.direction((byte) 0);
		for (int i = 0; i < 8; i++)
			s3.move();
		System.out.println(s3);

	}

}
