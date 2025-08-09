package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class CorsiHandler {
	
    private IDatiCellaCalendarioDTO dto;
    private ICalendarioService calendarioService;
    
    //----------------------------------------------------------------

    public CorsiHandler(IDatiCellaCalendarioDTO dto, 
    					ICalendarioService calendarioService) {
    	
        this.dto = dto;
        this.calendarioService = calendarioService;
        
    }
    
    //----------------------------------------------------------------

    public void caricaCorsiPerDataEOraEMinuti(int minuti) {
    	
        int oraMinuti = dto.getOra() * 60 + minuti;

        List<SessioneCorso> sessioni = calendarioService.findSessioniDisponibili(dto.getData(), dto.getData());

        List<String> corsi = new ArrayList<>();

        for (SessioneCorso s : sessioni) {
        	
            String fascia = s.getFasciaOraria();
            if (!isFasciaOrariaValida(fascia)) {

                continue;
            }
            
            int inizio = parseMinuti(fascia.split("-")[0]);
            int fine = parseMinuti(fascia.split("-")[1]);

            if (oraMinuti >= inizio && oraMinuti < fine) {
            	
                corsi.add(s.getSessioneId());
                /////////////////////////////////////////////////////
                /*System.out.println("ID_CORSO>>> " + s.getSessioneId() 
                				 + " ORE>>> " + s.getFasciaOraria()  
                				 + " DATA>>> " + s.getData());*/
                /////////////////////////////////////////////////////
         
            }
            
        }

        dto.getCorsi().clear();
        dto.getCorsi().addAll(corsi);
        
    }



    //----------------------------------------------------------------
    
    private boolean isFasciaOrariaValida(String fasciaOraria) {
    	
        if (fasciaOraria == null) return false;
        
        String regex = "^([01]?\\d|2[0-3]):[0-5]\\d-([01]?\\d|2[0-3]):[0-5]\\d$";
        return fasciaOraria.matches(regex);
        
    }

    //----------------------------------------------------------------
    
    private int parseMinuti(String orario) {
    	
        String[] parts = orario.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        
    }

    
    //----------------------------------------------------------------    

}
