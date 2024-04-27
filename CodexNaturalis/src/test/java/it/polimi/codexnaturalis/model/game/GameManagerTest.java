package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;
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
    void initializeGame() {
        gameManager.initializeGame();
        for(Map.Entry<String, ColorType> entry: playerInfo.entrySet()) {
            try {
                gameManager.playerPlayStarterCard(entry.getKey(), false);
            } catch (PersonalizedException.InvalidPlacementException |
                     PersonalizedException.InvalidPlaceCardRequirementException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void disconnectPlayer() {
    }

    @Test
    void reconnectPlayer() {
    }

    @Test
    void playerDraw() {/*
        gameManager.playerDraw("Maria", 1, "RESOURCE");
        try {
            gameManager.playerPlayCard("Maria", 1, 1, 0, false);
        } catch (PersonalizedException.InvalidPlacementException |
                 PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw new RuntimeException(e);
        }
    */
    }

    @Test
    void playerPersonalMissionSelect() {
    }

    @Test
    void playerPlayCard() {
    }

    @Test
    void playerPlayStarterCard() {
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