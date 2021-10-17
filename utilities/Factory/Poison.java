package utilities.Factory;

import utilities.Snake;

public class Poison extends Food{

    public int foodSize = -100;

    public Poison(){
        this.a = generate();
        System.out.println("Poison exists");
    }

    @Override
    public int getFoodSize() {
        return this.foodSize;
    }

    @Override
    public void eat() {

    }

    @Override
    public void effect(Snake s) {

    }
}
