package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.List;

import org.h2.jdbcx.JdbcDataSource;

import it.unipv.poisw.f25.gympal.persistence.util.IConnectionFactory;

//Classe di test per CalendarioDAO
//Utilizza un database H2 in-memory per isolare i test 
public class CalendarioDAOTest {

    private static IConnectionFactory testConnectionFactory;
    private static JdbcDataSource dataSource;
    private ICalendarioDAO calendarioDAO;

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
                } catch (SQLException e) {
                    // Ignora nel contesto di test
                }
            }
        }
        
        @Override
        public boolean isReadOnlyMode() {
            // Nei test, la scrittura è sempre abilitata.
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

        // Creazione dello schema della tabella CALENDARIO
        try (Connection conn = testConnectionFactory.createConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE CALENDARIO (" +
                         "NOME_EVENTO VARCHAR(100) NOT NULL, " +
                         "DATA_EVENTO DATE NOT NULL, " +
                         "MESSAGGIO TEXT NOT NULL, " +
                         "DESTINATARIO_MESSAGGIO VARCHAR(100) NOT NULL, " +
                         "PRIMARY KEY (NOME_EVENTO, DATA_EVENTO))";
            stmt.executeUpdate(sql);
        }
    }

    //Metodo eseguito prima di ogni singolo test, pulisce la tabella e inserisce dati di test sempre uguali per ogni test
    @Before
    public void setUp() throws Exception {
        calendarioDAO = new CalendarioDAO(testConnectionFactory);
        LocalDate oggi = LocalDate.now();

        try (Connection conn = testConnectionFactory.createConnection(); 
        	 Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM CALENDARIO");
            stmt.executeUpdate("INSERT INTO CALENDARIO VALUES ('Corso Yoga', '" + oggi + "', 'Lezione di Hatha Yoga', 'Clienti')");
            stmt.executeUpdate("INSERT INTO CALENDARIO VALUES ('Riunione Staff', '" + oggi + "', 'Riunione settimanale', 'Dipendenti')");
            stmt.executeUpdate("INSERT INTO CALENDARIO VALUES ('Manutenzione Attrezzi', '" + oggi.plusDays(5) + "', 'Manutenzione ordinaria', 'Staff Tecnico')");
        }
    }
    
    @Test
    public void testInsertEvento() {
        LocalDate domani = LocalDate.now().plusDays(1);
        Calendario nuovoEvento = new Calendario("Corso Pilates", domani, "Lezione di Pilates base", "Clienti");
        assertTrue("L'inserimento dell'evento dovrebbe ritornare true", calendarioDAO.insertEvento(nuovoEvento));
        
        Calendario eventoInserito = calendarioDAO.selectEvento("Corso Pilates", domani);
        assertNotNull("L'evento inserito non dovrebbe essere null", eventoInserito);
        assertEquals("Il messaggio non corrisponde", "Lezione di Pilates base", eventoInserito.getMessaggio());
    }

    @Test
    public void testUpdateEvento() {
        LocalDate oggi = LocalDate.now();
        Calendario eventoDaModificare = new Calendario("Corso Yoga", oggi, "Lezione di Vinyasa Yoga - Avanzato", "Clienti");
        assertTrue("L'aggiornamento dell'evento dovrebbe ritornare true", calendarioDAO.updateEvento(eventoDaModificare));
        
        Calendario eventoModificato = calendarioDAO.selectEvento("Corso Yoga", oggi);
        assertEquals("Il messaggio dovrebbe essere stato aggiornato", "Lezione di Vinyasa Yoga - Avanzato", eventoModificato.getMessaggio());
    }

    @Test
    public void testDeleteEvento() {
        LocalDate oggi = LocalDate.now();
        assertTrue("La cancellazione dovrebbe ritornare true", calendarioDAO.deleteEvento("Corso Yoga", oggi));
        
        Calendario eventoCancellato = calendarioDAO.selectEvento("Corso Yoga", oggi);
        assertNull("L'evento cancellato dovrebbe essere null", eventoCancellato);
    }

    @Test
    public void testSelectEventoFound() {
        LocalDate oggi = LocalDate.now();
        Calendario evento = calendarioDAO.selectEvento("Riunione Staff", oggi);
        assertNotNull("L'evento dovrebbe essere trovato", evento);
        assertEquals("Il destinatario non corrisponde", "Dipendenti", evento.getDestinatarioMessaggio());
    }

    @Test
    public void testSelectEventoNotFound() {
        LocalDate ieri = LocalDate.now().minusDays(1);
        Calendario evento = calendarioDAO.selectEvento("Evento Inesistente", ieri);
        assertNull("L'evento non dovrebbe essere trovato", evento);
    }

    @Test
    public void testSelectAllEventiByDate() {
        LocalDate oggi = LocalDate.now();
        List<Calendario> eventiDiOggi = calendarioDAO.selectAllEventiByDate(oggi);
        assertEquals("Ci dovrebbero essere 2 eventi per oggi", 2, eventiDiOggi.size());
    }

    @Test
    public void testSelectAllEventiByDateRange() {
        LocalDate oggi = LocalDate.now();
        LocalDate traUnaSettimana = oggi.plusDays(7);
        List<Calendario> eventi = calendarioDAO.selectAllEventiByDateRange(oggi, traUnaSettimana);
        assertEquals("Ci dovrebbero essere 3 eventi nell'intervallo di una settimana", 3, eventi.size());
    }
}