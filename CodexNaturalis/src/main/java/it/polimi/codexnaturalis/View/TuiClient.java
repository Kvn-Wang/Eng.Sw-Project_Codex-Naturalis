package it.polimi.codexnaturalis.View;

import it.polimi.codexnaturalis.network.lobby.Lobby;

import java.util.ArrayList;

public class TuiClient implements TypeOfUI {
    private VirtualNetworkCommand virtualNetworkCommand;

    @Override
    public void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand) {
        this.virtualNetworkCommand = virtualNetworkCommand;
    }

    @Override
    public void printSelectionNicknameRequest() {
        System.out.println("Inserisci il tuo nickname:");

    }

    @Override
    public void printSelectionNicknameRequestOutcome() {

    }

    @Override
    public void printLobby(ArrayList<Lobby> lobbies) {

    }

    @Override
    public void printSelectionCreateOrJoinLobbyRequest() {

    }

    @Override
    public void printCreationLobbyRequest() {

    }

    @Override
    public void printCreationLobbyRequestOutcome() {

    }

    @Override
    public void printReadyOrLeaveSelection() {

    }

    @Override
    public void printReadyOrLeaveSelectionOutcome() {

    }
}
