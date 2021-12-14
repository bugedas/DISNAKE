package utilities.Composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundObject {
    protected List<Object> children = new ArrayList<>();

    public void add(Object component) {
        children.add(component);
    }

    public void add(Object... components) {
        children.addAll(Arrays.asList(components));
    }

    public void remove(Object child) {
        children.remove(child);
    }

    public void remove(Object... components) {
        children.removeAll(Arrays.asList(components));
    }

    public void clear() {
        children.clear();
    }
}
