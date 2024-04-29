package it.polimi.codexnaturalis.network.Lobby;

import it.polimi.codexnaturalis.network.NetworkMessage;
import it.polimi.codexnaturalis.network.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.util.ArrayList;

public class LobbyThread extends Thread implements Observer {
    private LobbyInfo lobbyInfo;
    final int timoutGameStart;

    // TODO: data Race
    private ArrayList<PlayerInfo> listOfPlayers;

    public LobbyThread(String lobbyName) {
        this.listOfPlayers = new ArrayList<>();
        lobbyInfo = new LobbyInfo(lobbyName, false, UtilCostantValue.maxPlayerPerLobby);
        this.timoutGameStart = UtilCostantValue.timeoutSecGameStart;
    }

    public boolean connectPlayer(PlayerInfo player) {
        if(lobbyInfo.addPlayer()) {
            listOfPlayers.add(player);
            return true;
        } else {
            return false;
        }
    }

    //if after removing a player, currentPlayer == 0, returns False, else True
    public boolean disconnectPlayer(PlayerInfo player) {
        listOfPlayers.remove(player);

        if(lobbyInfo.removePlayer()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playerChooseColor() {
        return false;
    }

    private void startGame() {
        if(listOfPlayers.size() >= UtilCostantValue.minPlayerPerLobby) {
            //TODO
        }
        //TODO manca il metodo per registrare questo observer agli observable
    }

    //TODO
    @Override
    public void run() {
        super.run();
    }

    public String getLobbyName() {
        return lobbyInfo.getLobbyName();
    }

    public ArrayList<PlayerInfo> getListOfPlayers() {
        return listOfPlayers;
    }

    public LobbyInfo getLobbyInfo() {
        return lobbyInfo;
    }

    //TODO come gestire i messaggi al client
    @Override
    public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        switch(message.getMessageType().toString()) {
            case "CORRECT_CHOSEN_COLOR":

                break;
            case "COLOR_ALREADY_CHOSEN":

                break;

            case "WRONG_TYPE_SHOP":

                break;

            default:
                throw new PersonalizedException.InvalidRequestTypeOfNetworkMessage(message.getMessageType().toString());
        }
    }
}

