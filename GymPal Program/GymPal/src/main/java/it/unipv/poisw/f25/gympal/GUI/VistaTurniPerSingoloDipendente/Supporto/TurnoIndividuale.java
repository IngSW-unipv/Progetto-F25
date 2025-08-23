package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto;

import java.time.LocalDate;

public class TurnoIndividuale {
	
	private LocalDate data;
    private String ruolo;
    private final String staffId;
    
    //------------------------------------------------------------

    public TurnoIndividuale(LocalDate data, String ruolo, String staffId) {
    	
        this.data = data;
        this.ruolo = ruolo;
        this.staffId = staffId;
        
    }
    
    //------------------------------------------------------------

    public LocalDate getData() {
    	
        return data;
        
    }
    
    //------------------------------------------------------------

    public String getRuolo() {
    	
        return ruolo;
        
    }
    
    //------------------------------------------------------------
    
    public String getStaffId() {
    	
        return staffId;
        
    }
    
    //------------------------------------------------------------

}
