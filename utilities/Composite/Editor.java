package utilities.Composite;

public class Editor {
    private CompoundObject allObjects = new CompoundObject();

    public void loadObstacles(Object... objects){
        allObjects.clear();
        allObjects.add(objects);
    }
}
