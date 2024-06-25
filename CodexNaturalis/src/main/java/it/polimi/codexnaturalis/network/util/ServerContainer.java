package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerContainer {
    private static ServerContainer instance;
    private static ArrayList<PlayerInfo> activeClients;
    private static ArrayList<Lobby> activeLobby;

    public ServerContainer() {
        activeClients = new ArrayList<>();
        this.activeLobby = new ArrayList<>();
    }

    public static ServerContainer getInstance() {
        if (instance == null) {
            // Initialize the singleton instance if it's not already initialized
            instance = new ServerContainer();
        }
        return instance;
    }

    //returns true if the creation is successful (the nickname is globally unique) and add it to the globally unique arraylist
    public boolean playerCreation(VirtualView client, String nickname) {
        PlayerInfo playerInfo;

        if(checkNickGlobalNicknameValidity(nickname)) {
            System.out.println("Created player nickname: " + nickname);
            playerInfo = new PlayerInfo(client, nickname);
            activeClients.add(playerInfo);

            return true;
        } else {
            return false;
        }
    }

    public boolean lobbyCreation(String lobbyName) {
        if(checkNickGlobalLobbyNameValidity(lobbyName)) {
            for(Lobby elem : activeLobby) {
                if(elem.getLobbyName().equals(lobbyName)) {
                    return false;
                }
            }

            Lobby newLobby = new Lobby(lobbyName);
            activeLobby.add(newLobby);
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @param playerNickname
     * @param lobbyName
     * @return an arrayList != null if the join is successful, contains all the player that has joined the lobby
     *          including the caller
     * @throws RemoteException
     */
    public ArrayList<PlayerInfo> joinPlayerToLobby(String playerNickname, String lobbyName) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobby(lobbyName);

        // if lobby has not been found
        if(lobby == null) {
            return null;
        } else {
            if(lobby.connectPlayer(player)) {
                return lobby.getListOfPlayers();
            } else {
                return null;
            }
        }
    }

    public void leaveLobby(String playerNickname) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobbyByPlayer(playerNickname);

        //remove the player, and if currentPlayer == 0, eliminate the thread
        if(!lobby.disconnectPlayer(player)) {
            activeLobby.remove(lobby);
        }

        System.out.println(playerNickname + " has left the lobby");
    }

    public void setPlayerReady(String playerNickname) throws RemoteException {
        PlayerInfo player;
        Lobby lobby;

        player = stringToPlayer(playerNickname);
        lobby = getLobbyByPlayer(playerNickname);

        lobby.setPlayerReady(player);
    }

    public boolean checkNickGlobalNicknameValidity(String checkNickname) {
        // check each player that has yet to join a lobby
        for(PlayerInfo elem : activeClients) {
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

        for(PlayerInfo elem : activeClients) {
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

    private Lobby getLobby(String lobbyName) {
        for(Lobby elem : activeLobby) {
            if (elem.getLobbyName().equals(lobbyName)) {
                //remove the player, and if currentPlayer == 0, eliminate the thread
                return elem;
            }
        }

        return null;
    }

    private Lobby getLobbyByPlayer(String playerName) {
        for(Lobby elem : activeLobby) {
            for(PlayerInfo player : elem.getListOfPlayers()) {
                if(player.getNickname().equals(playerName)) {
                    return elem;
                }
            }
        }

        throw new PersonalizedException.LobbyNotFoundException("Lobby non trovata per player: " + playerName);
    }

    public void setPlayerColor(String nickname, ColorType colorType) {
        PlayerInfo player = stringToPlayer(nickname);
        Lobby playerLobby = getLobbyByPlayer(nickname);

        playerLobby.setPlayerColor(player, colorType);
    }

    public ArrayList<Lobby> getActiveLobby() {
        return activeLobby;
    }
}
