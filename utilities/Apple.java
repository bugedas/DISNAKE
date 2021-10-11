package utilities;

import java.nio.ByteBuffer;

public class Apple extends Food{
	
	public Apple(){
		this.a = generate();
		System.out.println("apple exists");
	}

	public ByteBuffer toBuffer(){
		ByteBuffer b = ByteBuffer.allocate(2);
		b.put((byte)a.x);
		b.put((byte)a.y);
		b.flip();
		return b;
	}
}
