package it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

public class DipendenteDAO implements IDipendenteDAO {
	
	private final IConnectionFactory connectionFactory;
	
	//Costruttore, riceve una factory per la creazione di connessioni
	public DipendenteDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
	
	//Recupera tutti i dipendenti dal database  
	@Override
	public List<Dipendente> selectAll() {
        List<Dipendente> dipendenti = new ArrayList<>();
        String query = "SELECT * FROM DIPENDENTI";
        
        //Blocco try-with-resources, chiude in automatico la connessione, statement e result set alla fine del try
        try (Connection conn = connectionFactory.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                dipendenti.add(mapResultSetToDipendente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dipendenti;
    }

	//Recupera un singolo dipendente basato sul suo codice fiscale 
	@Override
    public Dipendente selectDipendente(Dipendente dipendente) {
        Dipendente result = null;
        String query = "SELECT * FROM DIPENDENTI WHERE STAFF_ID = ?";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, dipendente.getStaffId());
            
            //Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToDipendente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //Inserisce un nuovo dipendente nel database
	@Override
    public boolean insertDipendente(Dipendente dipendente) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
            return false;
        }

        String query = "INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES (?, ?, ?, ?)";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, dipendente.getStaffId());
            ps.setString(2, dipendente.getNome());
            ps.setString(3, dipendente.getCognome());
            ps.setString(4, dipendente.getContatto());

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Aggiorna i dati di un dipendente esistente nel database
    @Override
    public boolean updateDipendente(Dipendente dipendente) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
            return false;
        }

        String query = "UPDATE DIPENDENTI SET NOME = ?, COGNOME = ?, CONTATTO = ? WHERE STAFF_ID = ?";

        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, dipendente.getNome());
            ps.setString(2, dipendente.getCognome());
            ps.setString(3, dipendente.getContatto());
            ps.setString(4, dipendente.getStaffId());

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cancella un dipendente dal database usando il suo codice fiscale
    @Override
    public boolean deleteDipendente(Dipendente dipendente) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
            return false;
        }

        String query = "DELETE FROM DIPENDENTI WHERE STAFF_ID = ?";

        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, dipendente.getStaffId());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Metodo privato per mappare una riga del ResultSet a un oggetto Dipendente
    private Dipendente mapResultSetToDipendente(ResultSet rs) throws SQLException {
        String staffId = rs.getString("STAFF_ID");
        String nome = rs.getString("NOME");
        String cognome = rs.getString("COGNOME");
        String contatto = rs.getString("CONTATTO");

        return new Dipendente(staffId, nome, cognome, contatto);
    }

}