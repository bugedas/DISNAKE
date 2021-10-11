package utilities;

public class Poison extends Food{

    public int foodSize = -100;

    public Poison(){
        this.a = generate();
        System.out.println("Poison exists");
    }
}
