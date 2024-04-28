package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.network.Lobby.LobbyThread;
import it.polimi.codexnaturalis.network.rmi.VirtualView;

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

    public static void playerCreation(VirtualView client, String nickname) {
        PlayerInfo playerInfo = new PlayerInfo(client, nickname);
        lobbyLessClients.add(playerInfo);
    }

    public static LobbyThread lobbyCreation(String lobbyname) {
        for(LobbyThread elem : activeLobby) {
            if(elem.getLobbyName() == lobbyname) {
                return null;
            }
        }

        LobbyThread newLobbyThread = new LobbyThread(lobbyname);
        activeLobby.add(newLobbyThread);
        return newLobbyThread;
    }


    public static boolean addPlayerToLobby(VirtualView client, String lobbyName) {
        int supp;

        supp = lobbyLessClients.indexOf(client);
        // if the player got a nickname
        if(supp != -1) {
            for(LobbyThread elem : activeLobby) {
                if(elem.equals(lobbyName)) {
                    elem.connectPlayer(lobbyLessClients.get(supp));
                    return true;
                }
            }

            // in case the lobby doesn't exists
            return false;
        } else {
            return false;
        }
    }

    public static boolean checkNickGlobalNicknameValidity(String checkNickname) {
        // check each player that has yet to join a lobby
        for(PlayerInfo elem : lobbyLessClients) {
            if(elem.getNickname().equals(checkNickname)) {
                return false;
            }
        }

        for(LobbyThread elem : activeLobby) {
            for(PlayerInfo elem1 : elem.getListOfPlayers()) {
                if(elem1.getNickname() == checkNickname) {
                    return false;
                }
            }
        }

        return true;
    }

    public static ArrayList<LobbyThread> getActiveLobby() {
        return activeLobby;
    }
}
