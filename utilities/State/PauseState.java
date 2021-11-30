package utilities.State;
import game.G_Manager;

public class PauseState extends State {

    PauseState(G_Manager manager) {
        super(manager);
        manager.sendTimer = true;
    }

    @Override
    public String pause() {
        manager.changeState(new PlayingState(manager));
        return "Paused";
    }

    @Override
    public String resume() {
        manager.changeState(new PlayingState(manager));
        return "Resumed";
    }
}