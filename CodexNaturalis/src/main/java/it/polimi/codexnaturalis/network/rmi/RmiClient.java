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
    private final String serverName = "VirtualServer";
    private final VirtualServer server;
    private String nickname;
    private String lobby;
    private Registry registry;
    Scanner scan = new Scanner(System.in);

    protected RmiClient() throws RemoteException, NotBoundException, InterruptedException {
        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddress, UtilCostantValue.portNumber);
        this.server = (VirtualServer) registry.lookup(serverName);
        System.out.println("Connessso al server RMI");
        this.server.connect(this);
    }

    @Override
    public void showValue(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public void initializeClient() throws RemoteException, InterruptedException {
        String json;
        Gson gson = new Gson();
        ArrayList<LobbyInfo> lobbies;
        boolean notYetInGame;

        setNicknameProcedure();

        notYetInGame = true;
        while(notYetInGame) {
            json = server.getAvailableLobby(nickname);
            if(!json.equals("[]")) {
                lobbies = gson.fromJson(json, new TypeToken<ArrayList<LobbyInfo>>(){}.getType());
            } else {
                System.out.println("Nessuna lobby aperta");
                lobbies = null;
            }

            selectionOfLobbies(lobbies);
            if(waitingInLobbyResult()) {
                wait();
            } else {
                server.leaveLobby(nickname, lobby);
            }
        }
    }

    private void setNicknameProcedure() throws RemoteException {
        System.out.println("Inserisci il tuo nickname:");
        while(true) {
            nickname = scan.nextLine();
            if(!server.setNickname(server.getPersonalID(), nickname)) {
                System.out.println("Nickname già preso, si prega di selezionare un altro: ");
            } else {
                System.out.println("Benvenuto: "+nickname);
                break;
            }
        }
    }

    //TODO come implementare refresh? ogni volta che dò un input refresho? chiedendo il json; pulire il codice che fa schifo
    private void selectionOfLobbies(ArrayList<LobbyInfo> lobbies) throws RemoteException {
        boolean flag;

        flag = true;
        if(lobbies != null) {
            while (flag) {
                System.out.println("Inserisci un nome di una lobby per joinare oppure scrivi 'CREA' per creare una nuova lobby");
                for (LobbyInfo elem : lobbies) {
                    System.out.println(elem.getLobbyName() + " | Player: " + elem.getCurrentPlayer() + "/" + elem.getMaxPlayer() + " | started:" + elem.getIsLobbyStarted());
                }
                lobby = scan.nextLine();

                switch (lobby) {
                    case "CREA":
                        while(true) {
                            System.out.println("Inserisci un nome di una lobby per crearla");
                            lobby = scan.nextLine();

                            if(server.createLobby(nickname, lobby)) {
                                break;
                            } else {
                                System.out.println("Nome lobby già preso");
                            }
                        }
                        flag = false;
                        break;
                    default:
                        if(server.joinLobby(nickname, lobby)) {
                            flag = false;
                            break;
                        } else {
                            System.out.println("Non è stato possibile joinare la partita");
                        }
                        break;
                }
            }
        } else {
            while(true) {
                System.out.println("Inserisci un nome di una lobby per crearla");
                lobby = scan.nextLine();

                if(server.createLobby(nickname, lobby)) {
                    break;
                } else {
                    System.out.println("Nome lobby già preso");
                }
            }
        }
        System.out.println("Hai joinato la lobby: " + lobby);
    }

    // ritorna true se il player si locka dentro la lobby, false se decide di abbandonare
    private boolean waitingInLobbyResult() {
        String command;

        while(true) {
            System.out.println("Scrivi READY per metterti in stato di ready, LEAVE per abbandonare la lobby");
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
