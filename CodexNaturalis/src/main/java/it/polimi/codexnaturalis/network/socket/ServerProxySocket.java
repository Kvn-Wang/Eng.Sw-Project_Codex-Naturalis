package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.MessageType;
import it.polimi.codexnaturalis.network.util.NetworkMessage;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerProxySocket implements VirtualServer {
    final PrintWriter output;

    public ServerProxySocket(PrintWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public String connect(VirtualView client) throws RemoteException {
        throw new RuntimeException("Metodo chiamato non valido! siamo in socket, non esiste connect");
    }

    @Override
    public boolean setNickname(String userID, String nickname) throws RemoteException {
        String json = convertToJson(new NetworkMessage(MessageType.COM_SET_NICKNAME_TCP, nickname));

        System.out.println("ho");
        output.println(json);
        System.out.println("inviato");

        return false;
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        return null;
    }

    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        return false;
    }

    @Override
    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException {

    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        return false;
    }

    @Override
    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException {

    }

    private String convertToJson(NetworkMessage message) {
        String json;
        Gson gson = new Gson();

        json = gson.toJson(message);

        return json;
    }
}
