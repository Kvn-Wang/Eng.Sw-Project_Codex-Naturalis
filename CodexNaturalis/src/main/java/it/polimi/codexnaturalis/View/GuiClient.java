package it.polimi.codexnaturalis.View;

import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.util.ArrayList;

public class GuiClient implements TypeOfUI {
    private VirtualNetworkCommand virtualNetworkCommand;

    @Override
    public void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand) {
        this.virtualNetworkCommand = virtualNetworkCommand;
    }

    @Override
    public void printSelectionNicknameRequest() {

    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {

    }

    @Override
    public void printLobby(ArrayList<LobbyInfo> lobbies) {

    }

    @Override
    public void printSelectionCreateOrJoinLobbyRequest() {

    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {

    }

    @Override
    public void printReadyOrLeaveSelection() {

    }

    @Override
    public void printReadyOrLeaveSelectionOutcome(boolean isReady) {

    }
}
