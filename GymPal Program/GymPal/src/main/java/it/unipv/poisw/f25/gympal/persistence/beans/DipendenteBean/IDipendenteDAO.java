//PRIMARY KEY = staffId

package it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean;

import java.util.List;

public interface IDipendenteDAO {

	//Recupera tutti i dipendenti dal database
    List<Dipendente> selectAll();
    
    //Recupera un singolo dipendente basato sul suo Id  
    Dipendente selectDipendente(Dipendente dipendente);

    //Inserisce un nuovo dipendente nel database
    boolean insertDipendente(Dipendente dipendente);

    //Aggiorna i dati di un dipendente esistente nel database
    boolean updateDipendente(Dipendente dipendente);

    //Cancella un dipendente dal database usando il suo Id
    boolean deleteDipendente(Dipendente dipendente);
}