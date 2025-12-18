package com.example.boutique;

/**
 * Manager : peut ajouter ou retirer des jeux via le magasin.
 */
public class Manager extends Employe {
    public Manager(String nom, int id) { super(nom, id); }

    @Override
    public String sePresenter() {
        return String.format("Manager %s (id:%d)", getNom(), getId());
    }

    public void ajouterNouveauJeu(Jeu j, Magasin m) {
        if (m != null && j != null) m.ajouterJeu(j);
    }

    public void retirerJeuDuRayon(Jeu j, Magasin m) {
        if (m != null && j != null) m.supprimerJeu(j);
    }
}

