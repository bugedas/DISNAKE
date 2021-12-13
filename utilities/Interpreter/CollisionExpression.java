package utilities.Interpreter;

import utilities.Point;

public class CollisionExpression implements Expression{
    Byte data;

    public CollisionExpression(Byte data)
    {
        this.data = data;
    }

    public byte interpreter(Point one, Point two)
    {
        if (one.equals(two) && one!=two)
            return 1;
        return data;
    }
}
