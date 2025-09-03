package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;

public class AppuntamentiHandler {
	
    private IDatiCellaCalendarioDTO dto;
    private List<AppuntamentoPT> tuttiAppuntamenti;
    
    //----------------------------------------------------------------

    public AppuntamentiHandler(IDatiCellaCalendarioDTO dto, 
    						   List<AppuntamentoPT> tuttiAppuntamenti) {
    	
        this.dto = dto;
        this.tuttiAppuntamenti = tuttiAppuntamenti;
                                
    }
    
    //----------------------------------------------------------------

    public void caricaAppuntamentiPerDataEOraEMinuti(int minuti) {

        List<String> daMostrare = new ArrayList<>();
        int oraMinuti = dto.getOra() * 60 + minuti;

        for (AppuntamentoPT a : tuttiAppuntamenti) {

            if (a.getData().equals(dto.getData()) &&
            		
                isFasciaOrariaValida(a.getFasciaOraria()) &&
                fasciaContieneOrario(a.getFasciaOraria(), oraMinuti)) {
                
                daMostrare.add("Cliente: " + a.getCf() + " <<=>> PT: " + a.getStaffId());
            }
            
        }

        dto.getAppuntamentiPT().clear();
        dto.getAppuntamentiPT().addAll(daMostrare);
        
    }

    
    //----------------------------------------------------------------

    /* Verifica che l'orario della cella (espresso in minuti cada all'interno dell'in-
     * tervallo specificato dalla fascia oraria.*/
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

    /* Verifica che le stringhe delle fasce orarie (es: 9:00 - 10:00) siano formate
     * nel modo corretto.*/
    private boolean isFasciaOrariaValida(String fasciaOraria) {
    	
        if (fasciaOraria == null || !fasciaOraria.contains("-")) return false;
        return fasciaOraria.matches("^([01]?\\d|2[0-3]):[0-5]\\d-([01]?\\d|2[0-3]):[0-5]\\d$");
        
    }
    
    //----------------------------------------------------------------

}
