package utilities.Mediator;

import client.DisplayManagement;

import java.net.InetSocketAddress;

public interface ParametersMediatorInterface {
    void setServer(InetSocketAddress server);

    void setServerName(String serverName);

    void setWindow(DisplayManagement window);

    String getServerName();

    DisplayManagement getWindow();

    InetSocketAddress getServer();
}
