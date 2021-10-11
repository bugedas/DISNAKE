package utilities;

public class GetFoodFactory {

    public static Food getFood(int foodType){
        if (foodType == 0){
            return new Apple();
        }
        else if (foodType == 1){
            return new Poison();
        }
        else return null;
    }
}
