package it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean;

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

//Classe di test di integrazione per DipendenteDAO.
//Utilizza un database H2 in-memory per isolare i test 
public class DipendenteDAOTest {
	
	private static IConnectionFactory testConnectionFactory;
	private static JdbcDataSource dataSource;
	private IDipendenteDAO dipendenteDAO;
	
	public static class H2TestConnectionFactory implements IConnectionFactory{
		
		private final JdbcDataSource ds;

		public H2TestConnectionFactory(JdbcDataSource ds) {
			this.ds = ds;
		}
		
		@Override
		public Connection createConnection() throws SQLException{
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
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        testConnectionFactory = new H2TestConnectionFactory(dataSource);

        // Creazione dello schema della tabella DIPENDENTI
        try (Connection conn = testConnectionFactory.createConnection(); 
        	 Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE DIPENDENTI (" +
                         "STAFF_ID VARCHAR(50) NOT NULL PRIMARY KEY, " +
                         "NOME VARCHAR(50) NOT NULL, " +
                         "COGNOME VARCHAR(50) NOT NULL, " +
                         "CONTATTO VARCHAR(100) NOT NULL)";
            stmt.executeUpdate(sql);
        }
    }
	 
	 //Metodo eseguito prima di ogni singolo test, pulisce la tabella e inserisce dati di test sempre uguali per ogni test
	 @Before
	 public void setUp() throws Exception {
	     dipendenteDAO = new DipendenteDAO(testConnectionFactory);

	     try (Connection conn = testConnectionFactory.createConnection(); Statement stmt = conn.createStatement()) {
	         stmt.executeUpdate("DELETE FROM DIPENDENTI");
	         stmt.executeUpdate("INSERT INTO DIPENDENTI VALUES ('ID_TEST_01', 'Mario', 'Rossi', 'mario.rossi@email.com')");
	         stmt.executeUpdate("INSERT INTO DIPENDENTI VALUES ('ID_TEST_02', 'Anna', 'Bianchi', 'anna.bianchi@email.com')");
	     }
	 }
	
	@Test
    public void testSelectAll() {
        List<Dipendente> dipendenti = dipendenteDAO.selectAll();
        assertNotNull("La lista non deve essere null", dipendenti);
        assertEquals("Dovrebbero esserci 2 dipendenti", 2, dipendenti.size());
    }

    @Test
    public void testSelectDipendenteFound() {
        Dipendente dipendente = dipendenteDAO.selectDipendente("ID_TEST_01");
        assertNotNull("Il dipendente dovrebbe essere trovato", dipendente);
        assertEquals("Il nome non corrisponde", "Mario", dipendente.getNome());
    }


    @Test
    public void testInsertDipendente() {
        Dipendente nuovoDipendente = new Dipendente("ID_TEST_03", "Luca", "Verdi", "luca.verdi@email.com");
        assertTrue("L'inserimento dovrebbe ritornare true", dipendenteDAO.insertDipendente(nuovoDipendente));

        Dipendente dipendenteInserito = dipendenteDAO.selectDipendente("ID_TEST_03");
        assertNotNull("Il dipendente inserito non dovrebbe essere null", dipendenteInserito);
        assertEquals("Il nome del dipendente inserito non corrisponde", "Luca", dipendenteInserito.getNome());
        assertEquals("Il numero totale di dipendenti dovrebbe essere 3", 3, dipendenteDAO.selectAll().size());
    }
    
    @Test
    public void testUpdateDipendente() {
        Dipendente dipendenteDaModificare = new Dipendente("ID_TEST_01", "Mario", "Rossi", "mario.rossi.new@email.com");
        assertTrue("L'aggiornamento dovrebbe ritornare true", dipendenteDAO.updateDipendente(dipendenteDaModificare));
        
        Dipendente dipendenteModificato = dipendenteDAO.selectDipendente("ID_TEST_01");
        assertEquals("Il contatto dovrebbe essere stato aggiornato", "mario.rossi.new@email.com", dipendenteModificato.getContatto());
    }
    
    @Test
    public void testDeleteDipendente() {
        assertTrue("La cancellazione dovrebbe ritornare true", dipendenteDAO.deleteDipendente("ID_TEST_01"));
        
        Dipendente dipendenteCancellato = dipendenteDAO.selectDipendente("ID_TEST_01");
        assertNull("Il dipendente cancellato dovrebbe essere null", dipendenteCancellato);
        assertEquals("Dovrebbe rimanere 1 solo dipendente", 1, dipendenteDAO.selectAll().size());
    }

}
