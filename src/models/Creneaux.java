package models;

import java.util.Date;

public class Creneaux {
    private int id;
    private String nom;
    private java.sql.Date date;
    private float heure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public float getHeure() {return heure;}

    public void setHeure(float heure) {
        this.heure = heure;
    }
}
