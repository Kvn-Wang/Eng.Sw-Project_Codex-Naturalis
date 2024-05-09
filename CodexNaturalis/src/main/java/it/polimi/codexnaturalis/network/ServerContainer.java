package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.Lobby.LobbyThread;
import it.polimi.codexnaturalis.network.rmi.VirtualView;
import it.polimi.codexnaturalis.utils.PersonalizedException;

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
        LobbyThread lobbyThread;

        player = stringToPlayer(playerNickname);
        lobbyThread = getLobbyThread(lobbyName);

        if(lobbyThread.connectPlayer(player)) {
            return true;
        } else {
            return false;
        }
    }

    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        LobbyThread lobbyThread;

        player = stringToPlayer(playerNickname);
        lobbyThread = getLobbyThread(lobbyName);

        //remove the player, and if currentPlayer == 0, eliminate the thread
        if(!lobbyThread.disconnectPlayer(player)) {
            activeLobby.remove(lobbyThread);
        }
    }

    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        LobbyThread lobbyThread;

        player = stringToPlayer(playerNickname);
        lobbyThread = getLobbyThread(lobbyName);

        lobbyThread.setPlayerReady(player);
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

    private PlayerInfo stringToPlayer(String nickname) {
        PlayerInfo player = null;

        for(PlayerInfo elem : lobbyLessClients) {
            if(elem.getNickname().equals(nickname)) {
                player = elem;
            }
        }

        if(player == null) {
            throw new PersonalizedException.PlayerNotFoundException(nickname);
        } else {
            return player;
        }
    }

    private LobbyThread getLobbyThread(String lobbyName) {
        for(LobbyThread elem : activeLobby) {
            if (elem.getLobbyName().equals(lobbyName)) {
                //remove the player, and if currentPlayer == 0, eliminate the thread
                return elem;
            }
        }

        throw new PersonalizedException.LobbyNotFoundException(lobbyName);
    }

    public ArrayList<LobbyThread> getActiveLobby() {
        return activeLobby;
    }
}
