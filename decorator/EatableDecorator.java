package decorator;

import interfaces.Eatable;
import utilities.Point;
import utilities.Snake;

import java.nio.ByteBuffer;

public class EatableDecorator implements Eatable {
    protected Eatable decoratedEatable;

    public EatableDecorator(Eatable decoratedEatable)
    {
        this.decoratedEatable = decoratedEatable;
    }

    @Override
    public int getFoodSize() {
        return this.decoratedEatable.getFoodSize();
    }

    @Override
    public Point getPoint() {
        return this.decoratedEatable.getPoint();
    }

    @Override
    public void eat() {
        this.decoratedEatable.eat();
    }

    @Override
    public void effect(Snake s) {
        this.decoratedEatable.effect(s);
    }

    @Override
    public ByteBuffer toBuffer() {
        return this.decoratedEatable.toBuffer();
    }
}