package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.rmi.RmiServer;
import it.polimi.codexnaturalis.network.socket.SocketServer;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

public class ServerMain {
    private String ipAddress = UtilCostantValue.ipAddress;
    private int portNumber = UtilCostantValue.portNumber;

    public static void main(String[] args) {
        new RmiServer().run();
        //new SocketServer().run();
    }
}
