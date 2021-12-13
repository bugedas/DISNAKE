package game.Visitor;

import game.Obstacle;

public interface ObstaclePartVisitor {
    public void visit(WallShape wallShape);
    public void visit(WallSize wallSize);
    public void visit(Obstacle obstacle);
}
