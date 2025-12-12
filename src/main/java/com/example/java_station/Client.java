package com.example.java_station;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Client extends Magasin {
    private Magasin magasin;

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    @FXML
    protected void AddClient() {
        if (magasin != null) {
            magasin.addClient();
        }
    }
}
