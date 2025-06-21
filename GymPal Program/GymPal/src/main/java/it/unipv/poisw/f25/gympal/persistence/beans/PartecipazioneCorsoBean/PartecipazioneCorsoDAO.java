package it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;


public class PartecipazioneCorsoDAO implements IPartecipazioneCorsoDAO {
	
	private final IConnectionFactory connectionFactory;

	
	public PartecipazioneCorsoDAO(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	// Recupera tutte le partecipazioni dal database
	@Override
	public List<PartecipazioneCorso> selectAll() {
		List<PartecipazioneCorso> partecipazioni = new ArrayList<>();
		String query = "SELECT CF, ID_SESSIONE FROM PARTECIPAZIONI_CORSI";
		
		// Blocco try-with-resources, chiude in automatico le risorse
		try (Connection conn = connectionFactory.createConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				partecipazioni.add(mapResultSetToPartecipazioneCorso(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partecipazioni;
	}
	
	// Recupera tutte le partecipazioni di un cliente dal database 
	@Override
	public List<PartecipazioneCorso> selectAllPartecipazioniByCf(PartecipazioneCorso partecipazione) {
		List<PartecipazioneCorso> partecipazioni = new ArrayList<>();
		String query = "SELECT CF, ID_SESSIONE FROM PARTECIPAZIONI_CORSI WHERE CF = ?";
		
		// Blocco try-with-resources
		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, partecipazione.getCf());
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					partecipazioni.add(mapResultSetToPartecipazioneCorso(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partecipazioni;
	}

	// Recupera tutte le partecipazioni ad una sessione dal database
	@Override
	public List<PartecipazioneCorso> selectAllPartecipazioniBySessione(PartecipazioneCorso partecipazione) {
		List<PartecipazioneCorso> partecipazioni = new ArrayList<>();
		String query = "SELECT CF, ID_SESSIONE FROM PARTECIPAZIONI_CORSI WHERE ID_SESSIONE = ?";
		
		// Blocco try-with-resources
		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, partecipazione.getSessioneId());

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					partecipazioni.add(mapResultSetToPartecipazioneCorso(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partecipazioni;
	}

	// Recupera tutte le partecipazioni di un cliente dal database comprese tra due date
	@Override
	public List<PartecipazioneCorso> selectAllPartecipazioniByCfAndRange(PartecipazioneCorso cf, PartecipazioneCorso inizio, PartecipazioneCorso fine) {
		List<PartecipazioneCorso> partecipazioni = new ArrayList<>();
		
		String query = "SELECT CF, ID_SESSIONE FROM PARTECIPAZIONI_CORSI " +
					   "WHERE CF = ? AND ID_SESSIONE IN " +
					   "(SELECT ID_SESSIONE FROM SESSIONI_CORSI WHERE DATA BETWEEN ? AND ?)";

		LocalDate dataInizio = parseSessioneIdToDate(inizio.getSessioneId());
		LocalDate dataFine = parseSessioneIdToDate(fine.getSessioneId());

		if (dataInizio == null || dataFine == null) {
			System.err.println("Formato ID sessione non valido per il range di date.");
			return partecipazioni;
		}

		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, cf.getCf());
			ps.setDate(2, Date.valueOf(dataInizio));
			ps.setDate(3, Date.valueOf(dataFine));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					partecipazioni.add(mapResultSetToPartecipazioneCorso(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partecipazioni;
	}


	// Inserisce una nuova sessione nel database 
	@Override
	public boolean insertPartecipazioneCorso(PartecipazioneCorso partecipazione) {
		if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
            return false;
        }
		
		String query = "INSERT INTO PARTECIPAZIONI_CORSI (CF, ID_SESSIONE) VALUES (?, ?)";
		
		// Blocco try-with-resources
		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, partecipazione.getCf());
			ps.setString(2, partecipazione.getSessioneId());
			
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Cancella una partecipazione dal database
	@Override
	public boolean deletePartecipazioneCorso(PartecipazioneCorso partecipazione) {
		if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile cancellare i dati.");
            return false;
        }
		
		String query = "DELETE FROM PARTECIPAZIONI_CORSI WHERE CF = ? AND ID_SESSIONE = ?";
		
		// Blocco try-with-resources
		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, partecipazione.getCf());
			ps.setString(2, partecipazione.getSessioneId());
			
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Cancella tutte le partecipazione antecedenti alla data attuale
	@Override
	public boolean deleteOldPartecipazioni() {
		if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile cancellare i dati.");
            return false;
        }
		
		// Cancella le partecipazioni il cui ID_SESSIONE corrisponde a una sessione con data passata
		String query = "DELETE FROM PARTECIPAZIONI_CORSI WHERE ID_SESSIONE IN "
					 + "(SELECT ID_SESSIONE FROM SESSIONI_CORSI WHERE DATA < ?)";
		
		// Blocco try-with-resources
		try (Connection conn = connectionFactory.createConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Metodo privato per mappare una riga di un ResultSet a un oggetto PartecipazioneCorso
	private PartecipazioneCorso mapResultSetToPartecipazioneCorso(ResultSet rs) throws SQLException {
		String cf = rs.getString("CF");
		String sessioneId = rs.getString("ID_SESSIONE");
		return new PartecipazioneCorso(cf, sessioneId);
	}
	
	// Esegue il parsing di un ID sessione per estrarne la data
	private LocalDate parseSessioneIdToDate(String sessioneId) {
		if (sessioneId == null || sessioneId.length() < 6) {
			return null;
		}
		try {
			// Estrae la sottostringa della data (es. "210625")
			String dateSubstring = sessioneId.substring(0, 6);
			// Definisce il formato atteso
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
			// Esegue il parsing e restituisce l'oggetto LocalDate
			return LocalDate.parse(dateSubstring, formatter);
		} catch (DateTimeParseException e) {
			System.err.println("Errore nel parsing della data dall'ID sessione: " + sessioneId);
			e.printStackTrace();
			return null;
		}
	}
}
