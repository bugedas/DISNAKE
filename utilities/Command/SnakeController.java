package utilities.Command;

import java.util.ArrayList;

public class SnakeController {
    private ArrayList<ICommand> commandList = new ArrayList<ICommand>();

    public ArrayList<ICommand> getCommandList(){
        return commandList;
    }
    public void execute(ICommand command){
        commandList.add(command);
        command.execute();
    }
    public void undo(ICommand command){
        commandList.remove(command);
        command.undo();

    }
}
