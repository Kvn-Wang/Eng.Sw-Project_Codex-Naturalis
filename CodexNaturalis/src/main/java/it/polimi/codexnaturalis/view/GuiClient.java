package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.view.GUI.Menu;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;

import java.rmi.RemoteException;

public class GuiClient implements TypeOfUI {
    private VirtualServer virtualNetworkCommand;
    private GameController virtualGame;


    @Override
    public void connectVirtualNetwork(VirtualServer virtualNetworkCommand) {
        this.virtualNetworkCommand = virtualNetworkCommand;
        Menu.setupMenu(this.virtualNetworkCommand);
        javafx.application.Application.launch(Menu.class);
    }

    @Override
    public void printSelectionNicknameRequest() {
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
            Menu.setNickname(positiveOutcome,nickname);
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

    @Override
    public void connectGameController(GameController virtualGame) {
        Menu.startGame();
    }
}
