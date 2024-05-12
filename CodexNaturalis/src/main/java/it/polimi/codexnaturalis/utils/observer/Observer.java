package it.polimi.codexnaturalis.utils.observer;

import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;

public interface Observer {
    public void update(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage;
}
