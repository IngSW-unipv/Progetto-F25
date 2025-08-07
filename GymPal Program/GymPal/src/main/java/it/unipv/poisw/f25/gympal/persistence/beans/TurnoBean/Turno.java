//PRIMARY KEY = data

package it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean;

import java.time.LocalDate;

public class Turno {

    private LocalDate data;
    private String recMat;
    private String recPom;
    private String ptMat;
    private String ptPom;

    // Costruttore di default
    public Turno() {
    }

    // Costruttore parametrizzato
    public Turno(LocalDate data, String recMat, String recPom, String ptMat, String ptPom) {
        this.data = data;
        this.recMat = recMat;
        this.recPom = recPom;
        this.ptMat = ptMat;
        this.ptPom = ptPom;
    }

    // Getters e setters
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getRecMat() {
        return recMat;
    }

    public void setRecMat(String recMat) {
        this.recMat = recMat;
    }

    public String getRecPom() {
        return recPom;
    }

    public void setRecPom(String recPom) {
        this.recPom = recPom;
    }

    public String getPtMat() {
        return ptMat;
    }

    public void setPtMat(String ptMat) {
        this.ptMat = ptMat;
    }

    public String getPtPom() {
        return ptPom;
    }

    public void setPtPom(String ptPom) {
        this.ptPom = ptPom;
    }

    @Override
    public String toString() {
        return "Turno [data=" + data + ", recMat=" + recMat + ", recPom=" + recPom + ", ptMat=" + ptMat + ", ptPom="
                + ptPom + "]";
    }
}
