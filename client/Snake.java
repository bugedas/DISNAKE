package client;
import java.awt.Color;
import java.util.LinkedList;

// La classe Snake code les serpents sur une liste de points
public class Snake {
	public byte direction;
	byte number;
	LinkedList<Point> points;

	public Snake(byte direction, byte number, LinkedList<Point> points){
		this.direction=direction;
		this.number =number;
		this.points=points;
	}
	
	@Override
	public String toString(){
		String s = "Snake " + number + " [";
		for (Point p : points) {
			s += p.toString();
			s += ",";
		}
		s += "] goes " + direction;
		return s;
	}
}

// Les points sont deux coordonnees et une couleur
class Point {
	int x, y;
	Color color;
	private final int size = utilities.GameOptions.gridSize;

	Point(int x, int y) {
		while (x < 0)
			x += size;
		while (y < 0)
			y += size;
		this.x = x % size;
		this.y = y % size;
		color = Color.white;
	}
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}
}