package it.polimi.codexnaturalis.network.Lobby;

import it.polimi.codexnaturalis.network.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;

public class LobbyThread extends Thread {
    private LobbyHandler lobbyInfo;
    final int timoutGameStart;

    // TODO: data Race
    private ArrayList<PlayerInfo> listOfPlayers;

    public LobbyThread(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        lobbyInfo = new LobbyHandler(lobbyName, false, UtilCostantValue.maxPlayerPerLobby);
        this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public boolean connectPlayer(PlayerInfo player) {
        if(lobbyInfo.addPlayer()) {
            listOfPlayers.add(player);
            return true;
        } else {
            return false;
        }
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
        return lobbyInfo.getLobbyName();
    }

    public ArrayList<PlayerInfo> getListOfPlayers() {
        return listOfPlayers;
    }

    public LobbyInfo getLobbyInfo() {
        return lobbyInfo;
    }
}

