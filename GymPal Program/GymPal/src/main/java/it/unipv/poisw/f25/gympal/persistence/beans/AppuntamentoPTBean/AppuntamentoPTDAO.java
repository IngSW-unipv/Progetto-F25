//PRIMARY KEY = cf, staffId, data, fasciaOraria

package it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean;

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

public class AppuntamentoPTDAO implements IAppuntamentoPTDAO {

    private final IConnectionFactory connectionFactory;

    // Costruttore, riceve una factory per la creazione di connessioni
    public AppuntamentoPTDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Inserisce un nuovo appuntamento nel database
    @Override
    public boolean insertAppuntamento(AppuntamentoPT appuntamento) {
        String query = "INSERT INTO APPUNTAMENTI_PT (CF, STAFF_ID, DATA, FASCIA_ORARIA) VALUES (?, ?, ?, ?)";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
                return false;
            }

            ps.setString(1, appuntamento.getCf());
            ps.setString(2, appuntamento.getStaffId());
            ps.setDate(3, Date.valueOf(appuntamento.getData()));
            ps.setString(4, appuntamento.getFasciaOraria());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancella un appuntamento dal database
    @Override
    public boolean deleteAppuntamento(AppuntamentoPT appuntamento) {
        String query = "DELETE FROM APPUNTAMENTI_PT WHERE CF = ? AND STAFF_ID = ? AND DATA = ? AND FASCIA_ORARIA = ?";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
                return false;
            }

            ps.setString(1, appuntamento.getCf());
            ps.setString(2, appuntamento.getStaffId());
            ps.setDate(3, Date.valueOf(appuntamento.getData()));
            ps.setString(4, appuntamento.getFasciaOraria());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Recupera un singolo appuntamento
    @Override
    public AppuntamentoPT findAppuntamento(AppuntamentoPT appuntamento) {
        String query = "SELECT * FROM APPUNTAMENTI_PT WHERE CF = ? AND STAFF_ID = ? AND DATA = ? AND FASCIA_ORARIA = ?";
        AppuntamentoPT result = null;
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, appuntamento.getCf());
            ps.setString(2, appuntamento.getStaffId());
            ps.setDate(3, Date.valueOf(appuntamento.getData()));
            ps.setString(4, appuntamento.getFasciaOraria());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToAppuntamentoPT(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Recupera tutti gli appuntamenti dal database
    @Override
    public List<AppuntamentoPT> selectAll() {
        List<AppuntamentoPT> appuntamenti = new ArrayList<>();
        String query = "SELECT * FROM APPUNTAMENTI_PT";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                appuntamenti.add(mapResultSetToAppuntamentoPT(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appuntamenti;
    }

    // Recupera tutti gli appuntamenti di un cliente
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByCf(AppuntamentoPT appuntamento) {
        List<AppuntamentoPT> appuntamenti = new ArrayList<>();
        String query = "SELECT * FROM APPUNTAMENTI_PT WHERE CF = ?";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, appuntamento.getCf());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appuntamenti.add(mapResultSetToAppuntamentoPT(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appuntamenti;
    }
    
    // Recupera tutti gli appuntamenti di un personal trainer
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByStaffId(AppuntamentoPT appuntamento) {
        List<AppuntamentoPT> appuntamenti = new ArrayList<>();
        String query = "SELECT * FROM APPUNTAMENTI_PT WHERE STAFF_ID = ?";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, appuntamento.getStaffId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appuntamenti.add(mapResultSetToAppuntamentoPT(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appuntamenti;
    }

    // Recupera tutti gli appuntamenti in una data specifica
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByDate(AppuntamentoPT appuntamento) {
        List<AppuntamentoPT> appuntamenti = new ArrayList<>();
        String query = "SELECT * FROM APPUNTAMENTI_PT WHERE DATA = ?";
        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDate(1, Date.valueOf(appuntamento.getData()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appuntamenti.add(mapResultSetToAppuntamentoPT(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appuntamenti;
    }
    
    // Cancella tutti gli appuntamenti antecedenti alla data attuale
 	@Override
 	public boolean deleteOldAppuntamenti() {
 		String query = "DELETE FROM APPUNTAMENTI_PT WHERE DATA < ?";
 		// Blocco try-with-resources
 		try (Connection conn = connectionFactory.createConnection();
 			 PreparedStatement ps = conn.prepareStatement(query)) {
 			
 			if (connectionFactory.isReadOnlyMode()) {
 	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile cancellare i dati.");
 	            return false;
 	        }
 			
 			ps.setDate(1, Date.valueOf(LocalDate.now()));
 			return ps.executeUpdate() > 0;
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 			return false;
 		}
 	}

    // Metodo privato per mappare una riga del ResultSet a un oggetto AppuntamentoPT
    private AppuntamentoPT mapResultSetToAppuntamentoPT(ResultSet rs) throws SQLException {
        String cf = rs.getString("CF");
        String staffId = rs.getString("STAFF_ID");
        LocalDate data = rs.getDate("DATA").toLocalDate();
        String fasciaOraria = rs.getString("FASCIA_ORARIA");
        return new AppuntamentoPT(cf, staffId, data, fasciaOraria);
    }
}
