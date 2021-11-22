package interfaces;

import utilities.Point;

import java.nio.ByteBuffer;
import java.util.List;

public interface ObstacleInterface {

    ByteBuffer toBuffer();

    List<Point> getWall();
}
