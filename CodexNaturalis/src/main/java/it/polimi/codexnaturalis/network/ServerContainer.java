package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.Lobby.LobbyThread;
import it.polimi.codexnaturalis.network.rmi.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerContainer {
    private static ServerContainer instance;
    private static ArrayList<PlayerInfo> lobbyLessClients;
    private static ArrayList<LobbyThread> activeLobby;

    private ServerContainer() {
        lobbyLessClients = new ArrayList<>();
        this.activeLobby = new ArrayList<>();
    }

    public static ServerContainer getInstance() {
        if (instance == null) {
            // Initialize the singleton instance if it's not already initialized
            instance = new ServerContainer();
        }
        return instance;
    }

    public void playerCreation(VirtualView client, String nickname) {
        PlayerInfo playerInfo = new PlayerInfo(client, nickname);
        lobbyLessClients.add(playerInfo);
    }

    public LobbyThread lobbyCreation(String lobbyname) {
        for(LobbyThread elem : activeLobby) {
            if(elem.getLobbyName() == lobbyname) {
                return null;
            }
        }

        LobbyThread newLobbyThread = new LobbyThread(lobbyname);
        activeLobby.add(newLobbyThread);
        return newLobbyThread;
    }


    public boolean joinPlayerToLobby(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;

        // cerca il playerInfo sapendo il suo nickname
        player = null;
        for(PlayerInfo elem : lobbyLessClients) {
            if(elem.getNickname().equals(playerNickname)) {
                player = elem;
            }
        }

        if(player == null) {
            return false;
        }

        for(LobbyThread elem : activeLobby) {
            if (elem.getLobbyName().equals(lobbyName)) {
                if(elem.connectPlayer(player)) {
                    // se c'è qualche player nella lobby (controllo utile nel caso di lobby appena creata)
                    if(elem.getListOfPlayers() != null) {
                        notifyClient(elem.getListOfPlayers(), playerNickname + " has joined the lobby!");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void notifyClient(ArrayList<PlayerInfo> notifiedPlayer, String message) throws RemoteException {
        for(PlayerInfo elem : notifiedPlayer) {
            elem.notifyPlayer(message);
        }
    }

    public boolean checkNickGlobalNicknameValidity(String checkNickname) {
        // check each player that has yet to join a lobby
        for(PlayerInfo elem : lobbyLessClients) {
            if(elem.getNickname().equals(checkNickname)) {
                return false;
            }
        }

        for(LobbyThread elem : activeLobby) {
            for(PlayerInfo elem1 : elem.getListOfPlayers()) {
                if(elem1.getNickname().equals(checkNickname)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNickGlobalLobbyNameValidity(String checkLobbyNickname) {
        for(LobbyThread elem : activeLobby) {
            if(elem.getLobbyName().equals(checkLobbyNickname)) {
                return false;
            }

        }
        return true;
    }

    public ArrayList<LobbyThread> getActiveLobby() {
        return activeLobby;
    }
}
