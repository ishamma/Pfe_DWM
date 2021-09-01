package com.example.pfe_dwm;

public class modificationData {

    public  int id_secretaire;
    public String patient_name;
    public String time;
    public String date_rdv;
    public String cin;

    public modificationData(String patient_name, String time, String date_rdv, String cin) {
        this.patient_name = patient_name;
        this.time = time;
        this.date_rdv = date_rdv;
        this.cin = cin;
    }

    public int getId_secretaire() {
        return id_secretaire;
    }

    public void setId_secretaire(int id_secretaire) {
        this.id_secretaire = id_secretaire;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate_rdv() {
        return date_rdv;
    }

    public void setDate_rdv(String date_rdv) {
        this.date_rdv = date_rdv;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
