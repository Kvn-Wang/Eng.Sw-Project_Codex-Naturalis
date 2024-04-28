package it.polimi.codexnaturalis.network.Lobby;

public class LobbyHandler extends LobbyInfo {

    public LobbyHandler(String lobbyName, Boolean isLobbyStarted, int maxPlayer) {
        super(lobbyName, isLobbyStarted, maxPlayer, 0);
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

    public void setLobbyStarted(Boolean lobbyStarted) {
        isLobbyStarted = lobbyStarted;
    }
}
