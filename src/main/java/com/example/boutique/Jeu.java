package com.example.boutique;

/**
 * Classe abstraite représentant un jeu (produit) dans la boutique.
 */
public abstract class Jeu implements Descriptible {
    private String titre;
    private String type; // ex: "RPG", "Action"
    private double prix;
    private boolean enPromo;

    public Jeu(String titre, String type, double prix) {
        this.titre = titre;
        this.type = type;
        this.prix = Math.max(0.0, prix);
        this.enPromo = false;
    }

    // getters et setters
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = Math.max(0.0, prix); }

    public boolean estEnPromo() { return enPromo; }
    protected void setEnPromo(boolean enPromo) { this.enPromo = enPromo; }

    /**
     * Applique une promotion en pourcentage (ex: 20 => -20%).
     * Implémentation simple: réduit le prix et marque enPromo.
     */
    public void appliquerPromo(double pourcentage) {
        if (pourcentage <= 0) return;
        double factor = Math.max(0.0, 1.0 - pourcentage / 100.0);
        this.prix = this.prix * factor;
        this.enPromo = true;
    }

    @Override
    public String getDetails() {
        return String.format("%s (%s) - %.2f€%s", titre, type, prix, enPromo ? " [PROMO]" : "");
    }
}

