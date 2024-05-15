package it.polimi.codexnaturalis.View;

public interface VirtualNetworkCommand {
    void selectNickname(String nickname);
    void joinLobby(String selection);
    void createLobby(String lobbyName);
    void setReady();
    void leaveLobby();
}
