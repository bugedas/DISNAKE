package utilities.Composite;

import game.Obstacle;
import utilities.Point;
import java.util.List;

public interface Object {
    Obstacle obstacle = new Obstacle();

    boolean isInBounds();
    void buildingLog();
    default List<Point> makeObstacle(){
        return obstacle.getBody();
    }
}
