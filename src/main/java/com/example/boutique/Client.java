package com.example.boutique;

/**
 * Client possédant un panier et un solde.
 */
public class Client {
    private String nom;
    private double solde;
    private final Panier panier;

    public Client(String nom, double solde) {
        this.nom = nom;
        this.solde = Math.max(0.0, solde);
        this.panier = new Panier();
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getSolde() { return solde; }

    /**
     * Tente de payer le montant ; si suffisant décrémente le solde et retourne true.
     */
    public boolean payer(double montant) {
        if (montant <= 0) return false;
        if (solde >= montant) {
            solde -= montant;
            return true;
        }
        return false;
    }

    public Panier getPanier() { return panier; }

    public void consulterCatalogue(Magasin m) {
        if (m != null) System.out.println(m.afficherStock());
    }

    public void ajouterAuPanier(Jeu j) { panier.ajouterJeu(j); }
    public void retirerDuPanier(Jeu j) { panier.retirerJeu(j); }
    public void viderPanier() { panier.vider(); }
}

