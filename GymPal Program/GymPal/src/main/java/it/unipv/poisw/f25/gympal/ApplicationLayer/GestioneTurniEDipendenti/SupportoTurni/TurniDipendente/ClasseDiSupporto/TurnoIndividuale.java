package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.ClasseDiSupporto;

import java.time.LocalDate;

public class TurnoIndividuale {
	
	private LocalDate data;
    private String ruolo;
    private String staffId;
    
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
