package it.polimi.codexnaturalis.network.Lobby;

public abstract class LobbyInfo {
    public String lobbyName;
    public Boolean isLobbyStarted;
    public final int maxPlayer;
    public int currentPlayer;

    public LobbyInfo(String lobbyName, Boolean isLobbyStarted, int maxPlayer) {
        this.lobbyName = lobbyName;
        this.isLobbyStarted = isLobbyStarted;
        this.maxPlayer = maxPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public Boolean getLobbyStarted() {
        return isLobbyStarted;
    }
}
