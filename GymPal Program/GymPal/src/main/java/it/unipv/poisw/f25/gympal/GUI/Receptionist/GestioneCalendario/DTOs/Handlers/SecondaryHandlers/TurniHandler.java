package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

/**
 * Handler per la logica di validazione della disponibilità del personale (es. PT).
 * Isolato dal DTO. Usato dal Coordinatore.
 */
public class TurniHandler {

    private ICalendarioService calendarioService;
    private IDatiCellaCalendarioDTO dto;

    //----------------------------------------------------------------
    
    public TurniHandler(ICalendarioService calendarioService) {
     
    	this.calendarioService = calendarioService;
    	
    }
    
    //----------------------------------------------------------------

    public TurniHandler(IDatiCellaCalendarioDTO dto, 
    				    ICalendarioService calendarioService) {
    	
        this.dto = dto;
        this.calendarioService = calendarioService;
        
    }

    //----------------------------------------------------------------
    
    public void caricaTurniPerDataEOraEMinuti(int minuti) {
    	
        LocalDate data = dto.getData();
        int oraMinuti = dto.getOra() * 60 + minuti;

        List<Turno> turniGiornalieri = calendarioService.findTurniByRange(data, data);
        
        dto.getEventiGenerici().clear();//Correggere qui: Turni dovrebbero avere una propria lista

        for (Turno t : turniGiornalieri) {
        	
            String turnoInfo = estraiTurnoPerMinuti(t, oraMinuti);
            if (turnoInfo != null && !turnoInfo.isEmpty()) {
                dto.getEventiGenerici().add("Turno: " + turnoInfo);//Correggere qui: Turni dovrebbero avere una propria lista
                
            }
            
        }
        
    }

  //----------------------------------------------------------------
    
    private String estraiTurnoPerMinuti(Turno turno, int minuti) {
    	
        if (minuti >= 8 * 60 && minuti < 13 * 60) {
        	
            return "Reception: " + safeString(turno.getRecMat()) 
            					 + ", PT: " + safeString(turno.getPtMat());
            
        }

        if (minuti >= 13 * 60 && minuti < 21 * 60) {
        	
            return "Reception: " + safeString(turno.getRecPom()) 
            					 + ", PT: " + safeString(turno.getPtPom());
            
        }

        return null;
    }

    
    //----------------------------------------------------------------

    public void caricaTurniPerDataEOra() {
    	
        LocalDate data = dto.getData();
        int ora = dto.getOra();

        List<Turno> turniGiornalieri = calendarioService.findTurniByRange(data, data);

        dto.getEventiGenerici().clear();

        for (Turno t : turniGiornalieri) {
        	
            String turnoInfo = estraiTurnoPerOra(t, ora);
            if (turnoInfo != null && !turnoInfo.isEmpty()) {
            	
                dto.getEventiGenerici().add("Turno: " + turnoInfo);
                
            }
            
        }
        
    }



    //----------------------------------------------------------------

    private String estraiTurnoPerOra(Turno turno, int ora) {

        // Mattina: 08:00 - 13:00
        if (oraInFascia(ora, 8 * 60, 13 * 60)) {
            return "Reception: " + safeString(turno.getRecMat()) + ", PT: " + safeString(turno.getPtMat());
        }

        // Pomeriggio: 13:00 - 21:00
        if (oraInFascia(ora, 13 * 60, 21 * 60)) {
            return "Reception: " + safeString(turno.getRecPom()) + ", PT: " + safeString(turno.getPtPom());
        }

        return null;
    }


    //----------------------------------------------------------------
    
    public boolean esistePersonaleDisponibile(LocalDate giorno, int ora) {
    	
        List<Turno> turni = calendarioService.findTurniByRange(giorno, giorno);

        if (turni.isEmpty()) {
        	
            return false;
            
        }

        Turno turno = turni.get(0); // unico turno

        if (oraInFascia(ora, 8 * 60, 13 * 60)) {
        	
            return turno.getRecMat() != null || turno.getPtMat() != null;
            
        }

        if (oraInFascia(ora, 13 * 60, 21 * 60)) {
        	
            return turno.getRecPom() != null || turno.getPtPom() != null;
            
        }

        return false;
        
    }

    
    //----------------------------------------------------------------
    
    private boolean oraInFascia(int oraCella, int inizio, int fine) {
    	
        int minutiCella = oraCella * 60;
        return minutiCella >= inizio && minutiCella < fine;
        
    }

    //----------------------------------------------------------------
    
    private String safeString(String s) {
    	
        return (s != null && !s.trim().isEmpty()) ? s : "—";
        
    }
    
    //----------------------------------------------------------------

	
}
