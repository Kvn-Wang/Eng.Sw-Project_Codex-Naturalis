package it.polimi.codexnaturalis.network.util;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

// contiene i metodi che devo inserire da terminale
public abstract class GeneralTuiClient extends UnicastRemoteObject implements GeneralClientView {
    private Scanner scan;

    protected GeneralTuiClient() throws RemoteException {
        super();
        scan = new Scanner(System.in);
    }

    //if RMI personalID will have value, otherwise is just null
    public String setNicknameProcedure(String personalID) {
        String nickname;

        System.out.println("Inserisci il tuo nickname:");
        nickname = scan.nextLine();

        return nickname;
    }

    public boolean printNicknameProcedureOutcome(boolean nicknameTaken) {
        if(nicknameTaken) {
            System.out.println("Nickname già preso, si prega di selezionare un altro. ");
            return false;
        } else {
            System.out.println("Benvenuto nel game.");
            return true;
        }
    }

    // ritorna una stringa che se può essere: CREATE, oppure un nome a caso = lobby da joinare
    public String joinOrCreateSelection() throws RemoteException {
        String lobby;

        printLobbies(getLobbies());

        lobby = scan.nextLine();

        return lobby;
    }

    public String createLobbySelection() {
        String lobby;

        System.out.println("Inserisci il nuovo nome della lobby, LEAVE per uscire dalla modalità CREATE");
        lobby = scan.nextLine();

        return lobby;
    }

    public void printJoinLobbyOutcome(boolean isJoined) {
        if(isJoined) {
            System.out.println("You joined the lobby");
        } else {
            System.out.println("Failed to join the lobby");
        }
    }

    private void printLobbies(ArrayList<LobbyInfo> lobbies) throws RemoteException {
        lobbies = getLobbies();
        System.out.println("Lobby list:");
        if(lobbies != null) {
            for (LobbyInfo elem : lobbies) {
                System.out.println("  - " + elem.getLobbyName() + " | Player: " + elem.getCurrentPlayer() + "/" + elem.getMaxPlayer() + " | started:" + elem.getIsLobbyStarted());
            }
            System.out.println("Write the name of an existing lobby to join it or write 'CREATE' to create a new lobby");
        } else {
            System.out.println("  - No lobby Available");
            System.out.println("Write 'CREATE' to create a new lobby");
        }
    }

    // ritorna true se il player si locka dentro la lobby, false se decide di abbandonare
    public boolean waitingInLobbyResult() {
        String command;

        while(true) {
            System.out.println("Write READY to set your state as ready, LEAVE to leave the lobby");
            command = scan.nextLine();

            switch(command) {
                case "READY":
                    return true;
                case "LEAVE":
                    return false;
                default:
                    System.out.println("Comando non valido");
                    break;
            }
        }
    }
}
