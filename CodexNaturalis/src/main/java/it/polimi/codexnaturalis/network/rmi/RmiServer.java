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
        ArrayList<LobbyInfo> lobbies = null;

        for(LobbyThread elem : serverContainer.getActiveLobby()) {
            lobbies.add(elem.getLobbyInfo());
        }

        json = gson.toJson(lobbies);
        System.out.println("Json sent: "+json);

        return json;
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
