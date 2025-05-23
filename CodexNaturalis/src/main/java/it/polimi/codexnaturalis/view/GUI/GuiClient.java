package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.view.TypeOfUI;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GuiClient implements TypeOfUI {
    private VirtualServer virtualNetworkCommand;
    private GameController virtualGame;

    public void initializeClient(VirtualServer virtualServer, ClientContainer clientContainer) throws RemoteException {
        this.virtualNetworkCommand = virtualServer;
        GuiGame.setupMenu(this.virtualNetworkCommand, clientContainer);
        javafx.application.Application.launch(GuiGame.class);
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
            GuiGame.setNickname(positiveOutcome,nickname);
    }

    @Override
    public void giveLobbies(ArrayList<LobbyInfo> lobbies) {
        GuiGame.lobbyListRefresh(lobbies);
    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException {

    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {

    }

    @Override
    public void lobbyActionOutcome(boolean isReady) {

    }

    @Override
    public void printChooseColorOutcome(boolean isSuccessful) {

    }

    @Override
    public void notifyLobbyStatusColor(String otherPlayerNickname, ColorType color) {
        GuiGame.colorPicked(otherPlayerNickname);
    }

    @Override
    public void notifyLobbyStatus(String otherPlayerNickname, String status) {
        if(status.equals("JOIN")) {
            System.out.println(otherPlayerNickname + " has joined the lobby!");
        } else if(status.equals("LEFT")){
            System.out.println(otherPlayerNickname + " has left the lobby");
            GuiGame.playerLeftGame(otherPlayerNickname);
        } else if(status.equals("READY")) {
            System.out.println(otherPlayerNickname + " is ready");
        } else if(status.equals("WAIT")) {
            System.out.println("Wait for more players");
        } else {
            System.err.println("Has been called an invalid command: "+status);
        }
    }

    @Override
    public void printLobby() {
        GuiGame.joinLobby();
    }

    @Override
    public void giveStarterCard(StarterCard starterCard) {
        GuiGame.addStarter(starterCard);
    }

    @Override
    public void givePersonalMission(Mission choice1, Mission choice2) {
        System.out.println("Mission Selection");
        GuiGame.missionSelection(choice1,choice2);
    }

    @Override
    public void notifyIsYourTurnInitPhase(boolean isYourTurn) {
        GuiGame.turnNotify(isYourTurn, false);
    }

    @Override
    public void outcomePlayCard(boolean isValidPlacement) {
        System.out.println("played card outcome: " + isValidPlacement);
        GuiGame.validPlacement(isValidPlacement);
    }

    @Override
    public void printErrorCommandSentGameState(GameState currentGameState) {
        System.out.println("it's not the play_phase");
        switch (currentGameState){
            case PLAY_PHASE -> GuiGame.drawDuringPlayPhase();
            case DRAW_PHASE -> GuiGame.validPlacement(false);
        }
    }

    @Override
    public void printIsYourTurn() {
        GuiGame.turnNotify(true, false);
    }

    @Override
    public void printIsNotYourTurn() {
        System.out.println("you can't place cards during another player's turn");
        GuiGame.validPlacement(false);
        GuiGame.turnNotify(false, false);
    }

    @Override
    public void printIsYourFinalTurn() {
        GuiGame.turnNotify(true, true);
    }

    @Override
    public void printWinners(ArrayList<String> winnersNickname) {
        GuiGame.endGame(winnersNickname);
    }

    @Override
    public void updateHand() {

    }

    @Override
    public void updatePlayerScoreBoard() {
        GuiGame.updateScore();
    }

    @Override
    public void printPlayerMsg(String sender, String msg) {
        GuiGame.updateChat(sender, msg);
    }

    @Override
    public void connectGameController(GameController virtualGame) {
        GuiGame.startGame(virtualGame);
    }

}
