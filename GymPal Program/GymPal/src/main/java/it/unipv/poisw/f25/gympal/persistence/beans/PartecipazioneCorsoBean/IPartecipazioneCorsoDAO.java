//PRIMARY KEY = cf, sessioneId

package it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean;

import java.util.List;

public interface IPartecipazioneCorsoDAO {
	
	// Recupera tutte le partecipazioni dal database
	List<PartecipazioneCorso> selectAll();
	
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

}
