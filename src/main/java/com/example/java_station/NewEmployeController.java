package com.example.java_station;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewEmployeController {
    protected Magasin magasin; // référence vers le magasin

    @FXML
    protected TextField nameField;

    @FXML
    protected TextField ageField;

    @FXML
    protected Label statusLabel;


    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    @FXML
    protected void onCreateEmploye() {
        String name = nameField.getText();
        String ageText = ageField.getText();

        if (name == null || name.isBlank()) {
            statusLabel.setText("Nom obligatoire");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Âge invalide");
            return;
        }

        if (age < 18) {
            statusLabel.setText("Votre employé est trop jeune.");
            return;
        }
        if (age > 64) {
            statusLabel.setText("Votre employé est à la retraite.");
            return;
        }

        if (magasin != null) {
            magasin.addEmploye();
        }

        Employe emp = new Employe(name, age);
        statusLabel.setText("Employé créé : " + emp.getName());
        nameField.clear();
        ageField.clear();
    }
}
