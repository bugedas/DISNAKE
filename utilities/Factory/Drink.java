package utilities.Factory;

import interfaces.Eatable;
import utilities.Point;
import utilities.Snake;

import java.nio.ByteBuffer;

public abstract class Drink implements Eatable {
    public int power;

    public abstract void effect(Snake s);

    @Override
    public ByteBuffer toBuffer() {
        return null;
    }

    @Override
    public Point getPoint()
    {
        return null;
    }

    @Override
    public int getFoodSize()
    {
        return 0;
    }
}
