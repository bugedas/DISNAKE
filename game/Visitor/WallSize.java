package game.Visitor;

public class WallSize implements ObstaclePart {
    @Override
    public void accept(ObstaclePartVisitor obstaclePartVisitor) {
        obstaclePartVisitor.visit(this);
    }
}
