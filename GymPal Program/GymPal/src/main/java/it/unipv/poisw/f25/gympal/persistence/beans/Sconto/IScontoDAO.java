//PRIMARY KEY = nomeSconto

package it.unipv.poisw.f25.gympal.persistence.beans.Sconto;

import java.util.List;

public interface IScontoDAO {
	
	
	// Recupera tutti gli sconti dal database
	List<Sconto> selectAll();
		
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
			

}
