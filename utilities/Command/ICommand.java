package utilities.Command;

import utilities.Snake;

public abstract class ICommand {
    protected Snake snake;

    public ICommand(Snake snake){
        super();
        this.snake = snake;
    }
    public abstract void execute();
    public abstract void undo();
}
