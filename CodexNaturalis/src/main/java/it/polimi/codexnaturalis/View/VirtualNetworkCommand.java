package it.polimi.codexnaturalis.View;

public interface VirtualNetworkCommand {
    void selectNickname(String nickname);
    void selectCreateOrJoinLobby(String selection);
    void createLobby(String lobbyName);
    void setReady();
    void leaveLobby();
}
