package utilities.Factory;

public class DrinkFactory extends AbstractFactory{
    public Drink getDrink(int drinkType){
        if (drinkType == 0){
            return new Protein();
        }
        else if (drinkType == 1){
            return new Beer();
        }
        else return null;
    }

    public Food getFood(int foodType){
        return null;
    }
}
