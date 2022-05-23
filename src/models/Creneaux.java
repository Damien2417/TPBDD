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

    public Creneaux(int id, String nom, java.sql.Date date, float heure){
        this.id=id;
        this.nom=nom;
        this.date=date;
        this.heure=heure;
    }

    public Creneaux(String date, float heure, String nom){
        this.heure=heure;
        this.date=java.sql.Date.valueOf(date);
        this.nom=nom;
    }
}
