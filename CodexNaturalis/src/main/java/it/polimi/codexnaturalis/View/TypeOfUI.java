package it.polimi.codexnaturalis.View;

import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.util.ArrayList;

public interface TypeOfUI {
    void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand);
    void printSelectionNicknameRequest();
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void printLobby(ArrayList<LobbyInfo> lobbies);
    void printSelectionCreateOrJoinLobbyRequest();
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void printReadyOrLeaveSelection();
    void printReadyOrLeaveSelectionOutcome(boolean isReady);
}
