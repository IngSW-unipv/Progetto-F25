package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.AppuntamentiHandler;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.CorsiHandler;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.EventiHandler;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.TurniHandler;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class DTOHandlerCalendarioSettimanale {

    private IDatiCellaCalendarioDTO dto;
    private ICalendarioFacadeService calendarioFacade;

    /* CACHING DATI - RIDUZIONE TEMPI CALCOLO */
    private List<AppuntamentoPT> tuttiAppuntamenti;
    private Map<LocalDate, List<SessioneCorso>> corsiCache;
    private Map<LocalDate, List<Turno>> turniCache;
    private Map<LocalDate, List<Calendario>> eventiCache;

    //----------------------------------------------------------------

    public DTOHandlerCalendarioSettimanale(IDatiCellaCalendarioDTO dto,
                                           ICalendarioFacadeService calendarioFacade,
                                           List<AppuntamentoPT> tuttiAppuntamenti,
                                           Map<LocalDate, List<SessioneCorso>> corsiCache,
                                           Map<LocalDate, List<Turno>> turniCache,
                                           Map<LocalDate, List<Calendario>> eventiCache) {

        this.dto = dto;
        this.calendarioFacade = calendarioFacade;

        this.tuttiAppuntamenti = tuttiAppuntamenti;
        this.corsiCache = corsiCache;
        this.turniCache = turniCache;
        this.eventiCache = eventiCache;
    }

    //----------------------------------------------------------------

    public void popolaDatiCella(int minuti) {
        // Turni staff
        TurniHandler turniHandler = new TurniHandler(dto, calendarioFacade, turniCache);
        turniHandler.caricaTurniPerDataEOraEMinuti(minuti);

        // Corsi di gruppo
        CorsiHandler corsiHandler = new CorsiHandler(dto, calendarioFacade, corsiCache);
        corsiHandler.caricaCorsiPerDataEOraEMinuti(minuti);

        // Appuntamenti PT
        AppuntamentiHandler appuntamentiHandler = new AppuntamentiHandler(dto, tuttiAppuntamenti);
        appuntamentiHandler.caricaAppuntamentiPerDataEOraEMinuti(minuti);

        // Eventi generici
        EventiHandler eventiHandler = new EventiHandler(dto, calendarioFacade, eventiCache);
        eventiHandler.caricaEventiPerDataEOraEminuti();
    }

    //----------------------------------------------------------------

}
