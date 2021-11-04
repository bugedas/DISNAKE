package utilities.Factory;

import utilities.Snake;
import utilities.Command.*;

public class Protein extends Drink{
    public int power;

    public void effect(Snake snake){
        SnakeController snakeCtrl = new SnakeController();
        ICommand growCmd = new Grow(snake);
        snakeCtrl.execute(growCmd);

    }

    @Override
    public void eat() {

    }
}
