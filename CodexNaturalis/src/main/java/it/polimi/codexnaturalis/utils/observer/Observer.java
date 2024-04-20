package it.polimi.codexnaturalis.utils.observer;

import it.polimi.codexnaturalis.network.NetworkMessage;

public interface Observer {
    public void update(NetworkMessage message);
}
