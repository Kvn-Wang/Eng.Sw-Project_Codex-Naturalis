package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.network.ServerMain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RmiServer extends Thread implements VirtualServer {
    ServerMain serverMain;

    public RmiServer(ServerMain sermarMain) {
        this.serverMain = serverMain;
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        //lobbyLessClients.add(client);
    }
}
