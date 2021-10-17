package utilities.Factory;

import interfaces.Eatable;
import utilities.GameOptions;
import utilities.Point;

import java.nio.ByteBuffer;

public abstract class Food implements Eatable {
    public Point a;

    public Point generate(){
        int x=(int) (Math.random()* GameOptions.gridSize);
        int y=(int) (Math.random()*GameOptions.gridSize);
        return new Point(x,y);
    }

    public ByteBuffer toBuffer(){
        ByteBuffer b = ByteBuffer.allocate(2);
        b.put((byte)a.x);
        b.put((byte)a.y);
        b.flip();
        return b;
    }

    @Override
    public Point getPoint()
    {
        return this.a;
    }
}
