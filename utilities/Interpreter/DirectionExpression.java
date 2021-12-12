package utilities.Interpreter;

import utilities.GameOptions;
import utilities.Point;

import java.util.Objects;

public class DirectionExpression implements Expression{
    int data;

    public DirectionExpression(int data)
    {
        this.data = data;
    }

    public byte interpreter(Point one, Point two)
    {
        if ((two.x) % data == (one.x + 1)
                % data) {
            return 2;
        }
        if ((two.x+1) % data == (one.x)
                % data) {
            return 0;
        }
        if ((two.y) % data == (one.y + 1)
                % data) {
            return 3;
        }
        if ((two.y+1) % data == (one.y)
                % data) {
            return 1;
        }
        return 4;
    }
}
