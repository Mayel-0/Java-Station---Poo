package com.example.java_station;

public class jeu extends Magasin {
    protected String Titre;
    protected double Prix;
    protected String Type;
    protected boolean EnPromo;

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }
}
