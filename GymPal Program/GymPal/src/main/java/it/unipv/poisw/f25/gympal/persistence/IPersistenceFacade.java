package it.unipv.poisw.f25.gympal.persistence;

import java.util.List;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

//Interfaccia per accedere allo strato di persistenza
public interface IPersistenceFacade {

    // Metodi relativi al DAO Cliente
 
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
}
