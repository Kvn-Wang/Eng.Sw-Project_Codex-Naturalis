package it.polimi.codexnaturalis.GUI;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Map extends Gui {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Person> tableView = new TableView<>();

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);

        tableView.getItems().add(new Person("John", "Doe"));
        tableView.getItems().add(new Person("Jane", "Smith"));

        StackPane root = new StackPane(tableView);
        Scene scene = new Scene(root, 800, 600);

        addZoomFunctionality(tableView);

        primaryStage.setTitle("Zoomable TableView");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addZoomFunctionality(TableView<?> tableView) {
        tableView.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = (deltaY > 0) ? 1.1 : 0.9;
            tableView.setScaleX(tableView.getScaleX() * scaleFactor);
            tableView.setScaleY(tableView.getScaleY() * scaleFactor);
        });
    }

    public static class Person {
        private final String firstName;
        private final String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}