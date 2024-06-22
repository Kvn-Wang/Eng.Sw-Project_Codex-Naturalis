package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.view.TypeOfUI;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GuiClient implements TypeOfUI {
    private VirtualServer virtualNetworkCommand;
    private GameController virtualGame;

    public void initializeClient(VirtualServer virtualServer, ClientContainer clientContainer) throws RemoteException {
        initializationPhase1(virtualServer, clientContainer);
    }

    //chiamata che garantisce il setup del nickname univoco
    protected void initializationPhase1(VirtualServer virtualServer, ClientContainer clientContainer)  {
        // aggiungo alla UI il potere di comunicare con l'esterno
        connectVirtualNetwork(virtualServer, clientContainer);
    }

    @Override
    public void connectVirtualNetwork(VirtualServer virtualNetworkCommand, ClientContainer clientContainer) {
        this.virtualNetworkCommand = virtualNetworkCommand;
        GuiGame.setupMenu(this.virtualNetworkCommand, clientContainer);
        javafx.application.Application.launch(GuiGame.class);
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
            GuiGame.setNickname(positiveOutcome,nickname);
    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException {
    }

    @Override
    public void printLobbyStatus(ArrayList<PlayerInfo> lobbyPlayers) throws RemoteException {

    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {

    }

    @Override
    public void lobbyActionOutcome(boolean isReady) {

    }

    @Override
    public void notifyLobbyStatus(String otherPlayerNickname, String status) {
        if(status.equals("JOIN")) {
            System.out.println(otherPlayerNickname + " has joined the lobby!");
        } else if(status.equals("LEFT")){
            System.out.println(otherPlayerNickname + " has left the lobby");
        } else if(status.equals("READY")) {
            System.out.println(otherPlayerNickname + " is ready");
        } else if(status.equals("WAIT")) {
            System.out.println("Wait for more players");
        } else {
            System.err.println("Has been called an invalid command: "+status);
        }
    }

    @Override
    public void giveStarterCard(StarterCard starterCard) {
        GuiGame.addStarter(starterCard);
    }

    @Override
    public void printHand(Hand hand) {
        GuiGame.UpdateHand(hand);
    }

    @Override
    public void giveCommonMission(Mission mission1, Mission mission2) {
        System.out.println("Common Mission");
        GuiGame.commonMissionSetup(mission1,mission2);
    }

    @Override
    public void givePersonalMission(Mission choice1, Mission choice2) {
        System.out.println("Mission Selection");
        GuiGame.missionSelection(choice1,choice2);
    }

    @Override
    public void notifyIsYourTurnInitPhase(boolean isYourTurn) {
        GuiGame.turnNotify();
    }

    @Override
    public void startGamePhase() {

    }

    @Override
    public void outcomePlayCard(boolean isValidPlacement) {

    }

    @Override
    public void printErrorCommandSentGameState(GameState currentGameState) {

    }

    @Override
    public void printIsYourTurn() {

    }

    @Override
    public void printIsYourFinalTurn() {

    }

    @Override
    public void printWinners(ArrayList<String> winnersNickname) {

    }

    @Override
    public void connectGameController(GameController virtualGame) {
        GuiGame.startGame(virtualGame);
    }

}
