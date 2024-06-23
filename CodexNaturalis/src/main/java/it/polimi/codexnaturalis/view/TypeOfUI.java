package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TypeOfUI {
    void connectGameController(GameController virtualGame);
    void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname);
    void giveLobbies(ArrayList<LobbyInfo> lobbies);
    void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException;
    void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName);
    void lobbyActionOutcome(boolean isReady);
    void notifyLobbyStatus(String otherPlayerNickname, String status);

    /*---- SETUP ----*/
    void giveStarterCard(StarterCard starterCard);
    void givePersonalMission(Mission choice1, Mission choice2);
    void notifyIsYourTurnInitPhase(boolean isYourTurn);

    /*---- PLAY ----*/
    void startGamePhase();
    // i valori aggiornati dei score sono nel container
    void outcomePlayCard(boolean isValidPlacement);

    /**
     * it happens whe you try to play a card in a drawing phase for example
     */
    void printErrorCommandSentGameState(GameState currentGameState);

    void printIsYourTurn();
    void printIsYourFinalTurn();
    void printWinners(ArrayList<String> winnersNickname);
}
