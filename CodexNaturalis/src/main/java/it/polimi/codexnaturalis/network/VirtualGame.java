package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.game.GameManager;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualGame extends UnicastRemoteObject implements Serializable, GameController, Observer {
    /**
     * Agreement: the starting player is the first one in the list get(0) after the list has been
     *   shuffled in the constructor
     */
    private ArrayList<PlayerInfo> players;

    //variable used to decide who can play
    private int currentPlayingPlayerIndex;
    /**
     * keeps track of which playing phase is the player in
     */
    GameState gameState;
    private GameController gameController;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public VirtualGame(ArrayList<PlayerInfo> players) throws RemoteException {
        super();
        this.players = players;
        gameState = GameState.PLAY_PHASE;

        // random order of play
        Collections.shuffle(this.players);
        currentPlayingPlayerIndex = 0;

        // initialize the real game controller
        gameController = new GameManager(players, this, gameState);
    }

    public GameController getGameController() {
        return gameController;
    }

    /**
     * notify the next player that it's his turn
     */
    private void goToNextPlayer()  {
        // Move to the next player
        currentPlayingPlayerIndex = (currentPlayingPlayerIndex + 1) % players.size();

        try {
            players.get(currentPlayingPlayerIndex).getClientHandler().showMessage(
                    new NetworkMessage(MessageType.YOUR_TURN)
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeGame() throws RemoteException {
        gameController.initializeGame();
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard) throws RemoteException {
        executorService.submit(() -> {
            try {
                gameController.playStarterCard(playerNick, starterCard);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) throws RemoteException {
        executorService.submit(() -> {
            try {
                gameController.playerPersonalMissionSelect(nickname, mission);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        gameController.disconnectPlayer(nickname);
    }

    @Override
    public void reconnectPlayer(String nickname) throws RemoteException {
        gameController.reconnectPlayer(nickname);
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) throws RemoteException {
        executorService.submit(() -> {
            try {
                if (nickname.equals(players.get(currentPlayingPlayerIndex).getNickname())) {
                    if(gameState == GameState.DRAW_PHASE) {
                        gameController.playerDraw(nickname, numcard, type);

                        //vai al prox turno
                        goToNextPlayer();
                        gameState = GameState.PLAY_PHASE;
                    } else {
                        nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.ERR_GAME_STATE_COMMAND, String.valueOf(gameState)));
                    }
                } else {
                    nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.NOT_YOUR_TURN));
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) {
        executorService.submit(() -> {
            try {
                if(nickname.equals(players.get(currentPlayingPlayerIndex).getNickname())) {
                    if(gameState == GameState.PLAY_PHASE) {
                        gameController.playerPlayCard(nickname, x, y, playedCard);
                        gameState = GameState.DRAW_PHASE;
                    } else {
                        nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.ERR_GAME_STATE_COMMAND, String.valueOf(gameState)));
                    }
                }
                else {
                    nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.NOT_YOUR_TURN));
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) throws RemoteException {
        gameController.typeMessage(receiver, sender, msg);
    }

    @Override
    public void switchPlayer(String reqPlayer, String target) throws RemoteException {
        gameController.switchPlayer(reqPlayer, target);
    }

    //traduzione nick -> playerInfo
    private PlayerInfo nickToPlayerInfo(String nickname){
        for(PlayerInfo p: players){
            if(nickname.equals(p.getNickname()))
                return p;
        }
        System.err.println("player non trovato");
        return null;
    }

    @Override
    public void endGame() throws RemoteException {
        gameController.endGame();
    }

    @Override
    public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        switch(message.getMessageType()) {
            //messaggi per playerSpecifici con argomenti illimitati
            case COM_ACK_TCP, CORRECT_PLACEMENT, GAME_SETUP_GIVE_STARTER_CARD, GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,
                    GAME_SETUP_SEND_PERSONAL_MISSION, GAME_SETUP_NOTIFY_TURN, PLACEMENT_CARD_OUTCOME, UPDATE_OTHER_PLAYER_GAME_MAP:
                System.out.println("Messaggio per "+message.getNickname()+" di tipo:"+message.getMessageType());
                try {
                    nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;

            case DRAWN_CARD_DECK:
                try {
                    nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;

            //per messaggi di broadcast con numero illimiatato di args
            case SCORE_UPDATE, STATUS_PLAYER_CHANGE, DRAW_CARD_UPDATE_SHOP_CARD_POOL:
                System.out.println("Messaggio broadcast: "+message.getMessageType());
                for(PlayerInfo p: players){
                    try {
                        p.getClientHandler().showMessage(message);
                        //p.getClientHandler().showMessage(new NetworkMessage(message.getMessageType(), message.getNickname(), message.getArgs().get(0)));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            default:
                throw new PersonalizedException.InvalidRequestTypeOfNetworkMessage(message.getMessageType().toString());
        }
    }
}
