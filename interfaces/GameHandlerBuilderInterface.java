package interfaces;

import games_handler.GH_Manager;

import java.io.IOException;

public interface GameHandlerBuilderInterface {
    void setInputPort(int port);
    void setOutputPort(int port);
    void setServerName(String serverName);
    void setBroadcastTimeInterval(int interval);
    void setNbp(int nbp);
    GH_Manager build() throws IOException;
}
