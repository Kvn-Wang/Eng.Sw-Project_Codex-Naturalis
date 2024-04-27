package it.polimi.codexnaturalis.network.socket;

import it.polimi.codexnaturalis.network.ServerMain;

public class SocketServer extends Thread {
    ServerMain serverMain;

    public SocketServer(ServerMain sermarMain) {
        this.serverMain = serverMain;
    }
}
