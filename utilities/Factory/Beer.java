package utilities.Factory;

import utilities.Command.*;
import utilities.Snake;

public class Beer extends Drink{
    public int power;

    public void effect(Snake snake){
        SnakeController snakeCtrl = new SnakeController();
        ICommand shrinkCmd = new Shrink(snake);
        snakeCtrl.execute(shrinkCmd);
    }

    @Override
    public void eat() {

    }
}
