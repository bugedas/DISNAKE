package utilities.Factory;

public class Apple extends Food{

	public int foodSize = 100;
	
	public Apple(){
		this.a = generate();
		System.out.println("apple exists");
	}
}
