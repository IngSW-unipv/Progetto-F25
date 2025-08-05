package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import java.util.List;

public interface ICalendarioDAO {

    //Inserisce un nuovo evento nel calendario
    boolean insertEvento(Calendario evento);

    //Aggiorna un evento esistente
    boolean updateEvento(Calendario evento);

    //Cancella un evento dal calendario
    boolean deleteEvento(Calendario evento);
    
    //Cancella tutti gli eventi antecedenti alla data attuale
    boolean deleteOldEventi();

    //Recupera un singolo evento 
    Calendario selectEvento(Calendario evento);

    //Recupera tutti gli eventi in una data specifica
    List<Calendario> selectAllEventiByDate(Calendario evento);

    //Recupera tutti gli eventi in un intervallo di date (incluso)
    List<Calendario> selectAllEventiByDateRange(Calendario eventoInizio, Calendario eventoFine);
}
