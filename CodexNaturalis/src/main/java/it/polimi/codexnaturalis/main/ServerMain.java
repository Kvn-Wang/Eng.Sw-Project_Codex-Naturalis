package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.network.rmi.RmiServer;

public class ServerMain {
    public static void main(String[] args) {
        new RmiServer().run();
        //new SocketServer().run();
    }
}
