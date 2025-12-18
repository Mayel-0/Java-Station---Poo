package com.example.boutique;

/**
 * Jeu destiné aux consoles.
 */
public class JeuConsole extends Jeu {
    private String console;

    public JeuConsole(String titre, String type, double prix, String console) {
        super(titre, type, prix);
        this.console = console;
    }

    public String getConsole() { return console; }
    public void setConsole(String console) { this.console = console; }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" - Console: %s", console);
    }
}

