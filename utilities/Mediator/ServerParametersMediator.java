package utilities.Mediator;

import client.DisplayManagement;

import java.net.InetSocketAddress;
import java.util.Objects;

public class ServerParametersMediator {
    private InetSocketAddress server;

    private String serverName;

    private DisplayManagement window = null;

    public String getServerName() {
        return serverName;
    }

    public DisplayManagement getWindow() {
        return window;
    }

    public void setServer(InetSocketAddress server) {
        this.server = server;
    }

    public InetSocketAddress getServer() {
        return server;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setWindow(DisplayManagement window) {
        this.window = window;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerParametersMediator that = (ServerParametersMediator) o;
        return Objects.equals(server, that.server) && Objects.equals(serverName, that.serverName) && Objects.equals(window, that.window);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, serverName, window);
    }

    @Override
    public String toString() {
        return "ServerParametersMediator{" +
                "server=" + server +
                ", serverName='" + serverName + '\'' +
                ", window=" + window +
                '}';
    }
}
