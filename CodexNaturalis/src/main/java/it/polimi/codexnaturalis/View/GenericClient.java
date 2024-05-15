package it.polimi.codexnaturalis.View;

import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;

public abstract class GenericClient implements VirtualNetworkCommand, VirtualView {
    String playerNickname;
    String lobbyNickname;
}
