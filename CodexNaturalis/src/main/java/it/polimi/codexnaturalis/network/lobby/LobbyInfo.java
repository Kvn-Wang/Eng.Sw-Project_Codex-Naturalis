package it.polimi.codexnaturalis.network.lobby;


import java.io.Serializable;

public class LobbyInfo implements Serializable {
    protected String lobbyName;
    protected Boolean isLobbyStarted;
    protected final int maxPlayer;
    protected int currentPlayer;
    private transient final Object lock = new Object();

    public LobbyInfo(String lobbyName, Boolean isLobbyStarted, int maxPlayer) {
        this.lobbyName = lobbyName;
        this.isLobbyStarted = isLobbyStarted;
        this.maxPlayer = maxPlayer;
        this.currentPlayer = 0;
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

    public boolean addPlayer() {
        synchronized (lock) {
            if(currentPlayer >= maxPlayer || isLobbyStarted) {
                return false;
            } else {
                currentPlayer++;
                return true;
            }
        }
    }

    //if after removing a player, currentPlayer == 0, returns False, else True
    public boolean removePlayer() {
        synchronized (lock) {
            currentPlayer--;
            if(currentPlayer == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void setLobbyStarted(Boolean lobbyStarted) {
        isLobbyStarted = lobbyStarted;
    }
}
