package com.example.boutique;

/**
 * Jeu rétro avec année de sortie.
 */
public class JeuRetro extends Jeu {
    private int anneeSortie;

    public JeuRetro(String titre, String type, double prix, int anneeSortie) {
        super(titre, type, prix);
        this.anneeSortie = anneeSortie;
    }

    public int getAnneeSortie() { return anneeSortie; }
    public void setAnneeSortie(int anneeSortie) { this.anneeSortie = anneeSortie; }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" - Année: %d", anneeSortie);
    }
}

