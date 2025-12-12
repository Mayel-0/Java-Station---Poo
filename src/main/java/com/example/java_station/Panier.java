package com.example.java_station;
import java.util.ArrayList;


public class Panier extends Magasin {
    protected ArrayList<jeu> Contenu = new ArrayList<>();

    public void AjouterJeu(jeu Jeux) {
        Contenu.add(Jeux);
    }

    public void SupprimerJeux(jeu Jeux) {
        Contenu.remove(Jeux);
    }

    public void Vider() {
        Contenu.clear();
    }

    public double getTotalPrix() {
        double total = 0;
        for (jeu j : Contenu) {
            total += j.getPrix();
        }
        return total;
    }

    public void GetContenu() {
        IO.println(Contenu);
    }

}
