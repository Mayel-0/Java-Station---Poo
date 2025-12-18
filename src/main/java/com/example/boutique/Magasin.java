package com.example.boutique;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le magasin (contrôleur principal).
 */
public class Magasin {
    private String nom;
    private String adresse;
    private final ArrayList<Jeu> jeuxEnStock;
    private final ArrayList<Employe> employes;
    private double caisse;

    public Magasin(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.jeuxEnStock = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.caisse = 0.0;
    }

    public void ajouterJeu(Jeu j) { if (j != null) jeuxEnStock.add(j); }
    public void supprimerJeu(Jeu j) { jeuxEnStock.remove(j); }

    public void embaucherEmploye(Employe e) {
        if (e != null && !employes.contains(e)) {
            employes.add(e);
            e.setMagasin(this);
        }
    }

    public void licencierEmploye(Employe e) {
        if (e != null && employes.remove(e)) {
            e.setMagasin(null);
        }
    }

    public String afficherStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stock du magasin ").append(nom).append(":\n");
        if (jeuxEnStock.isEmpty()) {
            sb.append("  (vide)\n");
        } else {
            for (int i = 0; i < jeuxEnStock.size(); i++) {
                Jeu j = jeuxEnStock.get(i);
                sb.append(String.format("  %d. %s\n", i + 1, j.getDetails()));
            }
        }
        return sb.toString();
    }

    public double getCaisse() { return caisse; }
    public void ajouterDansCaisse(double montant) { if (montant > 0) caisse += montant; }

    public List<Jeu> getJeuxEnStock() { return new ArrayList<>(jeuxEnStock); }
    public List<Employe> getEmployes() { return new ArrayList<>(employes); }

    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
}

