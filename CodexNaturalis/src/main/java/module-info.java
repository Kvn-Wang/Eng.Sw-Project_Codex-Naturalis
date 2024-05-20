module it.polimi.codexnaturalis {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;

    exports it.polimi.codexnaturalis.model.shop;
    opens it.polimi.codexnaturalis.model.shop to javafx.fxml;
    exports it.polimi.codexnaturalis.model.enumeration;
    opens it.polimi.codexnaturalis.model.enumeration to javafx.fxml;
    exports it.polimi.codexnaturalis.model.shop.card;
    opens it.polimi.codexnaturalis.model.shop.card to javafx.fxml;
    exports it.polimi.codexnaturalis.model.player;
    opens it.polimi.codexnaturalis.model.player to javafx.fxml;
    exports it.polimi.codexnaturalis.model.mission;
    opens it.polimi.codexnaturalis.model.mission to javafx.fxml;
    exports it.polimi.codexnaturalis.model.game;
    opens it.polimi.codexnaturalis.model.game to javafx.fxml;
    exports it.polimi.codexnaturalis.controller;
    opens it.polimi.codexnaturalis.controller to javafx.fxml;
    exports it.polimi.codexnaturalis.network.rmi;
    opens it.polimi.codexnaturalis.network.lobby to com.google.gson;
    exports it.polimi.codexnaturalis.network.communicationInterfaces;
    exports it.polimi.codexnaturalis.network.util;
    opens it.polimi.codexnaturalis.network.util to javafx.fxml;
    exports it.polimi.codexnaturalis.GUI to javafx.graphics;
}
