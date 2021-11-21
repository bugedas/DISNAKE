package utilities.Factory;

import utilities.Bridge.ObjectColor;

import java.util.HashMap;
import java.util.Map;

public class ObjectColorFlyweightFactory {
    static Map<String, ObjectColor> colors = new HashMap<>();

    public static ObjectColor getObjectColor(String name, ObjectColor color) {

        return colors.computeIfAbsent(name, k -> color);
    }
}
