module it.polimi.codexnaturalis {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;
    requires java.desktop;
    requires org.junit.jupiter.api;

    exports it.polimi.codexnaturalis.model.shop;
    opens it.polimi.codexnaturalis.model.shop to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.model.enumeration;
    opens it.polimi.codexnaturalis.model.enumeration to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.model.shop.card;
    opens it.polimi.codexnaturalis.model.shop.card to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.model.player;
    opens it.polimi.codexnaturalis.model.player to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.model.mission;
    opens it.polimi.codexnaturalis.model.mission to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.model.game;
    opens it.polimi.codexnaturalis.model.game to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.controller;
    opens it.polimi.codexnaturalis.controller to javafx.fxml, com.google.gson;
    exports it.polimi.codexnaturalis.network.rmi;
    opens it.polimi.codexnaturalis.network.lobby to com.google.gson, javafx.base;
    opens it.polimi.codexnaturalis.network to com.google.gson, javafx.base;
    opens it.polimi.codexnaturalis.network.rmi to com.google.gson, javafx.base;
    exports it.polimi.codexnaturalis.network.communicationInterfaces;
    exports it.polimi.codexnaturalis.network.util;
    opens it.polimi.codexnaturalis.network.util to com.google.gson, javafx.fxml;
    exports it.polimi.codexnaturalis.view.GUI to javafx.graphics;
    exports it.polimi.codexnaturalis.network.util.networkMessage;
    opens it.polimi.codexnaturalis.network.util.networkMessage to com.google.gson, javafx.fxml;
    exports it.polimi.codexnaturalis.view.VirtualModel.Hand;
    opens it.polimi.codexnaturalis.view.VirtualModel.Hand to com.google.gson, javafx.fxml;
    exports it.polimi.codexnaturalis.utils.jsonAdapter;
    opens it.polimi.codexnaturalis.utils.jsonAdapter to com.google.gson, javafx.fxml;
}
