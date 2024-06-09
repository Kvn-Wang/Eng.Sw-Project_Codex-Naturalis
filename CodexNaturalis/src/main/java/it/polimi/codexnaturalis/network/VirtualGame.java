package it.polimi.codexnaturalis.network;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.game.GameManager;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class VirtualGame extends UnicastRemoteObject implements Serializable, GameController, Observer {
    ArrayList<PlayerInfo> players;

    //variable used to decide who can play
    int currentPlayerIndex;
    int startingPlayerIndex;
    int starterCardPlaced = 0;
    GameController gameController;
    public VirtualGame(ArrayList<PlayerInfo> players) throws RemoteException {
        super();
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
        gameController = new GameManager(playerInfoNicknameColor, this);
    }

    private PlayerInfo getNextPlayer() {
        PlayerInfo nextPlayer = players.get(currentPlayerIndex);
        // Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return nextPlayer;
    }

    @Override
    public void initializeGame() throws RemoteException {
        gameController.initializeGame();
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
    public void playerDraw(String nickname, int numCard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage, RemoteException {
        if(nickname.equals(players.get(currentPlayerIndex).getNickname()))
            gameController.playerDraw(nickname, numCard, type);
        else {
            try {
                nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.NOT_YOUR_TURN));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) throws RemoteException {
            gameController.playerPersonalMissionSelect(nickname, numMission);
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException, RemoteException {
        if(nickname.equals(players.get(currentPlayerIndex).getNickname())||starterCardPlaced<players.size()) {
            starterCardPlaced++;
            gameController.playerPlayCard(nickname, x, y, numCard, isCardBack);
        }
        else {
            try {
                nickToPlayerInfo(nickname).notifyPlayer(new NetworkMessage(MessageType.NOT_YOUR_TURN));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
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
            case COM_ACK_TCP, CORRECT_PLACEMENT, GAME_SETUP_STARTER_CARD:
                System.out.println("Messaggio per "+message.getNickname()+" di tipo:"+message.getMessageType());
                try {
                    nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;

            case WRONG_TYPE_SHOP:
                try {
                    nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(new NetworkMessage(MessageType.WRONG_TYPE_SHOP, "WRONG_TYPE_SHOP"));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;

            case CORRECT_DRAW_CARD:
                try {
                    nickToPlayerInfo(message.getNickname()).getClientHandler().showMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                getNextPlayer();
                break;

            //messaggio di broadCast con un argomento
            case SCORE_UPDATE, STATUS_PLAYER_CHANGE, SHOP_UPDATE:
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
