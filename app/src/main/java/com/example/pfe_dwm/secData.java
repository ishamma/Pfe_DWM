package com.example.pfe_dwm;

public class secData {
    public  int id_sec;
    public String nom_sec;
    public String tele;
    public String cin_sec;

    public secData(int id_sec, String nom_sec, String tele, String cin_sec) {
        this.id_sec = id_sec;
        this.nom_sec = nom_sec;
        this.tele = tele;
        this.cin_sec = cin_sec;
    }

    public int getId_sec() {
        return id_sec;
    }

    public void setId_sec(int id_sec) {
        this.id_sec = id_sec;
    }

    public String getNom_sec() {
        return nom_sec;
    }

    public void setNom_sec(String nom_sec) {
        this.nom_sec = nom_sec;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getCin_sec() {
        return cin_sec;
    }

    public void setCin_sec(String cin_sec) {
        this.cin_sec = cin_sec;
    }
}
