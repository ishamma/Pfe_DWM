package com.example.pfe_dwm;

public class dataSecretaire {

    public String patient_name;
    public String time;
    public String date_rdv;
    public String cin;

    public dataSecretaire(String patient_name, String time, String date_rdv, String cin) {
        this.patient_name = patient_name;
        this.time = time;
        this.date_rdv = date_rdv;
        this.cin = cin;
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

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCin() {
        return cin;
    }
}
