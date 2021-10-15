package utilities.Factory;

public class FoodFactory extends AbstractFactory {

    public Food getFood(int foodType){
        if (foodType == 0){
            return new Apple();
        }
        else if (foodType == 1){
            return new Poison();
        }
        else return null;
    }

    public Drink getDrink(int drinkType){
        return null;
    }
}
