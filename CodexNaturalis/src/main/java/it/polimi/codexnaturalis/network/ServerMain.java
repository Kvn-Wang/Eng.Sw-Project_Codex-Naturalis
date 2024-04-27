package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.rmi.RmiServer;
import it.polimi.codexnaturalis.network.rmi.VirtualView;
import it.polimi.codexnaturalis.network.socket.SocketServer;

import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    final int portNumber;
    final int ipAddres;
    List<VirtualView> lobbyLessClients = new ArrayList<>();
    ArrayList<LobbyThread> activeLobby;
    RmiServer rmiServer;
    SocketServer socketServer;

    public ServerMain(int portNumber, int ipAddres) {
        this.portNumber = portNumber;
        this.ipAddres = ipAddres;

        rmiServer = new RmiServer(this);
        socketServer = new SocketServer(this);

        rmiServer.run();
        socketServer.run();
    }

    LobbyThread lobbyCreation(String lobbyname) {
        for(LobbyThread elem : activeLobby) {
            if(elem.getLobbyName() == lobbyname) {
                return null;
            }
        }

        LobbyThread newLobbyThread = new LobbyThread(lobbyname);
        activeLobby.add(newLobbyThread);
        return newLobbyThread;
    }

    boolean checkNickGlobalNicknameValidity() {
        return false;
    }
}
