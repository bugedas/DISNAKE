package utilities.Command;

import utilities.Snake;

public class Shrink extends ICommand{

    public Shrink(Snake snake) {
        super(snake);
    }

    @Override
    public void execute() {
        snake.shrinkAmount(5);
    }

    @Override
    public void undo() {
        snake.growAmount(5);
    }
}
