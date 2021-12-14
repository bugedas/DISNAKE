package utilities.Composite;

import game.Obstacle;
import utilities.Point;

import java.util.List;

public class MakeObstacle implements Object {
    Obstacle obstacle = new Obstacle();
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    Point start;
    Point end;

    public MakeObstacle(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        start = new Point((int) startX, startY);
        end = new Point((int) endX, endY);
    }

    @Override
    public boolean isInBounds() {
        return false;
    }

    @Override
    public void buildingLog() {
        System.out.println("Building obstacle..");
    }

    public List<Point> makeObstacle(){
        obstacle.addObstacle(start, end);
        buildingLog();
        return obstacle.body;
    }
}
