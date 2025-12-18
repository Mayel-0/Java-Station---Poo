package com.example.boutique;

/**
 * Vendeur : encaisse les clients et peut appliquer des promos.
 */
public class Vendeur extends Employe {
    public Vendeur(String nom, int id) { super(nom, id); }

    @Override
    public String sePresenter() {
        return String.format("Vendeur %s (id:%d)", getNom(), getId());
    }

    /**
     * Encaisse un client : vérifie le solde, vide son panier, et ajoute le montant à la caisse du magasin.
     */
    public boolean encaisser(Client c) {
        if (c == null || getMagasin() == null) return false;
        double total = c.getPanier().calculerTotal();
        if (c.payer(total)) {
            getMagasin().ajouterDansCaisse(total);
            c.viderPanier();
            return true;
        }
        return false;
    }

    public void appliquerPromo(Jeu j, double pourcentage) {
        if (j != null) j.appliquerPromo(pourcentage);
    }
}

