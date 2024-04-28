package it.polimi.codexnaturalis.network.rmi;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.network.Lobby.LobbyThread;
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
    private String name = "VirtualServer";
    private Map<String, VirtualView> nicknameLessClients;
    private String clientId;
    ServerContainer serverContainer;

    public RmiServer() {
        this.serverContainer = ServerContainer.getInstance();
        nicknameLessClients = new HashMap<>();
    }

    // TODO: come contraddistinguere nickname già preso ma offline od online?
    @Override
    public void connect(VirtualView client) throws RemoteException {
        this.clientId = UUID.randomUUID().toString(); // Generate unique identifier
        nicknameLessClients.put(clientId, client); // Store client with its identifier

        System.out.println("Connected client: "+clientId);
        client.initializeClient();
    }

    @Override
    public String getPersonalID() throws RemoteException {
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
    public String getAvailableLobby(String nickname) throws RemoteException {
        Gson gson = new Gson();
        String json;
        ArrayList<LobbyInfo> lobbies = new ArrayList<>();

        //TODO da pulire
        for(LobbyThread elem : serverContainer.getActiveLobby()) {
            lobbies.add(elem.getLobbyInfo());
        }
        json = serializeLobbies(lobbies);

        //json = gson.toJson(lobbies);

        System.out.println("Lobby Json sent: " + json);

        return json;
    }

    private static String serializeLobbies(ArrayList<LobbyInfo> lobbies) {
        StringBuilder json = new StringBuilder("[");
        for (LobbyInfo lobby : lobbies) {
            json.append("{");
            json.append("\"lobbyName\": \"" + lobby.getLobbyName() + "\",");
            json.append("\"isLobbyStarted\": " + lobby.getIsLobbyStarted() + ",");
            json.append("\"maxPlayer\": " + lobby.getMaxPlayer() + ",");
            json.append("\"currentPlayer\": " + lobby.getCurrentPlayer());
            json.append("},");
        }
        if (json.charAt(json.length() - 1) == ',') {
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }


    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        for(LobbyThread elem : serverContainer.getActiveLobby()) {
            if(elem.getLobbyName().equals(lobbyName)) {
                return serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            }
        }

        return false;
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(serverContainer.checkNickGlobalLobbyNameValidity(lobbyName)) {
            serverContainer.lobbyCreation(lobbyName);
            serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        VirtualServer engine = new RmiServer();
        VirtualServer stub = null;

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(UtilCostantValue.portNumber);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RMI server started");
    }
}
