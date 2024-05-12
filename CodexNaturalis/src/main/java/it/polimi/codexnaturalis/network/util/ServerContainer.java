package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerContainer {
    private static ServerContainer instance;
    private static ArrayList<PlayerInfo> lobbyLessClients;
    private static ArrayList<Lobby> activeLobby;

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

    //returns true if the creation is successful (the nickname is globally unique)
    public boolean playerCreation(VirtualView client, String nickname) {
        PlayerInfo playerInfo;

        if(checkNickGlobalNicknameValidity(nickname)) {
            System.out.println("Created player nickname: " + nickname);
            playerInfo = new PlayerInfo(client, nickname);
            lobbyLessClients.add(playerInfo);

            return true;
        } else {
            return false;
        }
    }

    public Lobby lobbyCreation(String lobbyname) {
        for(Lobby elem : activeLobby) {
            if(elem.getLobbyName() == lobbyname) {
                return null;
            }
        }

        Lobby newLobby = new Lobby(lobbyname);
        activeLobby.add(newLobby);
        return newLobby;
    }


    public boolean joinPlayerToLobby(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobbyThread(lobbyName);

        if(lobby.connectPlayer(player)) {
            return true;
        } else {
            return false;
        }
    }

    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobbyThread(lobbyName);

        //remove the player, and if currentPlayer == 0, eliminate the thread
        if(!lobby.disconnectPlayer(player)) {
            activeLobby.remove(lobby);
        }
    }

    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobbyThread(lobbyName);

        lobby.setPlayerReady(player);
    }

    public boolean checkNickGlobalNicknameValidity(String checkNickname) {
        // check each player that has yet to join a lobby
        for(PlayerInfo elem : lobbyLessClients) {
            if(elem.getNickname().equals(checkNickname)) {
                return false;
            }
        }

        for(Lobby elem : activeLobby) {
            for(PlayerInfo elem1 : elem.getListOfPlayers()) {
                if(elem1.getNickname().equals(checkNickname)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNickGlobalLobbyNameValidity(String checkLobbyNickname) {
        for(Lobby elem : activeLobby) {
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

    private Lobby getLobbyThread(String lobbyName) {
        for(Lobby elem : activeLobby) {
            if (elem.getLobbyName().equals(lobbyName)) {
                //remove the player, and if currentPlayer == 0, eliminate the thread
                return elem;
            }
        }

        throw new PersonalizedException.LobbyNotFoundException(lobbyName);
    }

    public ArrayList<Lobby> getActiveLobby() {
        return activeLobby;
    }
}
