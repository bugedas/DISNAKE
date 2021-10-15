package utilities.Factory;

public abstract class AbstractFactory {
    public abstract Food getFood(int foodType);

    public abstract Drink getDrink(int drinkType);
}
