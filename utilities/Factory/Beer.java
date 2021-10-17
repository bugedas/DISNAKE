package utilities.Factory;

import utilities.Snake;

public class Beer extends Drink{
    public int power;

    public void effect(Snake snake){
        snake.shrinkAmount(5);
    }

    @Override
    public void eat() {

    }
}
