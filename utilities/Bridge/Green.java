package utilities.Bridge;
import interfaces.ObjectColorInterface;

import java.awt.Color;
public class Green extends ObjectColor implements ObjectColorInterface {
    public Green(){
        super();
    }

    @Override
    public Color colorize() {
        return Color.GREEN;
    }
}
