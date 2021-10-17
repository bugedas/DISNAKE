package utilities.Factory;

import utilities.Snake;

public class Apple extends Food{
	public int foodSize = 100;

	public Apple(){
		this.a = generate();
		System.out.println("apple exists");
	}

	@Override
	public int getFoodSize() {
		return this.foodSize;
	}

	@Override
	public void eat() {

	}

	@Override
	public void effect(Snake s) {

	}
}
