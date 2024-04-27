package it.polimi.codexnaturalis.network;

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
    private Map<VirtualView, ValueOfPlayer> listOfPlayers;

    public LobbyThread(String lobbyName) {
        this.lobbyName = lobbyName;
        listOfPlayers = new HashMap<>();
        this.isLobbyStarted = false;
        this.maxPlayer = UtilCostantValue.maxPlayerPerLobby;
        this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public void connectPlayer(VirtualView player) {
        listOfPlayers.put(player, new ValueOfPlayer());
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

    public Collection<ValueOfPlayer> getPlayerInfo() {
        return listOfPlayers.values();
    }
}

class ValueOfPlayer {
    public Color colorChosen;
    public boolean isPlayerReady;
    public ValueOfPlayer() {
        this.colorChosen = null;
        this.isPlayerReady = false;
    }
}
