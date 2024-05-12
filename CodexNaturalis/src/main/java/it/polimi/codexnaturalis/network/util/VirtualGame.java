package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.game.GameManager;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VirtualGame implements GameController, Observer {
    ArrayList<PlayerInfo> players;

    //variable used to decide who can play
    int currentPlayerIndex;
    int startingPlayerIndex;
    GameController gameController;

    public VirtualGame(ArrayList<PlayerInfo> players) {
        this.players = players;

        // random order of play
        Collections.shuffle(this.players);
        startingPlayerIndex = 0;
        currentPlayerIndex = startingPlayerIndex;

        // initialize the real game controller
        Map<String, ColorType> playerInfoNicknameColor;
        playerInfoNicknameColor = new HashMap<>();
        for(PlayerInfo playerInfo : this.players) {
            playerInfoNicknameColor.put(playerInfo.getNickname(), playerInfo.getColorChosen());
        }
        gameController = new GameManager(playerInfoNicknameColor);
    }

    private PlayerInfo getNextPlayer() {
        PlayerInfo nextPlayer = players.get(currentPlayerIndex);
        // Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return nextPlayer;
    }

    @Override
    public void disconnectPlayer(String nickname) {

    }

    @Override
    public void reconnectPlayer(String nickname) {

    }

    @Override
    public void playerDraw(String nickname, int numCard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {

    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {

    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {

    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) {

    }

    @Override
    public void switchPlayer(String reqPlayer, String target) {

    }

    //TODO come gestire i messaggi al client
    @Override
    public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        switch(message.getMessageType()) {
            case CORRECT_CHOSEN_COLOR:

                break;
            case COLOR_ALREADY_CHOSEN:

                break;

            case WRONG_TYPE_SHOP:

                break;

            default:
                // come mandare il messaggio ad un certo player
                //players.get(0).getClientHandler().showValue("bohh");
                throw new PersonalizedException.InvalidRequestTypeOfNetworkMessage(message.getMessageType().toString());
        }
    }
}
