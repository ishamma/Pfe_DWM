package com.example.pfe_dwm;

public class notifData {
    public int id_notif;
    public String message;
    public String date_notif;

    public notifData(String message, String date_notif) {
        this.message = message;
        this.date_notif = date_notif;
    }

    public void setId_notif(int id_notif) {
        this.id_notif = id_notif;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate_notif(String date_notif) {
        this.date_notif = date_notif;
    }

    public int getId_notif() {
        return id_notif;
    }

    public String getMessage() {
        return message;
    }

    public String getDate_notif() {
        return date_notif;
    }
}
