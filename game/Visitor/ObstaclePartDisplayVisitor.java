package game.Visitor;

import game.Obstacle;

public class ObstaclePartDisplayVisitor implements ObstaclePartVisitor {
    @Override
    public void visit(WallShape wallShape) {
        System.out.println("Displaying WallShape.");
    }

    @Override
    public void visit(WallSize wallSize) {
        System.out.println("Displaying WallSize.");
    }

    @Override
    public void visit(Obstacle obstacle) {
        System.out.println("Displaying Obstacle.");
    }
}
