package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TypeOfUI {
    // collegare la UI alla network
    void connectVirtualNetwork(VirtualServer virtualNetworkCommand, ClientContainer clientContainer);
    void connectGameController(GameController virtualGame);
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException;
    void printLobbyStatus(ArrayList<PlayerInfo> lobbyPlayers) throws RemoteException;
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void lobbyActionOutcome(boolean isReady);

    void notifyLobbyStatus(String otherPlayerNickname, String status);
    void printHand(Hand hand);

    /*---- SETUP ----*/
    void giveStarterCard(StarterCard starterCard);
    void giveCommonMission(Mission mission1, Mission mission2);
    void givePersonalMission(Mission choice1, Mission choice2);
    void notifyIsYourTurnInitPhase(boolean isYourTurn);

    /*---- PLAY ----*/
    void startGamePhase();
    // i valori aggiornati dei score sono nel container
    void outcomePlayCard(boolean isValidPlacement);

    /**
     * it happens whe you try to play a card in a drawing phase for example
     * @param gameState
     */
    void printErrorCommandSentGameState(GameState currentGameState);

    void printIsYourTurn();
}
