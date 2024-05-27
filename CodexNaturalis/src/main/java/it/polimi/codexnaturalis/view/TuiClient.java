package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainerController;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class TuiClient implements TypeOfUI {
    protected VirtualServer networkCommand;
    protected GameController virtualGame;
    Scanner scan;

    public TuiClient() {
        scan = new Scanner(System.in);
    }

    @Override
    public void connectVirtualNetwork(VirtualServer virtualNetworkCommand) {
        this.networkCommand = virtualNetworkCommand;
    }

    @Override
    public void connectGameController(GameController virtualGame, ClientContainerController clientContainerController) {
        this.virtualGame = virtualGame;
    }

    @Override
    public void printSelectionNicknameRequest() throws RemoteException {
        String nickname;

        System.out.println("Inserisci il tuo nickname:");

        nickname = scan.nextLine();
        networkCommand.setNickname(null, nickname);
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
        if(positiveOutcome) {
            System.out.println("Benvenuto: "+ nickname);
        } else {
            System.out.println("Nickname già preso, si prega di selezionare un altro.");
        }
    }

    private void printLobby() throws RemoteException {
        ArrayList<LobbyInfo> lobbies;

        lobbies = networkCommand.getAvailableLobby();
        System.out.println("Lobby list:");
        if(lobbies != null) {
            for (LobbyInfo elem : lobbies) {
                System.out.println("  - " + elem.getLobbyName() + " | Player: " + elem.getCurrentPlayer() + "/" + elem.getMaxPlayer() + " | started:" + elem.getIsLobbyStarted());
            }
        } else {
            System.out.println("  - No lobby Available");
        }
    }

    @Override
    public void printSelectionCreateOrJoinLobbyRequest() throws RemoteException {
        String command;

        printLobby();

        System.out.println("Write the name of an existing lobby to join it or write 'CREATE' to create a new lobby");
        command = scan.nextLine();

        switch(command) {
            case "CREATE":
                System.out.println("Inserisci il nuovo nome della lobby, LEAVE per uscire dalla modalità CREATE");
                command = scan.nextLine();

                if(command.equals("LEAVE")) {
                    printSelectionCreateOrJoinLobbyRequest();
                } else {
                    networkCommand.createLobby(null, command);
                }
                break;
            default:
                networkCommand.joinLobby(null, command);
                break;
        }
    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException {
        if(positiveOutcome) {
            System.out.println("you've joined the lobby: "+lobbyName);
        } else {
            System.out.println("you failed to join the lobby");
        }
    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {
        if(outcomePositive) {
            System.out.println("You successfully created the lobby: "+ lobbyName);
        } else {
            System.out.println("Creation lobby failed, "+lobbyName+" name has already been taken!");
        }
    }

    @Override
    public void printReadyOrLeaveSelection() throws RemoteException {
        String command;
        boolean flag;

        flag = true;
        while(flag) {
            System.out.println("Write READY to set your state as ready, LEAVE to leave the lobby");
            command = scan.nextLine();

            switch(command) {
                case "READY":
                    networkCommand.setPlayerReady(null);
                    flag = false;
                    break;

                case "LEAVE":
                    networkCommand.leaveLobby(null);
                    flag = false;
                    break;

                default:
                    System.out.println("Comando non valido");
                    break;
            }
        }
    }

    @Override
    public void printReadyOrLeaveSelectionOutcome(boolean isReady) {
        if(isReady) {
            //System.out.println("You set yourself ready!");
        } else {
            System.out.println("You've left the lobby!");
        }
    }

    @Override
    public void notifyLobbyStatus(String otherPlayerNickname, String status) {
        if(status.equals("JOIN")) {
            System.out.println(otherPlayerNickname + " has joined the lobby!");
        } else if(status.equals("LEFT")){
            System.out.println(otherPlayerNickname + " has left the lobby");
        } else if(status.equals("READY")) {
            System.out.println(otherPlayerNickname + " is ready");
        } else if(status.equals("WAIT")) {
            System.out.println("Wait for more players");
        } else {
            System.err.println("Has been called an invalid command: "+status);
        }
    }
}
