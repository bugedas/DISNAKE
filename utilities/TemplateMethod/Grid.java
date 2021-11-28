package utilities.TemplateMethod;

import javax.swing.*;
import java.awt.*;

public class Grid extends BaseGrid {
    public Grid(String name) {
        frames = new JFrame(name);
    }

    public Dimension getSize() {
        return this.frames.getSize();
    }

    public JFrame getFrames() {
        return this.frames;
    }
}
