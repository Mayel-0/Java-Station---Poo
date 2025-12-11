package com.example.java_station;

import javafx.fxml.FXML;

public class MainController {

    // JavaFX va injecter automatiquement le contrôleur de Magasin.fxml ici
    @FXML
    private Magasin magasinController;

    // et le contrôleur de NewEmploye.fxml ici
    @FXML
    private NewEmployeController newEmployeController;

    @FXML
    private void initialize() {
        // on donne au NewEmployeController une référence vers le magasin
        newEmployeController.setMagasin(magasinController);
    }
}
