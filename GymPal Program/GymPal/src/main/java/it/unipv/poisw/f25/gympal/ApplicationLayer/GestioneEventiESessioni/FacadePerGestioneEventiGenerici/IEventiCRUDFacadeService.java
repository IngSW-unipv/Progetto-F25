package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

public interface IEventiCRUDFacadeService {
	
	boolean creaEvento(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, 
					   LocalTime oraFine, String messaggio, String destinatario);
	
    //---------------------------------------------------------------

	boolean modificaEvento(String nomeEventoOriginale, LocalDate dataEventoOriginale,
                		   LocalTime oraInizioOriginale, LocalTime oraFineOriginale,
                		   String nuovoNomeEvento, LocalDate nuovaData,
                		   LocalTime nuovaOraInizio, LocalTime nuovaOraFine,
                		   String nuovoMessaggio, String nuovoDestinatario);
	
    //---------------------------------------------------------------

	Calendario findEvento(String nomeEvento, LocalDate dataEvento, 
						  LocalTime oraInizio, LocalTime oraFine);
	
    //---------------------------------------------------------------

	List<Calendario> findEventiByData(LocalDate data);
	
    //---------------------------------------------------------------

	List<Calendario> findEventiByRange(LocalDate dataInizio, LocalDate dataFine);
	
    //---------------------------------------------------------------

	boolean cancellaEvento(String nomeEvento, LocalDate dataEvento, 
						   LocalTime oraInizio, LocalTime oraFine);
	
    //---------------------------------------------------------------

	boolean pulisciEventiVecchi();
	
    //---------------------------------------------------------------

}
