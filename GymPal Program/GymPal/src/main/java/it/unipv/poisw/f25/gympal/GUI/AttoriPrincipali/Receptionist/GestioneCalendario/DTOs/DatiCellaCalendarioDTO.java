package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs;

import java.time.LocalDate;
import java.util.List;

public class DatiCellaCalendarioDTO implements IDatiCellaCalendarioDTO {

    private LocalDate data;
    private int ora;
    private int minuti; 

    private List<String> corsi;
    private List<String> appuntamentiPT;
    private List<String> eventiGenerici;
    private List<String> turni;

    //----------------------------------------------------------------

    public DatiCellaCalendarioDTO(LocalDate data,
                                  int ora,
                                  int minuti, 
                                  List<String> corsi,
                                  List<String> appuntamentiPT,
                                  List<String> eventiGenerici,
                                  List<String> turni) {

        this.data = data;
        this.ora = ora;
        this.minuti = minuti;
        this.corsi = corsi;
        this.appuntamentiPT = appuntamentiPT;
        this.eventiGenerici = eventiGenerici;
        this.turni = turni;
        
    }

    //----------------------------------------------------------------

    @Override
    public LocalDate getData() {
    	
        return data;
        
    }

    //----------------------------------------------------------------

    @Override
    public int getOra() {
    	
        return ora;
        
    }

    //----------------------------------------------------------------

    @Override
    public int getMinuti() {
    	
        return minuti;
        
    }

    //----------------------------------------------------------------

    @Override
    public List<String> getCorsi() {
    	
        return corsi;
        
    }

    //----------------------------------------------------------------

    @Override
    public List<String> getAppuntamentiPT() {
    	
        return appuntamentiPT;
        
    }

    //----------------------------------------------------------------

    @Override
    public List<String> getEventiGenerici() {
    	
        return eventiGenerici;
        
    }

    //----------------------------------------------------------------
    
    @Override
    public List<String> getTurni(){
    	
    	return turni;
    	
    }
    
    //----------------------------------------------------------------

    @Override
    public String toString() {
    	
        return "DatiCellaCalendarioDTO {" +
               "\n  data=" + data +
               "\n  ora=" + ora +
               "\n  minuti=" + minuti +
               "\n  corsi=" + corsi +
               "\n  appuntamentiPT=" + appuntamentiPT +
               "\n  eventiGenerici=" + eventiGenerici +
               "\n}";
        
    }

    //----------------------------------------------------------------

}
