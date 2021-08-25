package com.example.pfe_dwm;

public class Tache {

    public  int id;
    public String user_name;
    public String email;
    public  int id_user;

    public Tache(String user_name) {
        this.user_name = user_name;
    }

    public int getId() {
        return id;
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



}
