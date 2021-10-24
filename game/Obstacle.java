package game;

import utilities.Point;

import java.util.ArrayList;
import java.util.List;

public class Obstacle {
    private List<Point> body;
    public Obstacle(){
        body = new ArrayList<>();
    }
    public void addObstacle(Point start, Point end) {
        int startX = start.getX();
        int endX = end.getX();
        int distX = endX - startX;

        int startY = start.getY();
        int endY = end.getY();
        int distY = endY - startY;

        int steps = Math.max(Math.abs(distX), Math.abs(distY));
        float stepX = (float) distX / (float) steps;
        float stepY = (float) distY / (float) steps;

        double x = startX;
        double y = startY;

        for (int i = 0; i <= steps; i++) {
            Point point = new Point((int) Math.round(x), (int) Math.round(y));
            body.add(point);

            x += stepX;
            y += stepY;
        }
    }
}
