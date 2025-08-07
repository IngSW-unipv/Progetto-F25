//PRIMARY KEY = cf

package it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; // Importa java.sql.Date per la conversione JDBC

import java.time.LocalDate; // Importa java.time.LocalDate

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

public class ClienteDAO implements IClienteDAO {

    private final IConnectionFactory connectionFactory;

    // Costruttore, riceve una factory per la creazione di connessioni
    public ClienteDAO(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Recupera tutti i clienti dal database
    @Override
    public List<Cliente> selectAll() {
        List<Cliente> clienti = new ArrayList<>();
        // Aggiorna la query SELECT per includere i nuovi campi
        String query = "SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, " +
                       "INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO " +
                       "FROM CLIENTI";

        // Blocco try-with-resources, chiude in automatico la connessione, statement e result set alla fine del try
        try (Connection conn = connectionFactory.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                clienti.add(mapResultSetToCliente(rs));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return clienti;
    }

    // Recupera un singolo cliente basato sul suo codice fiscale
    @Override
    public Cliente selectCliente(Cliente cliente) {
        Cliente result = null;
        // Aggiorna la query SELECT per includere i nuovi campi
        String query = "SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, " +
                       "INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO " +
                       "FROM CLIENTI WHERE CF = ?";

        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, cliente.getCf());

            // Blocco try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapResultSetToCliente(rs);
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Inserisce un nuovo cliente nel database
    @Override
    public boolean insertCliente(Cliente cliente) {
        // Aggiorna la query INSERT per includere i nuovi campi
        String query = "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, " +
                       "INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
                return false;
            }

            ps.setString(1, cliente.getCf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getCognome());
            ps.setString(4, cliente.getSesso());
            ps.setBoolean(5, cliente.getIsMinorenne());
            ps.setString(6, cliente.getContatto());
            ps.setString(7, cliente.getAbbonamento());
            ps.setDate(8, Date.valueOf(cliente.getInizioAbbonamento()));
            ps.setDate(9, Date.valueOf(cliente.getFineAbbonamento()));
            ps.setBoolean(10, cliente.getPagamentoEffettuato());
            ps.setString(11, cliente.getComposizioneAbbonamento());

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Aggiorna i dati di un cliente esistente nel database
    @Override
    public boolean updateCliente(Cliente cliente) {

        // Aggiorna la query UPDATE per includere i nuovi campi
        String query = "UPDATE CLIENTI SET NOME = ?, COGNOME = ?, SESSO = ?, FLAG_MINOR = ?, CONTATTO = ?, " +
                       "ABBONAMENTO = ?, INIZIO_ABBONAMENTO = ?, FINE_ABBONAMENTO = ?, " +
                       "PAGAMENTO_EFFETTUATO = ?, COMPOSIZIONE_ABBONAMENTO = ? WHERE CF = ?";

        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
                return false;
            }

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getSesso());
            ps.setBoolean(4, cliente.getIsMinorenne());
            ps.setString(5, cliente.getContatto());
            ps.setString(6, cliente.getAbbonamento());
            ps.setDate(7, Date.valueOf(cliente.getInizioAbbonamento()));
            ps.setDate(8, Date.valueOf(cliente.getFineAbbonamento()));
            ps.setBoolean(9, cliente.getPagamentoEffettuato());
            ps.setString(10, cliente.getComposizioneAbbonamento());
            ps.setString(11, cliente.getCf());

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancella un cliente dal database usando il suo codice fiscale
    @Override
    public boolean deleteCliente(Cliente cliente) {
        
        String query = "DELETE FROM CLIENTI WHERE CF = ?";

        // Blocco try-with-resources
        try (Connection conn = connectionFactory.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	if (connectionFactory.isReadOnlyMode()) {
                System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
                return false;
            }

            ps.setString(1, cliente.getCf());
            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo privato per mappare una riga del ResultSet a un oggetto Cliente
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        String cf = rs.getString("CF");
        String nome = rs.getString("NOME");
        String cognome = rs.getString("COGNOME");
        String sesso = rs.getString("SESSO");
        boolean isMinorenne = rs.getBoolean("FLAG_MINOR");
        String contatto = rs.getString("CONTATTO");
        String abbonamento = rs.getString("ABBONAMENTO");
        LocalDate inizioAbbonamento = rs.getDate("INIZIO_ABBONAMENTO").toLocalDate();
        LocalDate fineAbbonamento = rs.getDate("FINE_ABBONAMENTO").toLocalDate();
        boolean pagamentoEffettuato = rs.getBoolean("PAGAMENTO_EFFETTUATO");
        String composizioneAbbonamento = rs.getString("COMPOSIZIONE_ABBONAMENTO");

        return new Cliente(cf, nome, cognome, sesso, isMinorenne, contatto, abbonamento,
                           inizioAbbonamento, fineAbbonamento, pagamentoEffettuato, composizioneAbbonamento);
    }
}