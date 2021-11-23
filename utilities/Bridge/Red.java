package utilities.Bridge;
import interfaces.ObjectColorInterface;

import java.awt.Color;
public class Red extends ObjectColor implements ObjectColorInterface {
    public Red(){
        super();
    }

    @Override
    public Color colorize() {
        return Color.RED;
    }
}
