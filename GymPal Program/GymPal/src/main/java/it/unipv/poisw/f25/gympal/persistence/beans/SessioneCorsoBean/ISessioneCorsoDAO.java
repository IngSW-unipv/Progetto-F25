//PRIMARY KEY = sessioneId

package it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean;

import java.util.List;

public interface ISessioneCorsoDAO {
	
	//NOTA: è stato preso in considerazione l'overloading dei metodi di select all ma in ultima analisi è risultato infattibile
	
	// Recupera tutte le sessioni dal database
	List<SessioneCorso> selectAll();
	
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
	
}
