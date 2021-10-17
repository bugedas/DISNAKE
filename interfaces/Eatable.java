package interfaces;

import utilities.Point;
import utilities.Snake;

import java.nio.ByteBuffer;

public interface Eatable {
    int getFoodSize();
    Point getPoint();

    void eat();

    void effect(Snake s);

    ByteBuffer toBuffer();
}
