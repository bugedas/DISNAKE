package utilities.Factory;

import utilities.Snake;

public class Protein extends Drink{
    public int power;

    public void effect(Snake snake){
        snake.growAmount(5);
    }
}
