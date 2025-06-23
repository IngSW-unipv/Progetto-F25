package it.unipv.poisw.f25.gympal.persistence;

import java.util.List;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

//Interfaccia per accedere allo strato di persistenza
public interface IPersistenceFacade {

    //Metodi relativi al DAO Cliente
 
	//Recupera tutti i clienti dal database
    List<Cliente> selectAllClienti();

    //Recupera un singolo cliente basato sul suo codice fiscale
    Cliente selectCliente(Cliente cliente);

    //Inserisce un nuovo cliente nel database
    boolean insertCliente(Cliente cliente);

    //Aggiorna i dati di un cliente esistente nel database
    boolean updateCliente(Cliente cliente);

    //Cancella un cliente dal database usando il suo codice fiscale
    boolean deleteCliente(Cliente cliente);


    
    //Metodi relativi al DAO Dipendente
    
    //Recupera tutti i dipendenti dal database
    List<Dipendente> selectAllDipendenti();
    
    //Recupera un singolo dipendente basato sul suo codice fiscale    
    Dipendente selectDipendente(Dipendente dipendente);

    //Inserisce un nuovo dipendente nel database
    boolean insertDipendente(Dipendente dipendente);

    //Aggiorna i dati di un dipendente esistente nel database
    boolean updateDipendente(Dipendente dipendente);

    //Cancella un dipendente dal database usando il suo codice fiscale
    boolean deleteDipendente(Dipendente dipendente);
    

    
    //Metodi relativi al DAO Calendario
   
    //Inserisce un nuovo evento nel calendario
    boolean insertEvento(Calendario evento);

    //Aggiorna un evento esistente
    boolean updateEvento(Calendario evento);

    //Cancella un evento dal calendario
    boolean deleteEvento(Calendario evento);

    //Recupera un singolo evento 
    Calendario selectEvento(Calendario evento);

    //Recupera tutti gli eventi in una data specifica
    List<Calendario> selectAllEventiByDate(Calendario evento);

    //Recupera tutti gli eventi in un intervallo di date (incluso)
    List<Calendario> selectAllEventiByDateRange(Calendario eventoInizio, Calendario eventoFine);
    
    
    
    //Metodi relativi al DAO PartecipazioneCorso
    
    // Recupera tutte le partecipazioni dal database
 	List<PartecipazioneCorso> selectAllPartecipazioni();
 	
 	// Recupera tutte le partecipazioni di un cliente dal database 
 	List<PartecipazioneCorso> selectAllPartecipazioniByCf(PartecipazioneCorso partecipazione);
 	
 	// Recupera tutte le partecipazioni ad una sessione dal database
 	List<PartecipazioneCorso> selectAllPartecipazioniBySessione(PartecipazioneCorso partecipazione);
 	
 	// Recupera tutte le partecipazioni di un cliente dal database comprese tra due date
 	List<PartecipazioneCorso> selectAllPartecipazioniByCfAndRange(PartecipazioneCorso cf, PartecipazioneCorso inizio, PartecipazioneCorso fine);
 	
 	// Inserisce una nuova sessione nel database 
 	boolean insertPartecipazioneCorso(PartecipazioneCorso partecipazione);
 		
 	// Cancella una partecipazione dal database 
 	boolean deletePartecipazioneCorso(PartecipazioneCorso partecipazione);
 		
 	// Cancella tutte le partecipazione antecedenti alla data attuale
 	boolean deleteOldPartecipazioni();
 	
 	
 	
 	//Metodi relativi al DAO Sconto
 	
 	// Recupera tutti gli sconti dal database
 	List<Sconto> selectAllSconti();
 		
 	// Recupera tutti gli sconti in una data dal database 
 	List<Sconto> selectAllScontiByDate(Sconto sconto);
 		
 	// Recupera uno sconto dal database
 	Sconto selectSconto(Sconto sconto);
 		
 	// Inserisce un nuovo sconto nel database 
 	boolean insertSconto(Sconto sconto);
 	
 	// Aggiorna i dati di uno sconto esistente nel database
 	boolean updateSconto(Sconto sconto);
 			
 	// Cancella uno sconto dal database 
 	boolean deleteSconto(Sconto sconto);

 	
 	
 	//Metodi relativi al DAO SessioneCorso
 	
 	// Recupera tutte le sessioni dal database
 	List<SessioneCorso> selectAllSessioniCorsi();
 	
 	// Recupera una singola sessione basandosi sul suo ID (chiave primaria)
 	SessioneCorso selectSessioneCorso(SessioneCorso sessione);
 	
 	// Recupera tutte le sessioni in una data specifica
 	List<SessioneCorso> selectAllSessioniByDate(SessioneCorso sessione);
 	
 	// Recupera tutte le sessioni comprese tra due date
 	List<SessioneCorso> selectAllSessioniByRange(SessioneCorso sessioneInizio, SessioneCorso sessioneFine);
 	
 	// Recupera tutte le sessioni di un tipo specifico
 	List<SessioneCorso> selectAllSessioniByType(SessioneCorso sessione);
 	
 	// Recupera tutte le sessioni di un tipo specifico e comprese tra due date
 	List<SessioneCorso> selectAllSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine);
 	
 	// Recupera tutte le sessioni di un tipo specifico dove il numero di iscritti è <25
 	List<SessioneCorso> selectAllAvailableSessioniByType(SessioneCorso sessione);
 	
 	// Recupera tutte le sessioni di un tipo specifico e comprese tra due date dove il numero di iscritti è <25
 	List<SessioneCorso> selectAllAvailableSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine);
 	
 	// Inserisce una nuova sessione nel database
 	boolean insertSessioneCorso(SessioneCorso sessione);
 	
 	// Aggiorna i dati di una sessione esistente nel database
 	boolean updateSessioneCorso(SessioneCorso sessione);
 	
 	// Cancella una sessione dal database usando il suo ID
 	boolean deleteSessioneCorso(SessioneCorso sessione);
 	
 	// Cancella tutte le sessioni antecedenti alla data attuale
 	boolean deleteOldSessioni();

 	
 	
 	//Metodi relativi al DAO Turno
 	
 	// Recupera tutti i turni dal database
    List<Turno> selectAllTurni();

    // Recupera un singolo turno basato sulla sua data (chiave primaria)
    Turno selectTurno(Turno turno);
    
    // Recupera tutti i turni in cui è presente un determinato membro dello staff 
    // Staff id del dipendente interessato da inserire in recMat del dummy turno
    List<Turno> selectAllTurniByPerson(Turno turno);
    
    // Recupera tutti i turni compresi tra due date
    List<Turno> selectAllTurniByRange(Turno turnoInizio, Turno turnoFine);

    // Inserisce un nuovo turno nel database
    boolean insertTurno(Turno turno);

    // Aggiorna i dati di un turno esistente nel database
    boolean updateTurno(Turno turno);

    // Cancella un turno dal database usando la sua data
    boolean deleteTurno(Turno turno);
    
    // Cancella tutti i turni antecedenti alla data attuale
    boolean deleteOldTurni();
}
