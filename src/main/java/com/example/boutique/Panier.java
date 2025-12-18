package com.example.boutique;

import java.util.ArrayList;
import java.util.List;

/**
 * Panier contenant des jeux sélectionnés par le client.
 */
public class Panier {
    private final ArrayList<Jeu> contenu;

    public Panier() {
        this.contenu = new ArrayList<>();
    }

    public void ajouterJeu(Jeu j) {
        if (j != null) contenu.add(j);
    }

    public void retirerJeu(Jeu j) {
        contenu.remove(j);
    }

    public void vider() {
        contenu.clear();
    }

    /**
     * Calcule le total en tenant compte des jeux en promo (prix déjà ajusté par appliquerPromo).
     */
    public double calculerTotal() {
        double total = 0.0;
        for (Jeu j : contenu) {
            total += j.getPrix();
        }
        return total;
    }

    public List<Jeu> getContenu() {
        return new ArrayList<>(contenu);
    }
}

