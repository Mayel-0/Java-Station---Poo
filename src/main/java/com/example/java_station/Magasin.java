package com.example.java_station;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Magasin {
    protected boolean Open = false;
    protected int Employe = 0;

    @FXML
    protected Label OpenText;
    @FXML
    private Button btnMagasin;
    @FXML
    private Label IntEmploye;

    @FXML
    protected void toggleMagasin() {
        if (this.Open == false) {
            this.Open = true;
            btnMagasin.setText("Close");
            OpenText.setText("The shop is Open ! " + this.Open);
        } else if (this.Open == true) {
            this.Open = false;
            btnMagasin.setText("Open");
            OpenText.setText("The Shop is Close! " + this.Open);
        } else {
            btnMagasin.setText("ERREUR");
            OpenText.setText("ERREUR bouton");
        }
    }

    @FXML
    protected void initialize() {
        IntEmploye.setText("il y a " + this.Employe + " Employer");
    }

    public void addEmploye() {
        Employe++;
        updateEmployeLabel();
    }

    protected void updateEmployeLabel() {
        if (IntEmploye != null) {
            IntEmploye.setText("il y a " + Employe + " Employé(s)");
        }
    }

    // Getter Setter
    public boolean isOpen() {
        return this.Open;
    }

    public void setOpen(boolean open) {
        Open = open;
    }

    public int getEmploye() {
        return Employe;
    }

    private void setEmploye(int employe) {
        Employe = employe;
    }
}
