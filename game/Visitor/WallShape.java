package game.Visitor;

public class WallShape implements ObstaclePart{
    @Override
    public void accept(ObstaclePartVisitor obstaclePartVisitor) {
        obstaclePartVisitor.visit(this);
    }
}
