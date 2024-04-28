package it.polimi.codexnaturalis.network.rmi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.network.Lobby.LobbyInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

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

    protected RmiClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddress, UtilCostantValue.portNumber);
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
    public void initializeClient() throws RemoteException {
        String json;
        Gson gson = new Gson();
        ArrayList<LobbyInfo> lobbies;

        setNicknameProcedure();

        json = server.getAvailableLobby(nickname);
        if(!json.equals("[]")) {
            lobbies = gson.fromJson(json, new TypeToken<ArrayList<LobbyInfo>>(){}.getType());
        } else {
            System.out.println("Nessuna lobby aperta");
            lobbies = null;
        }

        selectionOfLobbies(lobbies);
    }

    private void setNicknameProcedure() throws RemoteException {
        Scanner scan = new Scanner(System.in);

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
        Scanner scan = new Scanner(System.in);
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

    @Override
    public void refreshLobbies() throws RemoteException {

    }

    //TODO: temporaneo
    public static void main(String[] args) throws NotBoundException, RemoteException {
        new RmiClient();
    }
}
