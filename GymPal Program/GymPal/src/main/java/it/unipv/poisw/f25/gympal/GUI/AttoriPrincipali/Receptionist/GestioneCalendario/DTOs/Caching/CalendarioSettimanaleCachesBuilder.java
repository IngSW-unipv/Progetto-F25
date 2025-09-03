package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.Caching;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.DatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.DipendentiInfosDTO;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.IDipendentiInfosDTO;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.Handlers.DTOHandlerCalendarioSettimanale;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.Handlers.DTOHandlerDipendentiInfos;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class CalendarioSettimanaleCachesBuilder {

    private final ICalendarioFacadeService calendarioFacade;

    private List<AppuntamentoPT> appuntamenti;
    private Map<LocalDate, List<SessioneCorso>> corsiCache;
    private Map<LocalDate, List<Turno>> turniCache;
    private Map<LocalDate, List<Calendario>> eventiCache;
    
    //----------------------------------------------------------------

    public CalendarioSettimanaleCachesBuilder(ICalendarioFacadeService calendarioFacade) {
    	
        if (calendarioFacade == null) {
        	
            throw new IllegalArgumentException("Il facade non può essere null.");
            
        }
        
        this.calendarioFacade = calendarioFacade;
        
    }
    
    //----------------------------------------------------------------

    public List<DatiCellaCalendarioDTO> build() {
    	
        LocalDate lunedi = LocalDate.now().with(DayOfWeek.MONDAY);
        IDipendentiInfosDTO infoDip = caricaInfoDipendenti();

        appuntamenti = new ArrayList<>();
        corsiCache = new HashMap<>();
        turniCache = new HashMap<>();
        eventiCache = new HashMap<>();

        caricaAppuntamenti(infoDip);
        precaricaCorsi(lunedi);
        precaricaTurni(lunedi);
        precaricaEventi(lunedi);

        return costruisciDTOs(lunedi);
        
    }
    
    //----------------------------------------------------------------

    public Map<LocalDate, List<SessioneCorso>> getCorsiCache() { return corsiCache; }

    public Map<LocalDate, List<Turno>> getTurniCache() { return turniCache; }

    public Map<LocalDate, List<Calendario>> getEventiCache() { return eventiCache; }

    public List<AppuntamentoPT> getAppuntamenti() { return appuntamenti; }
    
    //----------------------------------------------------------------

    private IDipendentiInfosDTO caricaInfoDipendenti() {
    	
        IDipendentiInfosDTO info = new DipendentiInfosDTO();
        DTOHandlerDipendentiInfos handler = new DTOHandlerDipendentiInfos(
                calendarioFacade.getRetrieveDipendentiService()
        );
        
        handler.estraiIDsDipendenti(info);
        return info;
        
    }
    
    //----------------------------------------------------------------

    private void caricaAppuntamenti(IDipendentiInfosDTO info) {
    	
        appuntamenti = new ArrayList<>();
        for (String id : info.getStaffIDs()) {
            appuntamenti.addAll(calendarioFacade.getAppuntamentiPT("", id));
            
        }
        
    }
    
    //----------------------------------------------------------------

    private void precaricaCorsi(LocalDate lunedi) {
    	
        for (int i = 0; i < 7; i++) {
            LocalDate data = lunedi.plusDays(i);
            List<SessioneCorso> sessioni = calendarioFacade.getSessioniDisponibili("", data, data);
            corsiCache.put(data, sessioni != null ? sessioni : new ArrayList<>());
            
        }
        
    }
    
    //----------------------------------------------------------------

    private void precaricaTurni(LocalDate lunedi) {
    	
        for (int i = 0; i < 7; i++) {
        	
            LocalDate data = lunedi.plusDays(i);
            List<Turno> turni = calendarioFacade.getTurniByRange(data, data);
            turniCache.put(data, turni != null ? turni : new ArrayList<>());
            
        }
        
    }
    
    //----------------------------------------------------------------

    private void precaricaEventi(LocalDate lunedi) {
    	
        for (int i = 0; i < 7; i++) {
        	
            LocalDate data = lunedi.plusDays(i);
            List<Calendario> eventi = calendarioFacade.findEventiByDate(data);
            eventiCache.put(data, eventi != null ? eventi : new ArrayList<>());
            
        }
        
    }
    
    //----------------------------------------------------------------

    private List<DatiCellaCalendarioDTO> costruisciDTOs(LocalDate lunedi) {
    	
        List<DatiCellaCalendarioDTO> lista = new ArrayList<>();

        for (int giorno = 0; giorno < 7; giorno++) {
        	
            LocalDate data = lunedi.plusDays(giorno);

            for (int ora = 8; ora <= 20; ora++) {
            	
                for (int minuti : new int[]{0, 30}) {
                	
                    DatiCellaCalendarioDTO dto = new DatiCellaCalendarioDTO(
                            data, ora, minuti,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>());

                    new DTOHandlerCalendarioSettimanale(dto, calendarioFacade,
                            appuntamenti, corsiCache, turniCache, eventiCache)
                            .popolaDatiCella(minuti);

                    lista.add(dto);
                    
                }
                
            }
            
        }
        return lista;
    }
    
    //----------------------------------------------------------------
    
}
