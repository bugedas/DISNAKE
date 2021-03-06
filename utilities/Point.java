package utilities;


import utilities.TemplateMethod.BaseGrid;

public class Point extends BaseGrid {
	public int x, y;
	
	public Point(int x, int y) {
		while (x < 0)
			x += GameOptions.gridSize;
		while (y < 0)
			y += GameOptions.gridSize;
		this.x = x % GameOptions.gridSize;
		this.y = y % GameOptions.gridSize;
	}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
	@Override
	public boolean equals(Object o) {
		Point that = (Point) o;
		return that.x == this.x && that.y == this.y;
	}
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}
}
