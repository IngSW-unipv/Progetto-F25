package it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean;

import java.time.LocalDate;

public class AppuntamentoPT {

    private String cf;
    private String staffId;
    private LocalDate data;
    private String fasciaOraria;

    // Costruttore di default
    public AppuntamentoPT() {
    }

    // Costruttore parametrizzato
    public AppuntamentoPT(String cf, String staffId, LocalDate data, String fasciaOraria) {
        this.cf = cf;
        this.staffId = staffId;
        this.data = data;
        this.fasciaOraria = fasciaOraria;
    }

    // Getters e setters
    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getFasciaOraria() {
        return fasciaOraria;
    }

    public void setFasciaOraria(String fasciaOraria) {
        this.fasciaOraria = fasciaOraria;
    }

    @Override
    public String toString() {
        return "AppuntamentoPT [cf=" + cf + ", staffId=" + staffId + ", data=" + data + ", fasciaOraria=" + fasciaOraria + "]";
    }
}
