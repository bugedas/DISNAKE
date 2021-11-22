package game;

import interfaces.ObstacleInterface;
import utilities.Point;

import java.nio.ByteBuffer;
import java.util.List;

public class ObstacleProxy implements ObstacleInterface {

    private Obstacle obstacle;

    @Override
    public List<Point> getWall() {
        if(obstacle == null){
            obstacle = new Obstacle();
        }
        return obstacle.getWall();
    }

    public ByteBuffer toBuffer(){
        if(obstacle == null){
            obstacle = new Obstacle();
        }
        return obstacle.toBuffer();
    }
}
