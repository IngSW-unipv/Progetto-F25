package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;

public class AppuntamentiHandler {
	
    private IDatiCellaCalendarioDTO dto;
    private ICalendarioService calendarioService;
    
    //----------------------------------------------------------------

    public AppuntamentiHandler(IDatiCellaCalendarioDTO dto, 
    						   ICalendarioService calendarioService) {
    	
        this.dto = dto;
        this.calendarioService = calendarioService;
        
    }
    
    //----------------------------------------------------------------

    public void caricaAppuntamentiPerDataEOraEMinuti(int minuti) {
    	
        List<AppuntamentoPT> appuntamenti = calendarioService.findLezioniPTByTrainer("ALL");
        List<String> daMostrare = new ArrayList<>();

        int oraMinuti = dto.getOra() * 60 + minuti;

        for (AppuntamentoPT a : appuntamenti) {
        	
            if (a.getData().equals(dto.getData()) && 
            					   fasciaContieneOrario(a.getFasciaOraria(), 
            							   				oraMinuti)) {
            	
                daMostrare.add(a.getCf());
                
            }
            
        }

        dto.getAppuntamentiPT().clear();
        dto.getAppuntamentiPT().addAll(daMostrare);
        
    }
    
    //----------------------------------------------------------------

    private boolean fasciaContieneOrario(String fasciaOraria, int minutiTotali) {
    	
        String[] intervallo = fasciaOraria.split("-");

        int inizio = parseMinuti(intervallo[0]);
        int fine = parseMinuti(intervallo[1]);

        return minutiTotali >= inizio && minutiTotali < fine;
        
    }
    
    //----------------------------------------------------------------

    private int parseMinuti(String orario) {
    	
        String[] parts = orario.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        
    }

    
    //----------------------------------------------------------------


}
