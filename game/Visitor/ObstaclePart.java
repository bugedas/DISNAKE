package game.Visitor;

public interface ObstaclePart {
    public void accept(ObstaclePartVisitor obstaclePartVisitor);
}
