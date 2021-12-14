package utilities.Composite;

import game.Obstacle;
import utilities.Point;

import java.util.List;



public class MakeRandomObstacle implements Object {
    Obstacle obstacle;
    Point start;
    Point end;
    List<Point> body;

    public MakeRandomObstacle(){
        this.start = new Point((int) ((Math.random() * (55 - 15)) + 15), (int) ((Math.random() * (55 - 15)) + 15));
        this.end = new Point((int) ((Math.random() * (55 - 15)) + 15), (int) ((Math.random() * (55 - 15)) + 15));
    }

    @Override
    public boolean isInBounds() {
        return false;
    }

    @Override
    public void buildingLog() {
        System.out.println("Building random obstacle..");
    }

    public List<Point> makeObstacle(){
        this.body = obstacle.getBody();
        obstacle.addObstacle(start, end);
        buildingLog();
        return this.body;
    }
}

