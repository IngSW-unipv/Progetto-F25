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
                         "ABBONAMENTO VARCHAR(20) NOT NULL)";
            stmt.executeUpdate(sql);
        }
    }

    //Metodo eseguito prima di ogni singolo test, pulisce la tabella e inserisce dati di test sempre uguali per ogni test
    @Before
    public void setUp() throws Exception {
        clienteDAO = new ClienteDAO(testConnectionFactory);

        try (Connection conn = testConnectionFactory.createConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM CLIENTI");
            stmt.executeUpdate("INSERT INTO CLIENTI VALUES ('RSSMRA85A01H501U', 'Mario', 'Rossi', 'M', 0, 'mario.rossi@email.com', 'Annuale')");
            stmt.executeUpdate("INSERT INTO CLIENTI VALUES ('VRDNCL90B15F612N', 'Carla', 'Verdi', 'F', 0, 'carla.verdi@email.com', 'Mensile')");
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
        Cliente cliente = clienteDAO.selectCliente("RSSMRA85A01H501U");
        assertNotNull("Il cliente dovrebbe essere trovato", cliente);
        assertEquals("Il nome non corrisponde", "Mario", cliente.getNome());
    }


    @Test
    public void testInsertCliente() {
        Cliente nuovoCliente = new Cliente("BNCLCU80A01H501Z", "Luca", "Bianchi", "M", false, "luca.b@email.com", "Trimestrale");
        assertTrue("L'inserimento dovrebbe ritornare true", clienteDAO.insertCliente(nuovoCliente));

        Cliente clienteInserito = clienteDAO.selectCliente("BNCLCU80A01H501Z");
        assertNotNull("Il cliente inserito non dovrebbe essere null", clienteInserito);
        assertEquals("Il nome del cliente inserito non corrisponde", "Luca", clienteInserito.getNome());
        assertEquals("Il numero totale di clienti dovrebbe essere 3", 3, clienteDAO.selectAll().size());
    }
    
    @Test
    public void testUpdateCliente() {
        Cliente clienteDaModificare = new Cliente("RSSMRA85A01H501U", "Mario", "Rossi", "M", false, "nuova.email@provider.com", "Annuale");
        assertTrue("L'aggiornamento dovrebbe ritornare true", clienteDAO.updateCliente(clienteDaModificare));
        
        Cliente clienteModificato = clienteDAO.selectCliente("RSSMRA85A01H501U");
        assertEquals("Il contatto dovrebbe essere stato aggiornato", "nuova.email@provider.com", clienteModificato.getContatto());
    }
    
    @Test
    public void testDeleteCliente() {
        assertTrue("La cancellazione dovrebbe ritornare true", clienteDAO.deleteCliente("RSSMRA85A01H501U"));
        
        Cliente clienteCancellato = clienteDAO.selectCliente("RSSMRA85A01H501U");
        assertNull("Il cliente cancellato dovrebbe essere null", clienteCancellato);
        assertEquals("Dovrebbe rimanere 1 solo cliente", 1, clienteDAO.selectAll().size());
    }
}
