package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.network.rmi.RmiClient;
import it.polimi.codexnaturalis.network.rmi.RmiServer;
import it.polimi.codexnaturalis.network.socket.SocketClient;
import it.polimi.codexnaturalis.view.GUI.GuiClient;
import it.polimi.codexnaturalis.view.TUI.TuiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        int typeOfconnectionClient;
        int typeOfUserView;
        Scanner scan = new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));
        String address;
        System.out.println("Insert address:");
        try {
            address = (reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        typeOfconnectionClient = 0;
        do {
            System.out.println("1 for socket, 2 for RMI :");
            try {
                typeOfconnectionClient = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid value!");
            }
        }while (typeOfconnectionClient != 2 && typeOfconnectionClient != 1);

        typeOfUserView = 0;
        do {
            System.out.println("1 for GUI, 2 for TUI :");
            try {
                typeOfUserView = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid value!");
            }
        }while (typeOfUserView != 2 && typeOfUserView != 1);

        if(typeOfconnectionClient == 1) {
            if(typeOfUserView == 1) {
                GuiClient guiClient = new GuiClient();
                SocketClient socketClient = new SocketClient(guiClient, address);
                guiClient.initializeClient(socketClient, socketClient.getClientContainerController());
            } else {
                TuiClient tuiClient = new TuiClient();
                SocketClient socketClient = new SocketClient(tuiClient, address);
                tuiClient.initializeClient(socketClient, socketClient.getClientContainerController());
            }
        } else {
            if(typeOfUserView == 1) {
                GuiClient guiClient = new GuiClient();
                RmiClient rmiClient = new RmiClient(guiClient, address);
                guiClient.initializeClient(rmiClient, rmiClient.getClientContainerController());
            } else {
                TuiClient tuiClient = new TuiClient();
                RmiClient rmiClient = new RmiClient(tuiClient, address);
                tuiClient.initializeClient(rmiClient, rmiClient.getClientContainerController());
            }
        }
    }
}
