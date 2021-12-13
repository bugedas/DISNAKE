package game;

import game.Visitor.ObstaclePart;
import game.Visitor.ObstaclePartVisitor;
import game.Visitor.WallShape;
import game.Visitor.WallSize;
import interfaces.ObstacleInterface;
import utilities.Point;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Obstacle implements ObstacleInterface, ObstaclePart {
    public List<Point> body = new ArrayList<>();
    ObstaclePart[] parts;

    public Obstacle(){
        parts = new ObstaclePart[] {new WallShape(), new WallSize()};
        generateWall();
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

    public ByteBuffer toBuffer(){
        ByteBuffer b = ByteBuffer.allocate((body.size()*2) + 1);

        b.put((byte)body.size());
        for (Point bo : body){
            b.put((byte)bo.x);
            b.put((byte)bo.y);
        }

        b.flip();
        return b;
    }

    public List<Point> generateWall(){

        for(int i = 0; i < 3; i++) {
            Point start = new Point((int) ((Math.random() * (55 - 15)) + 15), (int) ((Math.random() * (55 - 15)) + 15));
            Point end = new Point((int) ((Math.random() * (55 - 15)) + 15), (int) ((Math.random() * (55 - 15)) + 15));
            addObstacle(start, end);
        }

        return this.body;
    }

    @Override
    public void accept(ObstaclePartVisitor obstaclePartVisitor) {
        for (int i = 0; i < parts.length; i++) {
            parts[i].accept(obstaclePartVisitor);
        }
        obstaclePartVisitor.visit(this);
    }

    public List<Point> getWall(){
        return this.body;
    }
}
