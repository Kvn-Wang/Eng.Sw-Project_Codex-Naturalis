package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.GUI.Menu;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import javafx.application.Application;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class GuiClient implements TypeOfUI {
    private VirtualServer virtualNetworkCommand;
    private GameController virtualGame;


    @Override
    public void connectVirtualNetwork(VirtualServer virtualNetworkCommand) {
        this.virtualNetworkCommand = virtualNetworkCommand;
        Menu.setupMenu(this.virtualNetworkCommand);
    }

    @Override
    public void connectGameController(GameController virtualGame) {

    }

    @Override
    public void printSelectionNicknameRequest() {
        javafx.application.Application.launch(Menu.class);
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
            Menu.setupNickname(positiveOutcome,nickname);
    }

    @Override
    public void printSelectionCreateOrJoinLobbyRequest() {

    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException {

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
