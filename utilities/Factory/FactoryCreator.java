package utilities.Factory;

public class FactoryCreator {
    public static AbstractFactory getFactory(String type){
        if(type.equalsIgnoreCase("Food")){
            return new FoodFactory();
        } else if(type.equalsIgnoreCase("Drink")){
            return new DrinkFactory();
        }
        return null;
    }
}
