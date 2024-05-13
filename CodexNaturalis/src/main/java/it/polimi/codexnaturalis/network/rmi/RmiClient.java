package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController gameController;
    private String personalID;
    private String nickname;
    private String lobby;
    private Registry registry;
    Scanner scan = new Scanner(System.in);

    public RmiClient() throws RemoteException, NotBoundException, InterruptedException {
        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressServer, UtilCostantValue.portNumberServer);
        this.server = (VirtualServer) registry.lookup(serverName);

        // scambio dell'oggetto per comunicare col server
        personalID = server.connect(this);

        initializeClient();
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        System.out.println(message.getArgs());
    }

    @Override
    public void connectToGame(GameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    private void initializeClient() throws RemoteException {
        boolean notYetReady;

        setNicknameProcedure();

        notYetReady = true;
        while(notYetReady) {
            //after this function, the player must have joined a lobby
            selectionOfLobbies();

            if(waitingInLobbyResult()) {
                server.setPlayerReady(nickname, lobby);
                notYetReady = false;
            } else {
                server.leaveLobby(nickname, lobby);
            }
        }
    }

    private void setNicknameProcedure() throws RemoteException {
        System.out.println("Inserisci il tuo nickname:");
        while(true) {
            nickname = scan.nextLine();
            if(!server.setNickname(personalID, nickname)) {
                System.out.println("Nickname già preso, si prega di selezionare un altro: ");
            } else {
                System.out.println("Benvenuto: "+nickname);
                break;
            }
        }
    }

    private ArrayList<LobbyInfo> getLobbies() throws RemoteException {
        ArrayList<LobbyInfo> lobbies;

        lobbies = server.getAvailableLobby(nickname);

        return lobbies;
    }

    private void selectionOfLobbies() throws RemoteException {
        boolean isPlayerLobbyLess;
        ArrayList<LobbyInfo> lobbies;

        isPlayerLobbyLess = true;
        //while the player
        while(isPlayerLobbyLess) {
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

            lobby = scan.nextLine();
            switch (lobby) {
                case "CREATE":
                    while(true) {
                        System.out.println("Insert the name of the new lobby, or write 'LEAVE' return back");
                        lobby = scan.nextLine();

                        if(lobby.equals("LEAVE")) {
                            break;
                        } else if(server.createLobby(nickname, lobby)) {
                            isPlayerLobbyLess = false;
                            break;
                        } else {
                            System.out.println("Inserted lobby name already exists");
                        }
                    }
                    break;
                default:
                    if(server.joinLobby(nickname, lobby)) {
                        isPlayerLobbyLess = false;
                        break;
                    } else {
                        System.out.println("Failed to join the lobby");
                    }
                    break;
            }
        }

        System.out.println("You joined a lobby: " + lobby);
    }

    // ritorna true se il player si locka dentro la lobby, false se decide di abbandonare
    private boolean waitingInLobbyResult() {
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

    public VirtualServer getServer() {
        return server;
    }
}
