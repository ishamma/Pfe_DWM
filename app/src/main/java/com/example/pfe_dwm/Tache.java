package com.example.pfe_dwm;

public class Tache {

    public  int id;
    public String nom;
    public String email;
    public  int id_user;
    public String date;
    public String cin;
    public String id_creneaux;


    public Tache(String date, String id_creneaux, int id, String nom, String cin) {
        this.date = date;
        this.id_creneaux= id_creneaux;
        this.id=id;
        this.nom =nom;
        this.cin=cin;
    }
    public String getNom() {
        return nom;
    }

    public String getCin() {
        return cin;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId_creneaux(String id_creneaux) {
        this.id_creneaux = id_creneaux;
    }

    public String getEmail() {
        return email;
    }


    public int getId_user() {
        return id_user;
    }
    public String getDate(){
        return date;
    }
    public String getId_creneaux(){
        return id_creneaux;
    }


}
