package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GeneralClientView {
    String setNicknameProcedure(String personalID);
    boolean printNicknameProcedureOutcome(boolean nicknameTaken);
    ArrayList<LobbyInfo> getLobbies() throws RemoteException;
    String joinOrCreateSelection() throws RemoteException;
    boolean waitingInLobbyResult();
}
