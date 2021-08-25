package com.example.pfe_dwm;

public class MyData {

    private int id;
    private String user_name,email;

    public MyData(int id, String user_name, String email) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}