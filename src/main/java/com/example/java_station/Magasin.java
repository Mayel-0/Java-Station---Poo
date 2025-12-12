package com.example.java_station;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class Magasin {
    protected boolean Open = false;
    protected ArrayList<jeu> jeuxEnStock = new ArrayList<>();
    protected ArrayList<Employe> ListEmploye = new ArrayList<>();
    protected int Client = 0;

    public void embaucherEmploye(Employe cible) {
        ListEmploye.add(cible);
        updateEmployeLabel();
    }

    public void LicencierEmploye(Employe cible) {
        ListEmploye.remove(cible);
        updateEmployeLabel();
    }

    public void AjouterJeu(jeu Jeux) {
        jeuxEnStock.add(Jeux);
    }

    public void SupprimerJeux(jeu Jeux) {
        jeuxEnStock.remove(Jeux);
    }

    public void addClient() {
        Client++;
        updateClientLabel();
    }

    public void EmployeListe() {
        IO.println("Voici la liste des Employer " );
        IO.println(this.ListEmploye);
    }

    public void JeuxListe() {
        IO.println("voici la liste des jeux " );
        IO.println(this.jeuxEnStock);
    }

    protected void updateClientLabel() {
        if (IntClient != null) {
            IntClient.setText("il y a " + Client + " Client(s)");
        }
    }

    protected void updateEmployeLabel() {
        if (IntEmploye != null) {
            IntEmploye.setText("il y a "  + " Employé(s)");
        }
    }

    // Getter Setter
    public boolean isOpen() {
        return this.Open;
    }

    public void setOpen(boolean open) {
        Open = open;
    }



   @FXML
    protected Label OpenText;
    @FXML
    private Button btnMagasin;
    @FXML
    private Label IntEmploye;
    @FXML
    protected Label IntClient;

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
        IntEmploye.setText("il y a "  + " Employer");
        IntClient.setText("il y a " + this.Client + " Client");
    }
}
