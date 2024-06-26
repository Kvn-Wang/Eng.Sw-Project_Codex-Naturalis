package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.BendMission;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.VirtualGame;
import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.socket.ClientHandler;
import it.polimi.codexnaturalis.network.socket.SocketClient;
import it.polimi.codexnaturalis.network.socket.SocketServer;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.utils.observer.Observer;
import it.polimi.codexnaturalis.view.GUI.GuiClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Proxy;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    private static GameManager gameManager;

    @BeforeAll
    public static void Setup() throws IOException {
        var socketServer = new SocketServer();
        socketServer.start();

        var socketClient = new SocketClient(new GuiClient(), "127.0.0.1");
        var clientHandler = new ClientHandler(new ServerContainer(), socketClient.serverSocket);
        var playerInfo = new ArrayList<PlayerInfo>();
        playerInfo.add(new PlayerInfo(clientHandler, "player1"));
        playerInfo.add(new PlayerInfo(clientHandler, "player2"));

        var virtualGame = new VirtualGame(playerInfo);
        gameManager = (GameManager)virtualGame.getGameController();
    }

    @Test
    void initializeGame() {
        gameManager.initializeGame();
    }

    @Test
    void typeMessage() {
        gameManager.typeMessage("everyone", "player1", "Hello World");
    }

    @Test
    void playStarterCard() {
        // gameManager.playStarterCard("player1", new StarterCard());
        // assertEquals(1, gameManager.playerThatHasPlayedStarterCard);
    }

    @Test
    void playerDraw() {
        // gameManager.playerDraw("player1", 1, ShopType.RESOURCE);
    }

    @Test
    void playerPersonalMissionSelect() {
        gameManager.playerPersonalMissionSelect("player1", new BendMission(
                0,
                1,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                CardCorner.EAST
        ));
    }

    @Test
    void playerPlayCard() {
        gameManager.playerPlayCard("player1", 0, 0, new ResourceCard(
                0,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                1
        ));
    }

    @Test
    void gameEnd() {
        try {
            gameManager.gameEnd();
        } catch (RemoteException e) {
            fail("RemoteException thrown");
        }
    }
}
