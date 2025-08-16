package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

/**
 * Handler per il caricamento e la gestione degli eventi generici
 * visualizzati all'interno della cella del calendario.
 */
public class EventiHandler {
	
    private final IDatiCellaCalendarioDTO dto;
    private final ICalendarioFacadeService calendarioFacade;
    
    private final Map<LocalDate, List<Calendario>> eventiCache;

    //----------------------------------------------------------------

    public EventiHandler(IDatiCellaCalendarioDTO dto, 
                         ICalendarioFacadeService calendarioFacade,
                         Map<LocalDate, List<Calendario>> eventiCache) {
    	
        this.dto = dto;
        this.calendarioFacade = calendarioFacade;
        this.eventiCache = eventiCache;
    }

    //----------------------------------------------------------------

    /**
     * Carica eventi per la data e fascia oraria specifica della cella.
     */
    public void caricaEventiPerDataEOraEminuti() {
        
        LocalDate data = dto.getData();
        int oraMinuti = dto.getOra() * 60 + dto.getMinuti();

        // Caching eventi del giorno
        List<Calendario> eventiDelGiorno = eventiCache.computeIfAbsent(data, d ->
            calendarioFacade.findEventiByDate(d)
        );

        List<String> eventi = new ArrayList<>();

        for (Calendario evento : eventiDelGiorno) {
            
            if (evento.getOraInizio() == null || evento.getOraFine() == null) {
            	
                continue;
                
            }

            int inizioEvento = evento.getOraInizio().getHour() * 60 + evento.getOraInizio().getMinute();
            int fineEvento = evento.getOraFine().getHour() * 60 + evento.getOraFine().getMinute();

            if (oraMinuti >= inizioEvento && oraMinuti < fineEvento) {
                eventi.add(evento.getNomeEvento());
            }
            
        }

        dto.getEventiGenerici().clear();
        dto.getEventiGenerici().addAll(eventi);
    }

    //----------------------------------------------------------------
    
}
