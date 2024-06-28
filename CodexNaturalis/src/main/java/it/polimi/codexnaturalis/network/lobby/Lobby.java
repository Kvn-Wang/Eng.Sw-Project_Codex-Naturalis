package it.polimi.codexnaturalis.network.lobby;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.VirtualGame;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The type Lobby.
 */
public class Lobby {
    private ServerContainer serverContainer = ServerContainer.getInstance();
    private LobbyInfo lobbyInfo;
    private ArrayList<PlayerInfo> listOfPlayers;
    private GameController gameController;

    /**
     * Instantiates a new Lobby.
     *
     * @param lobbyName the lobby name
     */
    public Lobby(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        lobbyInfo = new LobbyInfo(lobbyName, false, UtilCostantValue.maxPlayerPerLobby);
        //this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    /**
     * Connect player boolean.
     *
     * @param player the player
     * @return the boolean
     * @throws RemoteException the remote exception
     */
    public boolean connectPlayer(PlayerInfo player) throws RemoteException {
        if(lobbyInfo.addPlayer()) {
            synchronized (listOfPlayers) {
                listOfPlayers.add(player);
            }

            //notifica al player, chi è nella lobby al momento
            player.notifyPlayer(new NetworkMessage(MessageType.COM_JOIN_LOBBY_OTHER_PLAYER_INFO_TCP, argsGenerator(listOfPlayers)));

            broadCastNotify(player.getNickname(), "JOIN");

            return true;
        } else {
            return false;
        }
    }

    /**
     * Disconnect player boolean.
     *
     * @param player the player
     * @return if after removing a player, currentPlayer == 0, returns False, else True
     * @throws RemoteException the remote exception
     */
    public boolean disconnectPlayer(PlayerInfo player) throws RemoteException {
        synchronized (listOfPlayers) {
            listOfPlayers.remove(player);
        }

        /**
         * reset of the eventual color that he has chosen
         */
        player.setColorChosen(null);

        broadCastNotify(player.getNickname(), "LEFT");

        if(lobbyInfo.removePlayer()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets player color.
     *
     * @param player      the player
     * @param colorChosen the color chosen
     */
    public void setPlayerColor(PlayerInfo player, ColorType colorChosen) {
        boolean someoneHasChosenThatColor = false;
        System.out.println("Setting up color for: "+ player.getNickname());

        for(PlayerInfo singlePlayer : listOfPlayers) {
            if(player != singlePlayer && singlePlayer.getColorChosen() == colorChosen) {
                someoneHasChosenThatColor = true;
            }
        }

        try {
            if(someoneHasChosenThatColor) {
                player.notifyPlayer(new NetworkMessage(MessageType.COM_SET_PLAYER_COLOR_OUTCOME,
                        String.valueOf(false)));
            } else {
                player.setColorChosen(colorChosen);
                // broadCast a tutti che ho selezionato quel colore
                for(PlayerInfo elem : listOfPlayers) {
                    elem.notifyPlayer(new NetworkMessage(player.getNickname(), MessageType.COM_SET_PLAYER_COLOR_OUTCOME,
                            String.valueOf(true), player.getNickname(), String.valueOf(colorChosen)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets player ready.
     *
     * @param player the player
     * @throws RemoteException the remote exception
     */
    public void setPlayerReady(PlayerInfo player) throws RemoteException {
        synchronized (listOfPlayers) {
            listOfPlayers.get(listOfPlayers.indexOf(player)).setPlayerReady(true);
        }

        broadCastNotify(player.getNickname(), "READY");

        //ogni volta che qualcuno si mette in ready, prova ad avviare la partita
        startGame();
    }

    private void startGame() throws RemoteException {
        synchronized (listOfPlayers) {
            if(listOfPlayers.size() >= UtilCostantValue.minPlayerPerLobby) {
                boolean allPlayerReady;

                allPlayerReady = true;
                for(PlayerInfo playerInfo : listOfPlayers) {
                    if(!playerInfo.isPlayerReady()) {
                        allPlayerReady = false;
                    }
                }

                if(allPlayerReady) {
                    connectPlayerToGame();
                }
            } else {
                broadCastNotify(listOfPlayers.get(0).getNickname(), "WAIT");
            }
        }
    }

    // crea il game e passa l'interfaccia ad ogni player (RMI)
    private void connectPlayerToGame() throws RemoteException {
        lobbyInfo.isLobbyStarted = true;

        // passo ad ogni player il virtualGameController e la lista degli altri player
        gameController = new VirtualGame(listOfPlayers, this);
        for(PlayerInfo playerInfo : listOfPlayers) {
            playerInfo.getClientHandler().connectToGame(gameController, copyArrayListExceptOne(listOfPlayers, playerInfo));
        }

        System.out.println("Lobby '" + lobbyInfo.getLobbyName() + "': all player connected to the game!");

        gameController.initializeGame();
    }

    private ArrayList<PlayerInfo> copyArrayListExceptOne(ArrayList<PlayerInfo> players, PlayerInfo exception) {
        ArrayList<PlayerInfo> playerList = new ArrayList<>();

        for(PlayerInfo elem : players) {
            if(!(elem == exception)) {
                playerList.add(elem);
            }
        }

        return playerList;
    }

    private void broadCastNotify(String player, String command) throws RemoteException {
        for(PlayerInfo elem : listOfPlayers) {
            elem.notifyPlayer(new NetworkMessage(player, MessageType.COM_LOBBY_STATUS_NOTIFY, command));
        }
    }

    /**
     * Gets lobby name.
     *
     * @return the lobby name
     */
    public String getLobbyName() {
        return lobbyInfo.getLobbyName();
    }

    /**
     * Gets list of players.
     *
     * @return the list of players
     */
    public ArrayList<PlayerInfo> getListOfPlayers() {
        synchronized (listOfPlayers) {
            return listOfPlayers;
        }
    }

    /**
     * Gets lobby info.
     *
     * @return the lobby info
     */
    public LobbyInfo getLobbyInfo() {
        return lobbyInfo;
    }

    public String argsGenerator(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public void gameEnded() {
        serverContainer.gameEnded(getLobbyName());
    }
}

