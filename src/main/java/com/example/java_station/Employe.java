package com.example.java_station;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Employe extends Magasin{
    private int Id;
    private final String Name;
    private final int Age;

    public Employe(String name, int age) {
        this.Name = name;
        this.Age = age;
        this.Employe++;
    }

    public String getName() { return this.Name; }
    public int getAge() { return this.Age; }

    public void sePresenter(Employe cible) {
        IO.println("=----------------------=");
        IO.println("");
        IO.println("Je un employer");
        IO.println("");
        IO.println("je m'appele " + cible.Name + " j'ai " +cible.Age + " ans");
        IO.println("je suis un employer modele");
        IO.println("");
        IO.println("=----------------------=");
    }
}
