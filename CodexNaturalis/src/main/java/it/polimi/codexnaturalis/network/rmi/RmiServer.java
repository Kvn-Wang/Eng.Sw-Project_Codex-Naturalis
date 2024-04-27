package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.network.ServerContainer;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RmiServer extends Thread implements VirtualServer {
    private String name = "VirtualServer";
    private ArrayList<VirtualView> nicknameLessClients;
    ServerContainer serverContainer;

    public RmiServer() {
        this.serverContainer = ServerContainer.getInstance();
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        System.out.println("Connected client: "+client);
        nicknameLessClients.add(client);
        client.askNickname();
    }

    @Override
    public boolean setNickname(String nickname) throws RemoteException {
        return serverContainer.checkNickGlobalNicknameValidity(nickname);
    }

    @Override
    public String getAvailableLobby(String nickname) throws RemoteException {
        //TODO
        return null;
    }

    public void run() {
        nicknameLessClients = new ArrayList<>();
        VirtualServer engine = new RmiServer();
        VirtualServer stub = null;

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(UtilCostantValue.portNumber);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RMI server started");
    }

    @Override
    public void initializeGame() {

    }

    @Override
    public void disconnectPlayer(String nickname) {

    }

    @Override
    public void reconnectPlayer(String nickname) {

    }

    @Override
    public void playerDraw(String nickname, int Numcard, String type) {

    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {

    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {

    }

    @Override
    public void playerPlayStarterCard(String nickname, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {

    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) {

    }

    @Override
    public void switchPlayer(String reqPlayer, String target) {

    }

    @Override
    public void endGame() {

    }
}
