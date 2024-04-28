package it.polimi.codexnaturalis.network.Lobby;

import com.google.gson.annotations.Expose;

public class LobbyInfo {
    protected String lobbyName;
    protected Boolean isLobbyStarted;
    protected final int maxPlayer;
    protected int currentPlayer;

    public LobbyInfo(String lobbyName, Boolean isLobbyStarted, int maxPlayer, int currentPlayer) {
        this.lobbyName = lobbyName;
        this.isLobbyStarted = isLobbyStarted;
        this.maxPlayer = maxPlayer;
        this.currentPlayer = currentPlayer;
    }

    public boolean addPlayer() {
        if(currentPlayer++ > maxPlayer) {
            return false;
        } else {
            currentPlayer++;
            return true;
        }
    }

    public void removePlayer() {
        currentPlayer--;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public Boolean getIsLobbyStarted() {
        return isLobbyStarted;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
