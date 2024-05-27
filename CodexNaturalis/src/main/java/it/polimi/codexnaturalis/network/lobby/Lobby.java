package it.polimi.codexnaturalis.network.lobby;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.util.MessageType;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.VirtualGame;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Lobby {
    private LobbyInfo lobbyInfo;
    //final int timoutGameStart;

    // TODO: data Race
    private ArrayList<PlayerInfo> listOfPlayers;
    private GameController gameController;

    public Lobby(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        lobbyInfo = new LobbyInfo(lobbyName, false, UtilCostantValue.maxPlayerPerLobby);
        //this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public boolean connectPlayer(PlayerInfo player) throws RemoteException {
        if(lobbyInfo.addPlayer()) {
            broadCastNotify(player.getNickname(), "JOIN");

            listOfPlayers.add(player);
            return true;
        } else {
            return false;
        }
    }

    //if after removing a player, currentPlayer == 0, returns False, else True
    public boolean disconnectPlayer(PlayerInfo player) throws RemoteException {
        listOfPlayers.remove(player);

        broadCastNotify(player.getNickname(), "LEFT");

        if(lobbyInfo.removePlayer()) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlayerReady(PlayerInfo player) throws RemoteException {
        listOfPlayers.get(listOfPlayers.indexOf(player)).setPlayerReady(true);

        broadCastNotify(player.getNickname(), "READY");

        //ogni volta che qualcuno si mette in ready, prova ad avviare la partita
        startGame();
    }

    private void startGame() throws RemoteException {
        if(listOfPlayers.size() >= UtilCostantValue.minPlayerPerLobby) {
            boolean allPlayerReady;

            allPlayerReady = true;
            for(PlayerInfo playerInfo : listOfPlayers) {
                if(!playerInfo.isPlayerReady()) {
                    allPlayerReady = false;
                }
            }

            if(allPlayerReady) {
                connectPlayerToGame();
            }
        } else {
            broadCastNotify(listOfPlayers.get(0).getNickname(), "WAIT");
        }
    }

    // crea il game e passa l'interfaccia ad ogni player (RMI)
    private void connectPlayerToGame() throws RemoteException {
        lobbyInfo.isLobbyStarted = true;

        gameController = new VirtualGame(listOfPlayers);
        for(PlayerInfo playerInfo : listOfPlayers) {
            // passo ad ogni player il virtualGameController e la lista degli altri player
            playerInfo.getClientHandler().connectToGame(gameController, copyArrayListExceptOne(listOfPlayers, playerInfo));
        }
    }

    private ArrayList<PlayerInfo> copyArrayListExceptOne(ArrayList<PlayerInfo> players, PlayerInfo exception) {
        ArrayList<PlayerInfo> playerList = new ArrayList<>();

        for(PlayerInfo elem : players) {
            if(!(elem == exception)) {
                playerList.add(elem);
            }
        }

        return playerList;
    }

    private void broadCastNotify(String player, String command) throws RemoteException {
        for(PlayerInfo elem : listOfPlayers) {
            elem.notifyPlayer(new NetworkMessage(player, MessageType.COM_LOBBY, command));
        }
    }

    public String getLobbyName() {
        return lobbyInfo.getLobbyName();
    }

    public ArrayList<PlayerInfo> getListOfPlayers() {
        return listOfPlayers;
    }

    public LobbyInfo getLobbyInfo() {
        return lobbyInfo;
    }
}

