package it.polimi.codexnaturalis.network.Lobby;

import it.polimi.codexnaturalis.network.PlayerInfo;
import it.polimi.codexnaturalis.network.rmi.VirtualView;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LobbyThread extends Thread {
    private String lobbyName;
    private Boolean isLobbyStarted;
    final int maxPlayer;
    final int timoutGameStart;

    // TODO: data Race
    private ArrayList<PlayerInfo> listOfPlayers;

    public LobbyThread(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        this.lobbyName = lobbyName;
        this.isLobbyStarted = false;
        this.maxPlayer = UtilCostantValue.maxPlayerPerLobby;
        this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public void connectPlayer(PlayerInfo player) {
        listOfPlayers.add(player);
    }

    public void disconnectPlayer(PlayerInfo player) {
        listOfPlayers.remove(player);
    }

    public boolean playerChooseColor() {
        return false;
    }

    private void startGame() {
        if(listOfPlayers.size() >= UtilCostantValue.minPlayerPerLobby) {

        }
    }

    @Override
    public void run() {
        super.run();
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public ArrayList<PlayerInfo> getListOfPlayers() {
        return listOfPlayers;
    }
}

