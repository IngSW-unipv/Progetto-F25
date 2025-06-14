package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ICalendarioDAO {

    //Inserisce un nuovo evento nel calendario
    boolean insertEvento(Calendario evento);

    //Aggiorna un evento esistente
    boolean updateEvento(Calendario evento);

    //Cancella un evento dal calendario
    boolean deleteEvento(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine);

    //Recupera un singolo evento 
    //Caalendario usa una chiave primaria composita (nomeEvento + dataEvento)
    Calendario selectEvento(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine);

    //Recupera tutti gli eventi in una data specifica
    List<Calendario> selectAllEventiByDate(LocalDate data);

    //Recupera tutti gli eventi in un intervallo di date (incluso)
    List<Calendario> selectAllEventiByDateRange(LocalDate dataInizio, LocalDate dataFine);
}
