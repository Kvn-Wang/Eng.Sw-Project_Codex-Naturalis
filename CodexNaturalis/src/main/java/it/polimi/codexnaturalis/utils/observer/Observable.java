package it.polimi.codexnaturalis.utils.observer;

import it.polimi.codexnaturalis.network.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.util.ArrayList;

public class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notifyObserver(NetworkMessage message) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        for(Observer element : observers) {
            element.update(message);
        }
    }
}
