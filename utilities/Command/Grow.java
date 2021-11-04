package utilities.Command;

import utilities.Snake;

public class Grow extends ICommand{

    public Grow(Snake snake) {
        super(snake);
    }

    @Override
    public void execute() {
        snake.growAmount(5);
    }

    @Override
    public void undo() {
        snake.shrinkAmount(5);
    }
}
