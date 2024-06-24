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
    private ExecutorService executorService;
    private ExecutorService senderExecturorService;

    /**
     * variable used for the final turn mechanic
     */
    private boolean finalGamePhaseTurn;
    private boolean finalTurn;

    public VirtualGame(ArrayList<PlayerInfo> players) throws RemoteException {
        super();
        this.players = players;
        gameState = GameState.PLAY_PHASE;
        finalGamePhaseTurn = false;
        finalTurn = false;

        /**
         * create the only executor that will have access to the model of the game, and
         *  a sender for every player that the model need to contact
         */
        executorService = Executors.newSingleThreadExecutor();
        senderExecturorService = Executors.newFixedThreadPool(this.players.size());

        // random order of play
        Collections.shuffle(this.players);
        currentPlayingPlayerIndex = 0;

        // initialize the real game controller
        gameController = new GameManager(players, this);
        System.out.println("Current game state: " + gameState.toString());
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

        // Check if it's the final turn and an extra turn has been taken
        if ((currentPlayingPlayerIndex % players.size() == 0) && finalGamePhaseTurn && finalTurn) {
            gameEnded();
            return;
        } else if((currentPlayingPlayerIndex % players.size() == 0) && finalGamePhaseTurn) {
            // finisci il turno in corso
            finalTurn = true;
        }

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
    public void playerDraw(String nickname, int numcard, ShopType type) throws RemoteException {
        executorService.submit(() -> {
            try {
                System.out.println(nickname+ " player has drawn a card: "+numcard+" from: "+type);
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
                System.out.println("il giocatore: "+nickname+" sta cercando di posizionare una carta in ("+x+","+y+"), durante la fase di gioco: "+gameState);
                if(nickname.equals(players.get(currentPlayingPlayerIndex).getNickname())) {
                    if(gameState == GameState.PLAY_PHASE) {
                        gameController.playerPlayCard(nickname, x, y, playedCard);
                        System.out.println(((GameManager) gameController).errorDuringPlayingPhase);
                        if(!((GameManager) gameController).errorDuringPlayingPhase == true) {
                            gameState = GameState.DRAW_PHASE;
                        }

                    } else {
                        System.out.println("Errore giocata carta");
                        nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.ERR_GAME_STATE_COMMAND, String.valueOf(gameState)));
                    }
                }
                else {
                    System.out.println("non è il suo turno");
                    nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.NOT_YOUR_TURN));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) throws RemoteException {
        gameController.typeMessage(receiver, sender, msg);
    }

    // not implemented
    @Override
    public void gameEnd() throws RemoteException {}

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
    public void update(NetworkMessage message) {
        switch(message.getMessageType()) {
            //messaggi per playerSpecifici con argomenti illimitati
            case GAME_SETUP_GIVE_STARTER_CARD, GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,
                    GAME_SETUP_SEND_PERSONAL_MISSION, GAME_SETUP_NOTIFY_TURN, PLACEMENT_CARD_OUTCOME, UPDATE_OTHER_PLAYER_GAME_MAP,
                    DRAWN_CARD_DECK:

                System.out.println("Messaggio per "+message.getNickname()+" di tipo:"+message.getMessageType());

                senderExecturorService.submit(() -> {
                    try {
                        nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(message);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            //per messaggi di broadcast con numero illimiatato di args
            case DRAW_CARD_UPDATE_SHOP_CARD_POOL, GAME_ENDED:
                System.out.println("Messaggio broadcast: "+message.getMessageType());
                for(PlayerInfo p: players){
                    senderExecturorService.submit(() -> {
                        try {
                            p.getClientHandler().showMessage(message);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                break;

            case NOTIFY_FINAL_TURN:
                System.out.println("Turno finale!!!");

                /**
                 * notify all players
                 */
                for(PlayerInfo p: players){
                    senderExecturorService.submit(()  -> {
                        try {
                            p.getClientHandler().showMessage(message);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                /**
                 * initiate the procedure to end the game
                 */
                finalGamePhaseTurn = true;

                break;

            default:
                System.err.println("Network message type requested not valid or not in the switch case: "+ message.getMessageType());
        }
    }

    private void gameEnded() {
        /**
         * notify all players
         */
        try {
            gameController.gameEnd();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
