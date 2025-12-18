package com.example.boutique;

/**
 * Jeu pour PC avec configuration requise.
 */
public class JeuPC extends Jeu {
    private String configRequise;

    public JeuPC(String titre, String type, double prix, String configRequise) {
        super(titre, type, prix);
        this.configRequise = configRequise;
    }

    public String getConfigRequise() { return configRequise; }
    public void setConfigRequise(String configRequise) { this.configRequise = configRequise; }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" - Config requise: %s", configRequise);
    }
}

