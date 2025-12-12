package com.example.java_station;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private Magasin magasinController;          // fx:id="magasin"

    @FXML
    private NewEmployeController newEmployeController; // fx:id="newEmploye"

    @FXML
    private Client clientsController;           // fx:id="clients"

    @FXML
    private void initialize() {
        newEmployeController.setMagasin(magasinController);
        clientsController.setMagasin(magasinController);
    }
}
