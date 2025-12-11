package com.example.java_station;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Employe extends Magasin{
    private final String Name;
    private final int Age;

    public Employe(String name, int age) {
        this.Name = name;
        this.Age = age;
        this.Employe++;
    }

    public String getName() { return this.Name; }
    public int getAge() { return this.Age; }
}
