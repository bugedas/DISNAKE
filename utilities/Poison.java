package utilities;

import java.nio.ByteBuffer;

public class Poison extends Food{

    public Poison(){
        this.a = generate();
        System.out.println("Poison exists");
    }

    public ByteBuffer toBuffer(){
        ByteBuffer b = ByteBuffer.allocate(2);
        b.put((byte)a.x);
        b.put((byte)a.y);
        b.flip();
        return b;
    }
}
