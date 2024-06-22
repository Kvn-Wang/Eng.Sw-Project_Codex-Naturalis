package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.VirtualGame;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;
    @BeforeEach
    public void Setup(){
        var playerInfo = new ArrayList<PlayerInfo>();
        // TODO: correctly instance players info
        // playerInfo.add(new PlayerInfo(, "player1"));
        // playerInfo.add(new PlayerInfo("player2"));

        try {
            var virtualGame = new VirtualGame(playerInfo);
            gameManager = (GameManager)virtualGame.getGameController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @BeforeEach
    void initializeGame() {
        gameManager.initializeGame();

        //for(Map.Entry<String, ColorType> entry: playerInfo.entrySet())
        //    gameManager.playerPlayCard(entry.getKey(), 1, 3, 0, true);

        //for(Map.Entry<String, ColorType> entry: playerInfo.entrySet())
        //    gameManager.playerPersonalMissionSelect(entry.getKey(), Mission);
    }

    @Test
    void disconnectPlayer() {
        gameManager.disconnectPlayer("player1");
        Player player = gameManager.nickToPlayer("player1");

        assertFalse(player.getStatus(), "Player should be marked as disconnected.");
    }

    @Test
    void reconnectPlayer() {
        gameManager.disconnectPlayer("player1");
        gameManager.reconnectPlayer("player1");
        Player player = gameManager.nickToPlayer("player1");

        assertTrue(player.getStatus(), "Player should be marked as reconnected.");
    }

    @Test
    void typeMessage() {
        gameManager.typeMessage("everyone", "player1", "Hello World");
    }
}