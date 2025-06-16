package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time; 

import java.time.LocalDate;
import java.time.LocalTime; 

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

public class CalendarioDAO implements ICalendarioDAO {

    private final IConnectionFactory connectionFactory;

    //Costruttore, riceve una factory per la creazione di connessioni
    public CalendarioDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    //Inserisce un nuovo evento nel calendario
    @Override
    public boolean insertEvento(Calendario evento) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
            return false;
        }
        String query = "INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES (?, ?, ?, ?, ?, ?)";
        //Blocco try-with-resources, chiude in automatico la connessione e prepared statement alla fine del try
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, evento.getNomeEvento());
            ps.setDate(2, Date.valueOf(evento.getDataEvento()));
            ps.setTime(3, Time.valueOf(evento.getOraInizio())); 
            ps.setTime(4, Time.valueOf(evento.getOraFine()));   
            ps.setString(5, evento.getMessaggio());
            ps.setString(6, evento.getDestinatarioMessaggio());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Aggiorna un evento esistente
    @Override
    public boolean updateEvento(Calendario evento) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
            return false;
        }
        String query = "UPDATE CALENDARIO SET MESSAGGIO = ?, DESTINATARIO_MESSAGGIO = ? WHERE NOME_EVENTO = ? AND DATA_EVENTO = ? AND ORA_INIZIO = ? AND ORA_FINE = ?";
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, evento.getMessaggio());
            ps.setString(2, evento.getDestinatarioMessaggio());
            ps.setString(3, evento.getNomeEvento());
            ps.setDate(4, Date.valueOf(evento.getDataEvento()));
            ps.setTime(5, Time.valueOf(evento.getOraInizio()));
            ps.setTime(6, Time.valueOf(evento.getOraFine()));
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cancella un evento dal calendario
    @Override
    public boolean deleteEvento(Calendario evento) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
            return false;
        }
        String query = "DELETE FROM CALENDARIO WHERE NOME_EVENTO = ? AND DATA_EVENTO = ? AND ORA_INIZIO = ? AND ORA_FINE = ?";
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, evento.getNomeEvento());
            ps.setDate(2, Date.valueOf(evento.getDataEvento()));
            ps.setTime(3, Time.valueOf(evento.getOraInizio()));
            ps.setTime(4, Time.valueOf(evento.getOraFine()));
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Recupera un singolo evento
    @Override
    public Calendario selectEvento(Calendario evento) {
        Calendario result = null;
        String query = "SELECT * FROM CALENDARIO WHERE NOME_EVENTO = ? AND DATA_EVENTO = ? AND ORA_INIZIO = ? AND ORA_FINE = ?";
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	ps.setString(1, evento.getNomeEvento());
            ps.setDate(2, Date.valueOf(evento.getDataEvento()));
            ps.setTime(3, Time.valueOf(evento.getOraInizio()));
            ps.setTime(4, Time.valueOf(evento.getOraFine()));
            //Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToCalendario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Recupera tutti gli eventi in una data specifica
    @Override
    public List<Calendario> selectAllEventiByDate(Calendario evento) {
        List<Calendario> eventi = new ArrayList<>();
        String query = "SELECT * FROM CALENDARIO WHERE DATA_EVENTO = ?";
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(evento.getDataEvento()));
            //Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    eventi.add(mapResultSetToCalendario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventi;
    }

    //Recupera tutti gli eventi in un intervallo di date (incluso)
    @Override
    public List<Calendario> selectAllEventiByDateRange(Calendario eventoInizio, Calendario eventoFine) {
        List<Calendario> eventi = new ArrayList<>();
        String query = "SELECT * FROM CALENDARIO WHERE DATA_EVENTO BETWEEN ? AND ?";
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(eventoInizio.getDataEvento()));
            ps.setDate(2, Date.valueOf(eventoFine.getDataEvento()));
            //Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    eventi.add(mapResultSetToCalendario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventi;
    }

    //Metodo privato per mappare una riga del ResultSet a un oggetto Calendario
    private Calendario mapResultSetToCalendario(ResultSet rs) throws SQLException {
        String nomeEvento = rs.getString("NOME_EVENTO");
        LocalDate dataEvento = rs.getDate("DATA_EVENTO").toLocalDate();
        LocalTime oraInizio = rs.getTime("ORA_INIZIO").toLocalTime(); 
        LocalTime oraFine = rs.getTime("ORA_FINE").toLocalTime();     
        String messaggio = rs.getString("MESSAGGIO");
        String destinatarioMessaggio = rs.getString("DESTINATARIO_MESSAGGIO");
        return new Calendario(nomeEvento, dataEvento, oraInizio, oraFine, messaggio, destinatarioMessaggio);
    }
}
