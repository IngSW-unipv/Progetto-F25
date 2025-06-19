package it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate; 

import java.util.List;

import org.h2.jdbcx.JdbcDataSource;

//Classe di test di integrazione per ClienteDAO.
//Utilizza un database H2 in-memory per isolare i test 
public class ClienteDAOTest {

    private static IConnectionFactory testConnectionFactory;
    private static JdbcDataSource dataSource;
    private IClienteDAO clienteDAO;

    //Una ConnectionFactory specifica per i test
    private static class H2TestConnectionFactory implements IConnectionFactory {
        private final JdbcDataSource ds;

        public H2TestConnectionFactory(JdbcDataSource ds) {
            this.ds = ds;
        }

        @Override
        public Connection createConnection() throws SQLException {
            return ds.getConnection();
        }
        
        //L'unico metodo che veramente mi serve è createConnection
        
        
        @Override
        public boolean isOpen(Connection conn) {
            try {
                return conn != null && !conn.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }

        @Override
        public void closeConnection(Connection conn) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        
        @Override
        public boolean isReadOnlyMode() {
            // Per i test, assumo che la scrittura sia sempre permessa.
            return false;
        }
    }

    //Metodo eseguito una sola volta prima di tutti i test della classe
    @BeforeClass
    public static void setUpClass() throws Exception {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"); // DB_CLOSE_DELAY=-1 mantiene il DB attivo
        dataSource.setUser("sa");
        dataSource.setPassword("");

        testConnectionFactory = new H2TestConnectionFactory(dataSource);

        // Creazione dello schema della tabella CLIENTI nel DB in-memory
        try (Connection conn = testConnectionFactory.createConnection(); 
        	 Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE CLIENTI (" +
                         "CF VARCHAR(16) NOT NULL PRIMARY KEY, " +
                         "NOME VARCHAR(50) NOT NULL, " +
                         "COGNOME VARCHAR(50) NOT NULL, " +
                         "SESSO CHAR(1) NOT NULL, " +
                         "FLAG_MINOR TINYINT(1) NOT NULL, " +
                         "CONTATTO VARCHAR(100) NOT NULL, " +
                         "ABBONAMENTO VARCHAR(20) NOT NULL, " +
                         "INIZIO_ABBONAMENTO DATE NOT NULL, " + 
                         "FINE_ABBONAMENTO DATE NOT NULL, " +    
                         "PAGAMENTO_EFFETTUATO BOOLEAN NOT NULL, " + 
                         "COMPOSIZIONE_ABBONAMENTO VARCHAR(255) NOT NULL)"; 
            stmt.executeUpdate(sql);
        }
    }

    //Metodo eseguito prima di ogni singolo test, pulisce la tabella e inserisce dati di test sempre uguali per ogni test
    @Before
    public void setUp() throws Exception {
        clienteDAO = new ClienteDAO(testConnectionFactory);

        try (Connection conn = testConnectionFactory.createConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM CLIENTI");
            // Inserimento dati di test con i nuovi campi
            stmt.executeUpdate("INSERT INTO CLIENTI VALUES ('RSSMRA85A01H501U', 'Mario', 'Rossi', 'M', 0, 'mario.rossi@email.com', 'Annuale', '2024-01-01', '2024-12-31', TRUE, 'Sala Pesi')");
            stmt.executeUpdate("INSERT INTO CLIENTI VALUES ('VRDNCL90B15F612N', 'Carla', 'Verdi', 'F', 0, 'carla.verdi@email.com', 'Mensile', '2024-06-01', '2024-06-30', TRUE, 'Corsi Collettivi')");
        }
    }
    
    @Test
    public void testSelectAll() {
        List<Cliente> clienti = clienteDAO.selectAll();
        assertNotNull("La lista non deve essere null", clienti);
        assertEquals("Dovrebbero esserci 2 clienti", 2, clienti.size());
    }

    @Test
    public void testSelectClienteFound() {
        Cliente clienteDaCercare = new Cliente();
        clienteDaCercare.setCf("RSSMRA85A01H501U");
        
        Cliente cliente = clienteDAO.selectCliente(clienteDaCercare);
        assertNotNull("Il cliente dovrebbe essere trovato", cliente);
        assertEquals("Il nome non corrisponde", "Mario", cliente.getNome());
        // Aggiungi asserzioni per i nuovi campi
        assertEquals("La data di inizio abbonamento non corrisponde", LocalDate.of(2024, 1, 1), cliente.getInizioAbbonamento());
        assertEquals("La data di fine abbonamento non corrisponde", LocalDate.of(2024, 12, 31), cliente.getFineAbbonamento());
        assertTrue("Il pagamento dovrebbe essere effettuato", cliente.getPagamentoEffettuato());
        assertEquals("La composizione abbonamento non corrisponde", "Sala Pesi", cliente.getComposizioneAbbonamento());
    }


    @Test
    public void testInsertCliente() {
        // Creazione di un nuovo cliente con i campi aggiornati
        Cliente nuovoCliente = new Cliente("BNCLCU80A01H501Z", "Luca", "Bianchi", "M", false, "luca.b@email.com", 
                                            "Trimestrale", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 9, 30), 
                                            false, "Piscina + Sala Pesi");
        assertTrue("L'inserimento dovrebbe ritornare true", clienteDAO.insertCliente(nuovoCliente));

        Cliente clienteInserito = clienteDAO.selectCliente(nuovoCliente);
        assertNotNull("Il cliente inserito non dovrebbe essere null", clienteInserito);
        assertEquals("Il nome del cliente inserito non corrisponde", "Luca", clienteInserito.getNome());
        assertEquals("Il numero totale di clienti dovrebbe essere 3", 3, clienteDAO.selectAll().size());
        // Aggiungi asserzioni per i nuovi campi
        assertEquals("La data di inizio abbonamento non corrisponde", LocalDate.of(2024, 7, 1), clienteInserito.getInizioAbbonamento());
        assertEquals("La data di fine abbonamento non corrisponde", LocalDate.of(2024, 9, 30), clienteInserito.getFineAbbonamento());
        assertEquals("Il pagamento non dovrebbe essere effettuato", false, clienteInserito.getPagamentoEffettuato());
        assertEquals("La composizione abbonamento non corrisponde", "Piscina + Sala Pesi", clienteInserito.getComposizioneAbbonamento());
    }
    
    @Test
    public void testUpdateCliente() {
        // Creazione di un cliente da modificare con i campi aggiornati
        Cliente clienteDaModificare = new Cliente("RSSMRA85A01H501U", "Mario", "Rossi", "M", false, "nuova.email@provider.com", 
                                                    "Annuale", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 
                                                    true, "Sala Pesi + Corsi"); // Modifica la composizione
        assertTrue("L'aggiornamento dovrebbe ritornare true", clienteDAO.updateCliente(clienteDaModificare));
        
        Cliente clienteModificato = clienteDAO.selectCliente(clienteDaModificare);
        assertEquals("Il contatto dovrebbe essere stato aggiornato", "nuova.email@provider.com", clienteModificato.getContatto());
        // Aggiungi asserzioni per i nuovi campi
        assertEquals("La data di inizio abbonamento non dovrebbe cambiare", LocalDate.of(2024, 1, 1), clienteModificato.getInizioAbbonamento());
        assertEquals("La data di fine abbonamento non dovrebbe cambiare", LocalDate.of(2024, 12, 31), clienteModificato.getFineAbbonamento());
        assertTrue("Il pagamento dovrebbe essere effettuato", clienteModificato.getPagamentoEffettuato());
        assertEquals("La composizione abbonamento dovrebbe essere stata aggiornata", "Sala Pesi + Corsi", clienteModificato.getComposizioneAbbonamento());
    }
    
    @Test
    public void testDeleteCliente() {
        Cliente clienteDaCancellare = new Cliente();
        clienteDaCancellare.setCf("RSSMRA85A01H501U");
        
        assertTrue("La cancellazione dovrebbe ritornare true", clienteDAO.deleteCliente(clienteDaCancellare));
        
        Cliente clienteCancellato = clienteDAO.selectCliente(clienteDaCancellare);
        assertNull("Il cliente cancellato dovrebbe essere null", clienteCancellato);
        assertEquals("Dovrebbe rimanere 1 solo cliente", 1, clienteDAO.selectAll().size());
    }
}