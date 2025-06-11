package it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

public class ClienteDAO implements IClienteDAO {
	
	private final IConnectionFactory connectionFactory;
	
	//Costruttore, riceve una factory per la creazione di connessioni
	public ClienteDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
	
	//Recupera tutti i clienti dal database
	@Override
    public List<Cliente> selectAll() {
        List<Cliente> clienti = new ArrayList<>();
        String query = "SELECT * FROM CLIENTI";
        
        //Blocco try-with-resources, chiude in automatico la connessione, statement e result set alla fine del try
        try (Connection conn = connectionFactory.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                clienti.add(mapResultSetToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienti;
    }

	//Recupera un singolo cliente basato sul suo codice fiscale
    @Override
    public Cliente selectCliente(String cf) {
        Cliente result = null;
        String query = "SELECT * FROM CLIENTI WHERE CF = ?";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, cf);
            
            //Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //Inserisce un nuovo cliente nel database
    @Override
    public boolean insertCliente(Cliente cliente) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
            return false;
        }
        
        String query = "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, cliente.getCf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getCognome());
            ps.setString(4, cliente.getSesso());
            ps.setBoolean(5, cliente.getIsMinorenne()); // Il driver JDBC converte boolean in TINYINT(1)
            ps.setString(6, cliente.getContatto());
            ps.setString(7, cliente.getAbbonamento());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Aggiorna i dati di un cliente esistente nel database
    @Override
    public boolean updateCliente(Cliente cliente) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
            return false;
        }
        
        String query = "UPDATE CLIENTI SET NOME = ?, COGNOME = ?, SESSO = ?, FLAG_MINOR = ?, CONTATTO = ?, ABBONAMENTO = ? WHERE CF = ?";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getSesso());
            ps.setBoolean(4, cliente.getIsMinorenne());
            ps.setString(5, cliente.getContatto());
            ps.setString(6, cliente.getAbbonamento());
            ps.setString(7, cliente.getCf());
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cancella un cliente dal database usando il suo codice fiscale
    @Override
    public boolean deleteCliente(String cf) {
        if (connectionFactory.isReadOnlyMode()) {
            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
            return false;
        }
        
        String query = "DELETE FROM CLIENTI WHERE CF = ?";
        
        //Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, cf);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Metodo privato per mappare una riga del ResultSet a un oggetto Cliente
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        String cf = rs.getString("CF");
        String nome = rs.getString("NOME");
        String cognome = rs.getString("COGNOME");
        String sesso = rs.getString("SESSO");
        boolean isMinorenne = rs.getBoolean("FLAG_MINOR"); // Il driver JDBC converte TINYINT(1) in boolean
        String contatto = rs.getString("CONTATTO");
        String abbonamento = rs.getString("ABBONAMENTO");
        
        return new Cliente(cf, nome, cognome, sesso, isMinorenne, contatto, abbonamento);
    }

}
