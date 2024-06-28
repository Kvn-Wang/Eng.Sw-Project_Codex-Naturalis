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
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observer;
import it.polimi.codexnaturalis.view.GUI.GuiClient;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
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
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, ResourceType.UNASSIGNABLE, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);

    @BeforeAll
    public static void Setup() throws IOException {
        var socketServer = new SocketServer();
        socketServer.start();

        SocketClient socketClient = new SocketClient(new GuiClient(), "127.0.0.1");
        ClientHandler clientHandler = new ClientHandler(new ServerContainer(), socketClient.serverSocket);
        ArrayList<PlayerInfo> playerInfo = new ArrayList<PlayerInfo>();
        playerInfo.add(new PlayerInfo(clientHandler, "player1"));
        playerInfo.add(new PlayerInfo(clientHandler, "player2"));

        VirtualGame virtualGame = new VirtualGame(playerInfo, new Lobby(""));
        //gameManager = (GameManager)virtualGame.getGameController();
        gameManager = new GameManager(playerInfo, virtualGame);
        gameManager.initializeGame();
    }

    @Test
    void initializeGame() {
        gameManager.initializeGame();
    }

    @Test
    void typeMessage() {
        gameManager.typeMessage( "player1","EVERYONE", "Hello World");
        System.out.println("\n");
        gameManager.typeMessage("player2","EVERYONE", "Hello World2");
        System.out.println("\n");
        gameManager.typeMessage("player1","player2","hello");
        System.out.println("\n");
        gameManager.typeMessage("player2","player1","hello");
        System.out.println("\n");
        gameManager.typeMessage("player2","player2","hello");
    }

    @Test
    void playStarterCard() {
        gameManager.playStarterCard("player1", starterCard);
        gameManager.playStarterCard("player2", starterCard);
    }

    @Test
    void playerDraw() {
        gameManager.playStarterCard("player1", starterCard);
        gameManager.playStarterCard("player2", starterCard);
        gameManager.playerDraw("player1", 1, ShopType.RESOURCE);
        gameManager.playerDraw("player1", 1, ShopType.OBJECTIVE);
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
        gameManager.playerPersonalMissionSelect("player2", new BendMission(
                0,
                1,
                ResourceType.ANIMAL,
                ResourceType.ANIMAL,
                CardCorner.EAST
        ));
    }

    @Test
    void playerPlayCard() {
        gameManager.playerPlayCard("player1", UtilCostantValue.lunghezzaMaxMappa/2-1, UtilCostantValue.lunghezzaMaxMappa/2, firstTestCard);
        gameManager.playStarterCard("player1", starterCard);
        gameManager.playerPlayCard("player1", UtilCostantValue.lunghezzaMaxMappa/2-1, UtilCostantValue.lunghezzaMaxMappa/2, firstTestCard);
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
