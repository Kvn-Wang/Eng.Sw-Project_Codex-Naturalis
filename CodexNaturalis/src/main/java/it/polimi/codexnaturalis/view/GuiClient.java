package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.GUI.Menu;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import javafx.application.Application;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class GuiClient implements TypeOfUI {
    private VirtualNetworkCommand virtualNetworkCommand;

    public GuiClient(){
        javafx.application.Application.launch(Menu.class);
    }

    public VirtualNetworkCommand getvnc(){
        return virtualNetworkCommand;
    }
    @Override
    public void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand) {
        this.virtualNetworkCommand = virtualNetworkCommand;
        Menu.setupMenu(this.virtualNetworkCommand);
    }

    @Override
    public void printSelectionNicknameRequest() {

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
