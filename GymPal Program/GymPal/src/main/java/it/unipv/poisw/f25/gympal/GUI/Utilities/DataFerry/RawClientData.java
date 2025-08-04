package it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry;

import java.time.LocalDate;

public class RawClientData {
	
    public String nome;
    public String cognome;
    public String codiceFiscale;
    public String contatto;
    public String sesso;
    public LocalDate dataNascita;
    
    //----------------------------------------------------------------

    public RawClientData(String nome, String cognome, String codiceFiscale,
                         String contatto, String sesso, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.contatto = contatto;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
    }
    
    //----------------------------------------------------------------

}
