package com.example.boutique;

/**
 * Employé abstrait.
 */
public abstract class Employe {
    private String nom;
    private int id;
    private Magasin magasin;

    public Employe(String nom, int id) {
        this.nom = nom;
        this.id = id;
    }

    public String getNom() { return nom; }
    public int getId() { return id; }

    public Magasin getMagasin() { return magasin; }
    public void setMagasin(Magasin magasin) { this.magasin = magasin; }

    public abstract String sePresenter();
}

