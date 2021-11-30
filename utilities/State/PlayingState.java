package utilities.State;

import game.G_Manager;

public class PlayingState extends State {

    PlayingState(G_Manager manager) {
        super(manager);
        manager.sendTimer = false;
    }

    @Override
    public String pause() {
        manager.changeState(new PauseState(manager));
        return "Paused";
    }

    @Override
    public String resume() {
        manager.changeState(new PauseState(manager));
        return "Playing";
    }
}