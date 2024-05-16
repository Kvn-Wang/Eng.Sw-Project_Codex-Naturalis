package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class TuiClient implements TypeOfUI {
    protected VirtualNetworkCommand networkCommand;
    Scanner scan;

    public TuiClient() {
        scan = new Scanner(System.in);
    }

    @Override
    public void connectVirtualNetwork(VirtualNetworkCommand virtualNetworkCommand) {
        this.networkCommand = virtualNetworkCommand;
    }

    @Override
    public void printSelectionNicknameRequest() throws RemoteException {
        String nickname;

        System.out.println("Inserisci il tuo nickname:");

        nickname = scan.nextLine();
        networkCommand.selectNickname(nickname);
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

        lobbies = networkCommand.getLobbies();
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
                    networkCommand.createLobby(command);
                }
                break;
            default:
                networkCommand.joinLobby(command);
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
                    networkCommand.setReady();
                    flag = false;
                    break;

                case "LEAVE":
                    networkCommand.leaveLobby();
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
            System.out.println("You set yourself ready!");
        } else {
            System.out.println("You've left the lobby!");
        }
    }
}
