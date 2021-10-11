package utilities;

import java.nio.ByteBuffer;

abstract class Food {
    public Point a;

    public Point generate(){
        int x=(int) (Math.random()*GameOptions.gridSize);
        int y=(int) (Math.random()*GameOptions.gridSize);
        return new Point(x,y);
    }
}
