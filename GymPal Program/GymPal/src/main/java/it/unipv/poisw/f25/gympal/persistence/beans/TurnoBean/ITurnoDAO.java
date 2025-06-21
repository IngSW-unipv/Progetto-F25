package it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean;

import java.util.List;

public interface ITurnoDAO {

    // Recupera tutti i turni dal database
    List<Turno> selectAll();

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
