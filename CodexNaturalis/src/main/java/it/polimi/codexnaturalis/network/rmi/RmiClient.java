package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.network.util.GeneralTuiClient;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class RmiClient extends GeneralTuiClient implements VirtualView {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController gameController;
    private String personalID;
    private String nickname;
    private String lobby;
    private Registry registry;

    public RmiClient() throws RemoteException, NotBoundException, InterruptedException {
        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressSocketServer, UtilCostantValue.portRmiServer);
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
        String outcomeChosen;

        //set nickname phase
        while(true){
            nickname = setNicknameProcedure(personalID);
            if(printNicknameProcedureOutcome(!server.setNickname(personalID, nickname))) {
                break;
            }
        }

        //join a lobby phase
        notYetReady = true;
        while(notYetReady) {
            //after this function, the player must have joined a lobby
            while(true) {
                outcomeChosen = joinOrCreateSelection();
                if(outcomeChosen.equals("CREATE")) {
                    lobby = createLobbySelection();

                    if(lobby.equals("LEAVE")) {
                        break;
                    }else if(server.createLobby(nickname, lobby)) {
                        System.out.println("You created a lobby");
                        break;
                    } else {
                        System.out.println("Inserted lobby name already exists");
                    }
                } else {
                    printJoinLobbyOutcome(server.joinLobby(nickname, lobby));
                }
            }

            //set ready or leave phase
            if(waitingInLobbyResult()) {
                server.setPlayerReady(nickname, lobby);
                notYetReady = false;
            } else {
                server.leaveLobby(nickname, lobby);
            }
        }
    }

    public ArrayList<LobbyInfo> getLobbies() throws RemoteException {
        ArrayList<LobbyInfo> lobbies;

        lobbies = server.getAvailableLobby();

        return lobbies;
    }

    public VirtualServer getServer() {
        return server;
    }
}
