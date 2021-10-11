package utilities;

import java.nio.ByteBuffer;

public abstract class Food {
    public Point a;
    public int foodSize;

    public Point generate(){
        int x=(int) (Math.random()*GameOptions.gridSize);
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
}
