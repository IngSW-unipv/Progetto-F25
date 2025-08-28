package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

public class EventiCRUDFacadeService implements IEventiCRUDFacadeService{
	
	private final ICalendarioService calendarioService;

    //---------------------------------------------------------------

    public EventiCRUDFacadeService(ICalendarioService calendarioService) {
    	
        this.calendarioService = calendarioService;
        
    }

    //---------------------------------------------------------------

    @Override
    public boolean creaEvento(String nomeEvento, LocalDate dataEvento, 
    						  LocalTime oraInizio,LocalTime oraFine, 
    						  String messaggio, String destinatario) {

        return calendarioService.creaEventoCalendario(
            nomeEvento, dataEvento, oraInizio, oraFine, messaggio, destinatario);
        
    }

    //---------------------------------------------------------------

    @Override
    public boolean modificaEvento(String nomeEventoOriginale, LocalDate dataEventoOriginale,
                                  LocalTime oraInizioOriginale, LocalTime oraFineOriginale,
                                  String nuovoNomeEvento, LocalDate nuovaData,
                                  LocalTime nuovaOraInizio, LocalTime nuovaOraFine,
                                  String nuovoMessaggio, String nuovoDestinatario) {

        return calendarioService.modificaEventoCalendario(
            nomeEventoOriginale, dataEventoOriginale, oraInizioOriginale, 
            oraFineOriginale, nuovoNomeEvento, nuovaData, nuovaOraInizio, 
            nuovaOraFine, nuovoMessaggio, nuovoDestinatario);
        
    }

    //---------------------------------------------------------------

    @Override
    public Calendario findEvento(String nomeEvento, LocalDate dataEvento,
                                  LocalTime oraInizio, LocalTime oraFine) {

        return calendarioService.findEvento(nomeEvento, dataEvento, 
        								    oraInizio, oraFine);
        
    }

    //---------------------------------------------------------------

    @Override
    public List<Calendario> findEventiByData(LocalDate data) {
    	
        return calendarioService.findEventiByDate(data);
        
    }

    @Override
    public List<Calendario> findEventiByRange(LocalDate dataInizio, 
    										  LocalDate dataFine) {
    	
        return calendarioService.findEventiByRange(dataInizio, dataFine);
        
    }

    //---------------------------------------------------------------

    @Override
    public boolean cancellaEvento(String nomeEvento, LocalDate dataEvento,
                                  LocalTime oraInizio, LocalTime oraFine) {

        return calendarioService.cancellaEventoCalendario(nomeEvento, dataEvento, 
        												  oraInizio, oraFine);
        
    }

    @Override
    public boolean pulisciEventiVecchi() {
    	
        return calendarioService.pulisciEventiVecchi();
        
    }
    
    //---------------------------------------------------------------

}
