package it.polimi.codexnaturalis.utils.observer;

import it.polimi.codexnaturalis.network.NetworkMessage;

import java.util.ArrayList;

public class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notifyObserver(NetworkMessage message) {
        for(Observer element : observers) {
            element.update(message);
        }
    }
}
