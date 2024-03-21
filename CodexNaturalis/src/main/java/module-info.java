module it.polimi.codexnaturalis {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.codexnaturalis to javafx.fxml;
    exports it.polimi.codexnaturalis;
}