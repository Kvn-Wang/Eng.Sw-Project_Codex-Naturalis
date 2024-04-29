package it.polimi.codexnaturalis.network.rmi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.network.Lobby.LobbyInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.lang.reflect.Type;
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
    private String personalID;
    private String nickname;
    private String lobby;
    private Registry registry;
    Scanner scan = new Scanner(System.in);

    protected RmiClient() throws RemoteException, NotBoundException, InterruptedException {
        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressServer, UtilCostantValue.portNumberServer);
        this.server = (VirtualServer) registry.lookup(serverName);
        System.out.println("Connessso al server RMI");

        // scambio dell'oggetto per comunicare col server
        personalID = server.connect(this);

        initializeClient();
    }

    @Override
    public void showValue(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    private void initializeClient() throws RemoteException, InterruptedException {
        boolean notYetInGame;

        setNicknameProcedure();

        notYetInGame = true;
        while(notYetInGame) {
            //after this function, the player must have joined a lobby
            selectionOfLobbies();

            if(waitingInLobbyResult()) {
                server.setPlayerReady(nickname, lobby);
                //wait();
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
        String json;
        Gson gson = new Gson();
        ArrayList<LobbyInfo> lobbies;

        json = server.getAvailableLobby(nickname);
        if(!json.equals("[]")) {
            lobbies = gson.fromJson(json, new TypeToken<ArrayList<LobbyInfo>>(){}.getType());
        } else {
            lobbies = null;
        }

        return lobbies;
    }

    //TODO come implementare refresh? ogni volta che dò un input refresho? chiedendo il json; pulire il codice che fa schifo
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

            //TODO aggiungere refresh?

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



    @Override
    public void refreshLobbies() throws RemoteException {

    }

    //TODO: temporaneo
    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        new RmiClient();
    }
}
