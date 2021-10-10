package builders;

import games_handler.GH_Manager;
import interfaces.GameHandlerBuilderInterface;

import java.io.IOException;

public class GameHandlerBuilder implements GameHandlerBuilderInterface {
    private final GH_Manager manager;

    public GameHandlerBuilder() {
        this.manager = new GH_Manager();
    }

    @Override
    public void setInputPort(int port) {
        this.manager.inputPort = port;
    }

    @Override
    public void setOutputPort(int port) {
        this.manager.outputPort = port;
    }

    @Override
    public void setServerName(String serverName) {
        this.manager.serverName = serverName;
    }

    @Override
    public void setBroadcastTimeInterval(int interval) {
        this.manager.broadcastTimeInterval = interval;
    }

    @Override
    public void setNbp(int nbp) {
        this.manager.nbP = nbp;
    }

    @Override
    public GH_Manager build() throws IOException {
        this.manager.start();
        return this.manager;
    }
}
