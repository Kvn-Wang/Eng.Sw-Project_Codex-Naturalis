package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.network.rmi.RmiServer;
import it.polimi.codexnaturalis.network.socket.SocketServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        new RmiServer().start();
        new SocketServer().start();
    }
}
