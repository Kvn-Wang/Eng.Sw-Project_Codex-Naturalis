package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.network.Lobby.Lobby;
import it.polimi.codexnaturalis.network.Lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.ServerContainer;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RmiServer extends Thread implements VirtualServer {
    private Registry registry;
    private String name = UtilCostantValue.RMIServerName;
    private Map<String, VirtualView> nicknameLessClients;
    ServerContainer serverContainer;

    public RmiServer() {
        this.serverContainer = ServerContainer.getInstance();
        nicknameLessClients = new HashMap<>();
    }

    @Override
    public String connect(VirtualView client) throws RemoteException, InterruptedException {
        String clientId;

        clientId = UUID.randomUUID().toString(); // Generate unique identifier
        nicknameLessClients.put(clientId, client); // Store client with its identifier

        System.out.println("Someone Connected");

        /*for (Map.Entry<String, VirtualView> entry : nicknameLessClients.entrySet()) {
            System.out.println("Lista K: "+ entry.getKey());
            System.out.println("Lista V: "+ entry.getValue());
        } */

        return clientId;
    }

    @Override
    public boolean setNickname(String userID, String nickname) throws RemoteException {
        if(serverContainer.checkNickGlobalNicknameValidity(nickname)) {
            System.out.println("Created player nickname: " + nickname);
            serverContainer.playerCreation(nicknameLessClients.get(userID), nickname);
            nicknameLessClients.remove(userID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby(String nickname) throws RemoteException {
        /*Gson gson = new Gson();
        String json;*/
        ArrayList<LobbyInfo> lobbiesInfo = new ArrayList<>();

        //TODO da pulire
        for(Lobby elem : serverContainer.getActiveLobby()) {
            lobbiesInfo.add(elem.getLobbyInfo());
        }
        //json = gson.toJson(lobbies);

        //System.out.println("Lobby Json sent: " + json);

        return lobbiesInfo;
    }

    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        for(Lobby elem : serverContainer.getActiveLobby()) {
            if(elem.getLobbyName().equals(lobbyName)) {
                return serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            }
        }

        return false;
    }

    @Override
    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException {
        serverContainer.leaveLobby(playerNickname, lobbyName);
        System.out.println(playerNickname + " has left " + lobbyName + " lobby");
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(serverContainer.checkNickGlobalLobbyNameValidity(lobbyName)) {
            serverContainer.lobbyCreation(lobbyName);
            System.out.println(lobbyName + " lobby has been created");

            serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            System.out.println(playerNickname + " has joined " + lobbyName + " lobby");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException {
        serverContainer.setPlayerReady(playerNickname, lobbyName);
    }

    public void run() {
        VirtualServer engine = new RmiServer();
        VirtualServer stub = null;

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            registry = LocateRegistry.createRegistry(UtilCostantValue.portNumberServer);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RMI server started");
    }
}
