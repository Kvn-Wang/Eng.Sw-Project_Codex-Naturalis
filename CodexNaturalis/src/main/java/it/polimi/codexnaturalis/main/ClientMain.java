package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.rmi.RmiClient;
import it.polimi.codexnaturalis.network.socket.SocketClient;
import it.polimi.codexnaturalis.view.GuiClient;
import it.polimi.codexnaturalis.view.TuiClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        int typeOfconnectionClient;
        int typeOfUserView;
        Scanner scan = new Scanner(System.in);

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
                new SocketClient();
            } else {
                new SocketClient();
            }
        } else {
            if(typeOfUserView == 1) {
                new RmiClient(new GuiClient());
            } else {
                new RmiClient(new TuiClient());
            }
        }
    }
}
