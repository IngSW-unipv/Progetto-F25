package it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;


public class TurnoDAO implements ITurnoDAO {

    private final IConnectionFactory connectionFactory;

    // Costruttore, riceve una factory per la creazione di connessioni
    public TurnoDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Recupera tutti i turni dal database
    @Override
    public List<Turno> selectAll() {
        List<Turno> turni = new ArrayList<>();
        String query = "SELECT DATA, REC_MAT, REC_POM, PT_MAT, PT_POM FROM TURNI";

        // Blocco try-with-resources, chiude in automatico le risorse
        try (Connection conn = connectionFactory.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                turni.add(mapResultSetToTurno(rs));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); 
        }
        return turni;
    }

    // Recupera un singolo turno basato sulla sua data
    @Override
    public Turno selectTurno(Turno turno) {
        Turno result = null;
        String query = "SELECT DATA, REC_MAT, REC_POM, PT_MAT, PT_POM FROM TURNI WHERE DATA = ?";

        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDate(1, Date.valueOf(turno.getData()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToTurno(rs);
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    // Recupera tutti i turni in cui è presente un determinato membro dello staff
 // Staff id del dipendente interessato da inserire in recMat del dummy turno
    @Override
    public List<Turno> selectAllTurniByPerson(Turno turno) {
        List<Turno> turni = new ArrayList<>();

        String query = "SELECT DATA, REC_MAT, REC_POM, PT_MAT, PT_POM FROM TURNI " +
                       "WHERE REC_MAT = ? OR REC_POM = ? OR PT_MAT = ? OR PT_POM = ?";

        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, turno.getRecMat());
            ps.setString(2, turno.getRecMat());
            ps.setString(3, turno.getRecMat());
            ps.setString(4, turno.getRecMat());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    turni.add(mapResultSetToTurno(rs));
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return turni;
    }
    
    // Recupera tutti i turni compresi tra due date
    @Override
    public List<Turno> selectAllTurniByRange(Turno turnoInizio, Turno turnoFine) {
        List<Turno> turni = new ArrayList<>();
        String query = "SELECT DATA, REC_MAT, REC_POM, PT_MAT, PT_POM FROM TURNI WHERE DATA BETWEEN ? AND ?";
        
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDate(1, Date.valueOf(turnoInizio.getData()));
            ps.setDate(2, Date.valueOf(turnoFine.getData()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    turni.add(mapResultSetToTurno(rs));
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return turni;
    }

    // Inserisce un nuovo turno nel database
    @Override
    public boolean insertTurno(Turno turno) {

        String query = "INSERT INTO TURNI (DATA, REC_MAT, REC_POM, PT_MAT, PT_POM) VALUES (?, ?, ?, ?, ?)";
        
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
                return false;
            }

            ps.setDate(1, Date.valueOf(turno.getData()));
            ps.setString(2, turno.getRecMat());
            ps.setString(3, turno.getRecPom());
            ps.setString(4, turno.getPtMat());
            ps.setString(5, turno.getPtPom());

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Aggiorna i dati di un turno esistente nel database
    @Override
    public boolean updateTurno(Turno turno) {
        
        String query = "UPDATE TURNI SET REC_MAT = ?, REC_POM = ?, PT_MAT = ?, PT_POM = ? WHERE DATA = ?";
        
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
                return false;
            }

            ps.setString(1, turno.getRecMat());
            ps.setString(2, turno.getRecPom());
            ps.setString(3, turno.getPtMat());
            ps.setString(4, turno.getPtPom());
            ps.setDate(5, Date.valueOf(turno.getData()));

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancella un turno dal database usando la sua data
    @Override
    public boolean deleteTurno(Turno turno) {

        String query = "DELETE FROM TURNI WHERE DATA = ?";
        
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
                return false;
            }

            ps.setDate(1, Date.valueOf(turno.getData()));
            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancella tutti i turni antecedenti alla data attuale
    @Override
    public boolean deleteOldTurni() {
        
        String query = "DELETE FROM TURNI WHERE DATA < ?";
        
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
                return false;
            }
            
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo privato per mappare una riga del ResultSet a un oggetto Turno
    private Turno mapResultSetToTurno(ResultSet rs) throws SQLException {
        LocalDate data = rs.getDate("DATA").toLocalDate();
        String recMat = rs.getString("REC_MAT");
        String recPom = rs.getString("REC_POM");
        String ptMat = rs.getString("PT_MAT");
        String ptPom = rs.getString("PT_POM");

        return new Turno(data, recMat, recPom, ptMat, ptPom);
    }
}
