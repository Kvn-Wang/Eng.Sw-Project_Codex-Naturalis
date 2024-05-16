package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TypeOfUI {
    void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand);
    void printSelectionNicknameRequest() throws RemoteException;
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void printSelectionCreateOrJoinLobbyRequest() throws RemoteException;
    void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException;
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void printReadyOrLeaveSelection() throws RemoteException;
    void printReadyOrLeaveSelectionOutcome(boolean isReady);
}
