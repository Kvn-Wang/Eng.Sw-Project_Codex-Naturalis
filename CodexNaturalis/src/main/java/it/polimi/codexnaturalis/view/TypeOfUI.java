package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TypeOfUI {
    // collegare la UI alla network
    void connectVirtualNetwork(VirtualServer virtualNetworkCommand);
    void connectGameController(GameController virtualGame);
    void printSelectionNicknameRequest() throws RemoteException;
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void printSelectionCreateOrJoinLobbyRequest() throws RemoteException;
    void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException;
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void printReadyOrLeaveSelection() throws RemoteException;
    void printReadyOrLeaveSelectionOutcome(boolean isReady);
}
