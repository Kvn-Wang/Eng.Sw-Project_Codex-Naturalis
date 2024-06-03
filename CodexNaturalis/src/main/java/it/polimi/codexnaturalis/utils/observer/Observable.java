package it.polimi.codexnaturalis.utils.observer;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
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

    public String argsGenerator(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
