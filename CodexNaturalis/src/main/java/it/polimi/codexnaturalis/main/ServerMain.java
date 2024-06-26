package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.network.rmi.RmiServer;
import it.polimi.codexnaturalis.network.socket.SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));
        String add;
        System.out.println("Insert address:");
        try {
            add = (reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setProperty("java.rmi.server.hostname", add);

        new RmiServer().start();
        new SocketServer().start();
    }
}
