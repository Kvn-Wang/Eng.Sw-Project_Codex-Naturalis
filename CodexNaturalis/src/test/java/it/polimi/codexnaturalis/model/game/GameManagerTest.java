package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;
    private Map<String, ColorType> playerInfo = new HashMap<>();
    @BeforeEach
    public void Setup(){
            playerInfo.put("Maria", ColorType.RED);
            playerInfo.put("Kevin", ColorType.GREEN);
            playerInfo.put("Pietro", ColorType.BLUE);
            playerInfo.put("Valerio", ColorType.YELLOW);
        gameManager = new GameManager(playerInfo);
    }

    @Test
    @BeforeEach
    void initializeGame() {
        //gameManager.initializeGame();
        for(Map.Entry<String, ColorType> entry: playerInfo.entrySet()) {
            try {
                gameManager.playerPlayCard(entry.getKey(), 1, 3, 0, true);
            } catch (PersonalizedException.InvalidPlacementException |
                     PersonalizedException.InvalidPlaceCardRequirementException e) {
                throw new RuntimeException(e);
            }
        }
        for(Map.Entry<String, ColorType> entry: playerInfo.entrySet()) {
                gameManager.playerPersonalMissionSelect(entry.getKey(), 1);
        }
    }



    @Test
    void disconnectPlayer() {
        gameManager.disconnectPlayer("valerio");
        playerInfo.get("valerio");
    }

    @Test
    void reconnectPlayer() {
    }

    @Test
    void playerPlayCard() {
        String playingPlayer=gameManager.getPlayerTurn().getNickname();
        try {
            gameManager.playerPlayCard(playingPlayer, (UtilCostantValue.lunghezzaMaxMappa/2) + 1,UtilCostantValue.lunghezzaMaxMappa/2,1,false);
        } catch (PersonalizedException.InvalidPlacementException |
                 PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void playerDraw() {
        String playingPlayer=gameManager.getPlayerTurn().getNickname();
        try {
            gameManager.playerPlayCard(playingPlayer, (UtilCostantValue.lunghezzaMaxMappa/2) + 1,UtilCostantValue.lunghezzaMaxMappa/2,1,false);
        } catch (PersonalizedException.InvalidPlacementException |
                 PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
        try {
            gameManager.playerDraw(playingPlayer,1, "RESOURCE");
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void nextTurn(){
        for(int i=0; i<12; i++) {
            String playingPlayer = gameManager.getPlayerTurn().getNickname();
            try {
                gameManager.playerPlayCard(playingPlayer, (UtilCostantValue.lunghezzaMaxMappa / 2) - 1-(i/4), UtilCostantValue.lunghezzaMaxMappa / 2, 1, true);
            } catch (PersonalizedException.InvalidPlacementException |
                     PersonalizedException.InvalidPlaceCardRequirementException e) {
                throw new RuntimeException(e);
            }
            try {
                gameManager.playerDraw(playingPlayer, 1, "RESOURCE");
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Test
    void typeMessage() {
    }

    @Test
    void switchPlayer() {
    }

    @Test
    void endGame() {
    }
}