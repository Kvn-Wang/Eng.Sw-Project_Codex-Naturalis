package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.network.util.GeneralTuiClient;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class RmiClient extends GenericClient {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController gameController;
    private String personalID;
    private Registry registry;

    public RmiClient(TypeOfUI typeOfUI) throws RemoteException, NotBoundException, InterruptedException {
        //setup communicazione bidirezionale tra rete e oggetto grafico
        super(typeOfUI);
        typeOfUI.connectVirtualNetwork(this);

        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressSocketServer, UtilCostantValue.portRmiServer);
        this.server = (VirtualServer) registry.lookup(serverName);

        System.out.println("Collegato al server: " + serverName);

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
        // per com'è stato scritto il codice, dopo questa riga avremo un nickname sicuramente settato correttamente
        // stessa cosa vale per le righe successive
        typeOfUI.printSelectionNicknameRequest();

        //setup lobbyName unico
        typeOfUI.printSelectionCreateOrJoinLobbyRequest();

        typeOfUI.printReadyOrLeaveSelection();
    }

    public VirtualServer getServer() {
        return server;
    }

    @Override
    public void selectNickname(String nickname) throws RemoteException {
        if(server.setNickname(personalID, nickname)) {
            this.playerNickname = nickname;
            typeOfUI.printSelectionNicknameRequestOutcome(true, this.playerNickname);
        } else {
            typeOfUI.printSelectionNicknameRequestOutcome(false, nickname);

            //emula un loop infinito finchè non sceglie un nickname corretto
            typeOfUI.printSelectionNicknameRequest();
        }
    }

    @Override
    public ArrayList<LobbyInfo> getLobbies() throws RemoteException {
        return server.getAvailableLobby();
    }

    @Override
    public void joinLobby(String lobbyName) throws RemoteException {
        if(server.joinLobby(playerNickname, lobbyName)) {
            this.lobbyNickname = lobbyName;
            typeOfUI.printJoinLobbyOutcome(true, this.lobbyNickname);
        } else {
            typeOfUI.printJoinLobbyOutcome(false, lobbyName);

            //start loop
            typeOfUI.printSelectionCreateOrJoinLobbyRequest();
        }
    }

    @Override
    public void createLobby(String lobbyName) throws RemoteException {
        if(server.createLobby(playerNickname, lobbyName)) {
            this.lobbyNickname = lobbyName;
            typeOfUI.printCreationLobbyRequestOutcome(true, this.lobbyNickname);
        } else {
            typeOfUI.printCreationLobbyRequestOutcome(false, lobbyName);
        }
    }

    @Override
    public void setReady() throws RemoteException {
        server.setPlayerReady(playerNickname, lobbyNickname);
        typeOfUI.printReadyOrLeaveSelectionOutcome(true);
    }

    @Override
    public void leaveLobby() throws RemoteException {
        server.leaveLobby(playerNickname, lobbyNickname);
        typeOfUI.printReadyOrLeaveSelectionOutcome(false);
    }
}
