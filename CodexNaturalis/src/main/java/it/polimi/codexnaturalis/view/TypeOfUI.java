package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainerController;

import java.rmi.RemoteException;

public interface TypeOfUI {
    // collegare la UI alla network
    void connectVirtualNetwork(VirtualServer virtualNetworkCommand, ClientContainerController clientContainerController);
    void connectGameController(GameController virtualGame, ClientContainerController clientContainerController);
    void printSelectionNicknameRequest() throws RemoteException;
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void printSelectionCreateOrJoinLobbyRequest() throws RemoteException;
    void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException;
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void printReadyOrLeaveSelection() throws RemoteException;
    void printReadyOrLeaveSelectionOutcome(boolean isReady);
    void notifyLobbyStatus(String otherPlayerNickname, String status);
    // la starter card sarà in posizione 0 nella hand
    void printStarterCardReq(Card starterCard);
    void printHand(Hand hand);
    void printCommonMission(Mission mission1, Mission mission2);
    void printPersonalMissionReq(Mission choice1, Mission choice2);
    void notifyIsYourTurn(boolean isYourTurn);
}
