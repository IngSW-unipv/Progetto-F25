//PRIMARY KEY = sessioneId

package it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean;

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

public class SessioneCorsoDAO implements ISessioneCorsoDAO{
	
	private final IConnectionFactory connectionFactory;
	private static final int MAX_ISCRITTI_CORSO = 25;
	
	// Costruttore, riceve una factory per la creazione di connessioni
	public SessioneCorsoDAO(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	// Recupera tutte le sessioni dal database
	@Override
	public List<SessioneCorso> selectAll(){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI";
		
		// Blocco try-with-resources, chiude in automatico le risorse
		try(Connection conn = connectionFactory.createConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)) {
			
		   while(rs.next()) {
			   corsi.add(mapResultSetToSessioneCorso(rs));
		   }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;		
	}
	
	// Recupera una singola sessione basato sul suo ID (chiave primaria)
	@Override
	public SessioneCorso selectSessioneCorso(SessioneCorso sessione) {
		SessioneCorso result = null;
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI WHERE ID_SESSIONE = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, sessione.getSessioneId());
		
			try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToSessioneCorso(rs);
                }
            }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	// Recupera tutte le sessioni in una data specifica
	public List<SessioneCorso> selectAllSessioniByDate(SessioneCorso sessione){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI WHERE DATA = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setDate(1, Date.valueOf(sessione.getData()));
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Recupera tutte le sessioni comprese tra due date
	public List<SessioneCorso> selectAllSessioniByRange(SessioneCorso sessioneInizio, SessioneCorso sessioneFine){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI WHERE DATA BETWEEN ? AND ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setDate(1, Date.valueOf(sessioneInizio.getData()));
			ps.setDate(2, Date.valueOf(sessioneFine.getData()));
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Recupera tutte le sessioni di un tipo specifico
	public List<SessioneCorso> selectAllSessioniByType(SessioneCorso sessione){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI "
				     + "WHERE UPPER(ID_SESSIONE) LIKE ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			//"%" + sessione.getSessioneId().toUpperCase() = qualunque stringa che terminaa con una 
			//sequenza di caratteri uguale a SessioneId, case insensitive
			ps.setString(1, "%" + sessione.getSessioneId().toUpperCase());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Recupera tutte le sessioni di un tipo specifico e comprese tra due date
	public List<SessioneCorso> selectAllSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI "
				     + "WHERE UPPER(ID_SESSIONE) LIKE ? AND DATA BETWEEN ? AND ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setString(1, "%" + sessioneTipo.getSessioneId().toUpperCase());
			ps.setDate(2, Date.valueOf(sessioneInizio.getData()));
			ps.setDate(3, Date.valueOf(sessioneFine.getData()));

			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Recupera tutte le sessioni di un tipo specifico dove il numero di iscritti è <25
	public List<SessioneCorso> selectAllAvailableSessioniByType(SessioneCorso sessione){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI "
				     + "WHERE UPPER(ID_SESSIONE) LIKE ? AND NUM_ISCRITTI < ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			//"%" + sessione.getSessioneId().toUpperCase() = qualunque stringa che terminaa con una 
			//sequenza di caratteri uguale a SessioneId, case insensitive
			ps.setString(1, "%" + sessione.getSessioneId().toUpperCase());
			ps.setInt(2, MAX_ISCRITTI_CORSO);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Recupera tutte le sessioni di un tipo specifico e comprese tra due date dove il numero di iscritti è <25
	public List<SessioneCorso> selectAllAvailableSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine){
		List<SessioneCorso> corsi = new ArrayList<>();
		String query = "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI "
				     + "WHERE UPPER(ID_SESSIONE) LIKE ? AND DATA BETWEEN ? AND ? AND NUM_ISCRITTI < ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setString(1, "%" + sessioneTipo.getSessioneId().toUpperCase());
			ps.setDate(2, Date.valueOf(sessioneInizio.getData()));
			ps.setDate(3, Date.valueOf(sessioneFine.getData()));
			ps.setInt(4, MAX_ISCRITTI_CORSO);

			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					corsi.add(mapResultSetToSessioneCorso(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return corsi;
	}
	
	@Override
	// Inserisce una nuova sessione nel database
	public boolean insertSessioneCorso(SessioneCorso sessione) {
		
		
		String query = "INSERT INTO SESSIONI_CORSI (ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI) "
	             + "VALUES (?, ?, ?, ?, ?)";
		
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
	            return false;
	        }
			
			ps.setString(1, sessione.getSessioneId());
			ps.setString(2, sessione.getStaffId());
			ps.setDate(3, Date.valueOf(sessione.getData()));
			ps.setString(4, sessione.getFasciaOraria());
			ps.setInt(5, sessione.getNumIscritti());
			
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	// Aggiorna i dati di una sessione esistente nel database
	public boolean updateSessioneCorso(SessioneCorso sessione) {
		
		String query = "UPDATE SESSIONI_CORSI SET STAFF_ID = ?, DATA = ?, FASCIA_ORARIA = ?, NUM_ISCRITTI = ? "
					 + "WHERE ID_SESSIONE = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
	            return false;
	        }
			
			ps.setString(1, sessione.getStaffId());
			ps.setDate(2, Date.valueOf(sessione.getData()));
			ps.setString(3, sessione.getFasciaOraria());
			ps.setInt(4, sessione.getNumIscritti());
			ps.setString(5, sessione.getSessioneId());
			
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	// Cancella una sessione dal database usando il suo ID
	public boolean deleteSessioneCorso(SessioneCorso sessione) {
		
		String query = "DELETE FROM SESSIONI_CORSI WHERE ID_SESSIONE = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
	            return false;
	        }
			
			ps.setString(1, sessione.getSessioneId());
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	// Cancella tutte le sessioni antecedenti alla data attuale
	public boolean deleteOldSessioni() {
		
		String query = "DELETE FROM SESSIONI_CORSI WHERE DATA < ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
	            return false;
	        }
			
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			return ps.executeUpdate() > 0;

		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Metodo privato per mappare una riga del ResultSet a un oggetto SessioneCorso
	private SessioneCorso mapResultSetToSessioneCorso(ResultSet rs) throws SQLException {
		String  sessioneId = rs.getString("ID_SESSIONE");
		String  staffId = rs.getString("STAFF_ID");
		LocalDate data = rs.getDate("DATA").toLocalDate();
		String  fasciaOraria = rs.getString("FASCIA_ORARIA");
		int numIscritti = rs.getInt("NUM_ISCRITTI");
		
		return new SessioneCorso(sessioneId, staffId, data, fasciaOraria, numIscritti);
	}
}
