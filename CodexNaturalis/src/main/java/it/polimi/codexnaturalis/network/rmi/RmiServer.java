package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RmiServer extends Thread implements VirtualServer {
    private Registry registry;
    private Map<String, VirtualView> nicknameLessClients;
    ServerContainer serverContainer;

    public RmiServer() {
        this.serverContainer = ServerContainer.getInstance();
        nicknameLessClients = new HashMap<>();
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {
        nicknameLessClients.put(UUID, client); // Store client with its identifier

        System.out.println("Someone Connected");
    }

    @Override
    public boolean setNickname(String userID, String nickname) throws RemoteException {
        if(serverContainer.playerCreation(nicknameLessClients.get(userID), nickname)) {
            nicknameLessClients.remove(userID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        ArrayList<LobbyInfo> lobbiesInfo = new ArrayList<>();

        for(Lobby elem : serverContainer.getActiveLobby()) {
            lobbiesInfo.add(elem.getLobbyInfo());
        }

        return lobbiesInfo;
    }

    @Override
    public ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        return serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        serverContainer.leaveLobby(playerNickname);
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(serverContainer.lobbyCreation(lobbyName)) {
            System.out.println(lobbyName + " lobby has been created");

            serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            System.out.println(playerNickname + " has joined " + lobbyName + " lobby");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setPlayerColor(String nickname, ColorType colorChosen) {
        System.out.println("received command color");
        serverContainer.setPlayerColor(nickname, colorChosen);
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        serverContainer.setPlayerReady(playerNickname);
    }

    public void run() {
        VirtualServer engine = new RmiServer();
        VirtualServer stub = null;
        String name = UtilCostantValue.RMIServerName;

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            registry = LocateRegistry.createRegistry(UtilCostantValue.portRmiServer);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RMI server started.");
    }
}
