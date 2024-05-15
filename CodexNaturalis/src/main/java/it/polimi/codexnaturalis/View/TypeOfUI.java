package it.polimi.codexnaturalis.View;

import it.polimi.codexnaturalis.network.lobby.Lobby;

import java.util.ArrayList;

public interface TypeOfUI {
    void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand);
    void printSelectionNicknameRequest();
    void printSelectionNicknameRequestOutcome();
    void printLobby(ArrayList<Lobby> lobbies);
    void printSelectionCreateOrJoinLobbyRequest();
    void printCreationLobbyRequest();
    void printCreationLobbyRequestOutcome();
    void printReadyOrLeaveSelection();
    void printReadyOrLeaveSelectionOutcome();
}
