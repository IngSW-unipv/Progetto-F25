package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class CorsiHandler {

    private IDatiCellaCalendarioDTO dto;
    private ICalendarioFacadeService calendarioFacade;

    /*CACHE*/
    private Map<LocalDate, List<SessioneCorso>> corsiCache;

    //----------------------------------------------------------------

    public CorsiHandler(IDatiCellaCalendarioDTO dto,
                        ICalendarioFacadeService calendarioFacade,
                        Map<LocalDate, List<SessioneCorso>> corsiCache) {

        this.dto = dto;
        this.calendarioFacade = calendarioFacade;
        this.corsiCache = corsiCache;
        
    }

    //----------------------------------------------------------------

    public void caricaCorsiPerDataEOraEMinuti(int minuti) {
    	
        int oraMinuti = dto.getOra() * 60 + minuti;
        LocalDate data = dto.getData();

        /* La lista è costruita una volta, se assente. Altrimenti è consultata
         * in modo diretto, anziché effettuare ulteriori chiamate a database*/
        List<SessioneCorso> sessioni = corsiCache.computeIfAbsent(data, d ->
            calendarioFacade.findSessioniDisponibili(d, d)
        );

        List<String> corsi = new ArrayList<>();

        for (SessioneCorso s : sessioni) {
        	
            String fascia = s.getFasciaOraria();
            
            if (!isFasciaOrariaValida(fascia)) {
            	
                continue;
                
            }

            int inizio = parseMinuti(fascia.split("-")[0]);
            int fine = parseMinuti(fascia.split("-")[1]);

            if (oraMinuti >= inizio && oraMinuti < fine) {
            	
                corsi.add("Corso: " + s.getSessioneId()
                          + " <<=>> Num. Iscritti: " + s.getNumIscritti());
                
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
