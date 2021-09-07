package com.example.pfe_dwm;

public class Tache {

    public  int id;
    public String user_name;
    public String email;
    public  int id_user;
    public String date;
    public String id_creneaux;

    public Tache(String date,String id_creneaux) {
        this.date = date;
        this.id_creneaux= id_creneaux;
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

    public String getUser_name() {
        return user_name;
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
