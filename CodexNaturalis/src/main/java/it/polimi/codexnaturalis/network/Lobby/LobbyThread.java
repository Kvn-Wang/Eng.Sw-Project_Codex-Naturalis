package it.polimi.codexnaturalis.network.Lobby;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.game.GameManager;
import it.polimi.codexnaturalis.network.NetworkMessage;
import it.polimi.codexnaturalis.network.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LobbyThread extends Thread implements Observer {
    private LobbyInfo lobbyInfo;
    final int timoutGameStart;

    // TODO: data Race
    private ArrayList<PlayerInfo> listOfPlayers;
    private GameController gameController;

    public LobbyThread(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        lobbyInfo = new LobbyInfo(lobbyName, false, UtilCostantValue.maxPlayerPerLobby);
        this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public boolean connectPlayer(PlayerInfo player) throws RemoteException {
        if(lobbyInfo.addPlayer()) {
            notifyClient(player.getNickname() + " has joined the lobby!");

            listOfPlayers.add(player);
            return true;
        } else {
            return false;
        }
    }

    //if after removing a player, currentPlayer == 0, returns False, else True
    public boolean disconnectPlayer(PlayerInfo player) throws RemoteException {
        listOfPlayers.remove(player);

        notifyClient(player.getNickname() + " has left the lobby!");

        if(lobbyInfo.removePlayer()) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlayerReady(PlayerInfo player) throws RemoteException {
        listOfPlayers.get(listOfPlayers.indexOf(player)).setPlayerReady(true);

        notifyClient(player.getNickname() + " is ready!");

        startGame();
    }

    public boolean playerChooseColor() {
        return false;
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
            // TODO: dopo che il player si mette in ready, non può più fare nulla, va bene?
            notifyClient("Waiting for more player before starting");
        }
        //TODO manca il metodo per registrare questo observer agli observable
    }

    private void connectPlayerToGame() throws RemoteException {
        Map<String, ColorType> playerInfoNicknameColor;

        playerInfoNicknameColor = new HashMap<>();
        for(PlayerInfo playerInfo : listOfPlayers) {
            playerInfoNicknameColor.put(playerInfo.getNickname(), playerInfo.getColorChosen());
        }

        gameController = new GameManager(playerInfoNicknameColor);
        for(PlayerInfo playerInfo : listOfPlayers) {
            playerInfo.getClientHandler().connectToGame(gameController);
        }

        notifyClient("Game has Started!");
    }

    private void notifyClient(String message) throws RemoteException {
        for(PlayerInfo elem : listOfPlayers) {
            elem.notifyPlayer(message);
        }
    }

    //TODO
    @Override
    public void run() {
        super.run();
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

    //TODO come gestire i messaggi al client
    @Override
    public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        switch(message.getMessageType().toString()) {
            case "CORRECT_CHOSEN_COLOR":

                break;
            case "COLOR_ALREADY_CHOSEN":

                break;

            case "WRONG_TYPE_SHOP":

                break;

            default:
                throw new PersonalizedException.InvalidRequestTypeOfNetworkMessage(message.getMessageType().toString());
        }
    }
}

