package utilities.TemplateMethod;

import utilities.Bridge.Green;
import utilities.Bridge.ObjectColor;
import utilities.Bridge.Red;
import utilities.Factory.ObjectColorFlyweightFactory;

import javax.swing.*;
import java.awt.*;

public class BaseGrid {
    byte EMPTY = 0, FULL = 1, APPLE = 2, PERSO = 3, POISON = 4, WALL = 5, cellSize = 10;
    public JFrame frames;

    public void setBounds(int x, int y, int width, int height) {
        this.frames.setBounds(x, y, width, height);
    }

    public void fill(Graphics g, int i, int j, byte color) {
        ObjectColor colorRed = ObjectColorFlyweightFactory.getObjectColor("poison", new Red());
        ObjectColor colorGreen =ObjectColorFlyweightFactory.getObjectColor("food", new Green());
        g.setColor((color == EMPTY) ? Color.WHITE
                : (color == FULL) ? Color.BLACK : (color == APPLE) ? colorRed.colorize()
                : (color == PERSO) ? Color.blue  : (color == POISON) ? colorGreen.colorize()
                : color == WALL ? Color.CYAN : Color.gray);

        g.fillRect(i * cellSize, j * cellSize,
                cellSize - cellSize / 8, cellSize - cellSize / 8);
    }
}
