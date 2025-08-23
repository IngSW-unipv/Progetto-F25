package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

/**
 * Handler per la logica di validazione della disponibilità del personale (es. PT).
 * Isolato dal DTO. Usato dal Coordinatore.
 */
public class TurniHandler {

    private ICalendarioFacadeService calendarioFacade;
    private IDatiCellaCalendarioDTO dto;
    
    private Map<LocalDate, List<Turno>> turniCache;

    //----------------------------------------------------------------
    
    public TurniHandler(ICalendarioFacadeService calendarioFacade) {
        this.calendarioFacade = calendarioFacade;
    }

    //----------------------------------------------------------------

    public TurniHandler(IDatiCellaCalendarioDTO dto,
                        ICalendarioFacadeService calendarioFacade,
                        Map<LocalDate, List<Turno>> turniCache) {
    	
        this.dto = dto;
        this.calendarioFacade = calendarioFacade;
        this.turniCache = turniCache;
        
    }

    //----------------------------------------------------------------
    
    public void caricaTurniPerDataEOraEMinuti(int minuti) {
    	
        LocalDate data = dto.getData();
        int oraMinuti = dto.getOra() * 60 + minuti;

        List<Turno> turniGiornalieri = turniCache.computeIfAbsent(data, d ->
        
            calendarioFacade.getTurniByRange(d, d)
            
        );

        dto.getTurni().clear();

        for (Turno t : turniGiornalieri) {
        	
            String turnoInfo = estraiTurnoPerMinuti(t, oraMinuti);
            
            if (turnoInfo != null && !turnoInfo.isEmpty()) {
            	
                dto.getTurni().add("<html>Turno: <br>" + turnoInfo + "</html>");
                
            }
            
        }
        
    }
    
    //----------------------------------------------------------------

    public void caricaTurniPerDataEOra() {
    	
        LocalDate data = dto.getData();
        int ora = dto.getOra();

        List<Turno> turniGiornalieri = turniCache.computeIfAbsent(data, d ->
            calendarioFacade.getTurniByRange(d, d)
        );

        dto.getTurni().clear();

        for (Turno t : turniGiornalieri) {
        	
            String turnoInfo = estraiTurnoPerOra(t, ora);
            
            if (turnoInfo != null && !turnoInfo.isEmpty()) {
            	
                dto.getTurni().add("Turno: " + turnoInfo);
                
            }
            
        }
        
    }
    
    //----------------------------------------------------------------
    
    public boolean esistePersonaleDisponibile(LocalDate giorno, int ora) {
    	
        List<Turno> turni = calendarioFacade.getTurniByRange(giorno, giorno);

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
    
    private String estraiTurnoPerMinuti(Turno turno, int minuti) {
        if (minuti >= 8 * 60 && minuti < 13 * 60) {
        	
            return " - Reception MAT: " + safeString(turno.getRecMat()) + "<br>" 
                 + " - PT MAT: " + safeString(turno.getPtMat());
            
        }

        if (minuti >= 13 * 60 && minuti < 21 * 60) {
        	
            return " - Reception POM: " + safeString(turno.getRecPom()) + "<br>"
                 + " - PT POM: " + safeString(turno.getPtPom());
            
        }

        return null;
    }

    //----------------------------------------------------------------

    private String estraiTurnoPerOra(Turno turno, int ora) {
    	
        if (oraInFascia(ora, 8 * 60, 13 * 60)) {
        	
            return "Reception: " + safeString(turno.getRecMat()) + ", PT: " + safeString(turno.getPtMat());
        }

        if (oraInFascia(ora, 13 * 60, 21 * 60)) {
        	
            return "Reception: " + safeString(turno.getRecPom()) + ", PT: " + safeString(turno.getPtPom());
        }

        return null;
        
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
