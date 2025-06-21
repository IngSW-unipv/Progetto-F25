package it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

// Classe di test per TurnoDAO
// Utilizza un database H2 in-memory per isolare i test 
public class TurnoDAOTest {

    private static IConnectionFactory testConnectionFactory;
    private static JdbcDataSource dataSource;
    private ITurnoDAO turnoDAO;

    // Una ConnectionFactory specifica per i test
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

    // Metodo eseguito una sola volta prima di tutti i test della classe
    @BeforeClass
    public static void setUpClass() throws Exception {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        testConnectionFactory = new H2TestConnectionFactory(dataSource);

        // Creazione dello schema della tabella TURNI
        try (Connection conn = testConnectionFactory.createConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE TURNI (" +
                         "DATA DATE PRIMARY KEY, " +
                         "REC_MAT VARCHAR(255), " +
                         "REC_POM VARCHAR(255), " +
                         "PT_MAT VARCHAR(255), " +
                         "PT_POM VARCHAR(255))";
            stmt.executeUpdate(sql);
        }
    }

    // Metodo eseguito prima di ogni singolo test, pulisce la tabella e inserisce dati di test
    @Before
    public void setUp() throws Exception {
        turnoDAO = new TurnoDAO(testConnectionFactory);
        LocalDate oggi = LocalDate.now();
        LocalDate ieri = oggi.minusDays(1);
        LocalDate domani = oggi.plusDays(1);
        LocalDate prossimaSettimana = oggi.plusDays(7);

        try (Connection conn = testConnectionFactory.createConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM TURNI");
            stmt.executeUpdate("INSERT INTO TURNI VALUES ('" + oggi + "', 'REC01', 'REC02', 'PT01', 'PT02')");
            stmt.executeUpdate("INSERT INTO TURNI VALUES ('" + domani + "', 'REC01', NULL, 'PT03', NULL)");
            stmt.executeUpdate("INSERT INTO TURNI VALUES ('" + prossimaSettimana + "', 'REC02', 'REC01', 'PT02', 'PT01')");
            // Inserisce un turno vecchio per testare la cancellazione
            stmt.executeUpdate("INSERT INTO TURNI VALUES ('" + ieri + "', 'REC03', 'REC03', 'PT03', 'PT03')");
        }
    }

    @Test
    public void testSelectAll() {
        List<Turno> turni = turnoDAO.selectAll();
        assertEquals("Dovrebbero esserci 4 turni in totale", 4, turni.size());
    }
    
    @Test
    public void testSelectTurnoFound() {
        // Per la ricerca creo un oggetto con la chiave primaria del turno desiderato
        Turno turnoDaCercare = new Turno(LocalDate.now(), null, null, null, null);
        Turno turnoTrovato = turnoDAO.selectTurno(turnoDaCercare);
        assertNotNull("Il turno dovrebbe essere trovato", turnoTrovato);
        assertEquals("L'ID del receptionist del mattino non corrisponde", "REC01", turnoTrovato.getRecMat());
    }

    @Test
    public void testSelectTurnoNotFound() {
        // Anche per cercare un turno non esistente, uso un oggetto Turno
        Turno turnoDaCercare = new Turno(LocalDate.now().minusYears(1), null, null, null, null);
        Turno turnoTrovato = turnoDAO.selectTurno(turnoDaCercare);
        assertNull("Il turno non dovrebbe essere trovato", turnoTrovato);
    }

    @Test
    public void testSelectAllTurniByPerson() {
        // Creo un oggetto Turno "dummy" con solo l'ID di uno staff per la ricerca
        Turno turnoConPersona = new Turno();
        turnoConPersona.setRecMat("REC01"); // Cerca tutti i turni di REC01

        List<Turno> turniPersona = turnoDAO.selectAllTurniByPerson(turnoConPersona);
        assertEquals("Ci dovrebbero essere 3 turni per REC01", 3, turniPersona.size());
    }

    @Test
    public void testSelectAllTurniByRange() {
        LocalDate oggi = LocalDate.now();
        LocalDate traUnaSettimana = oggi.plusDays(7);
        
        // Creo due oggetti Turno "dummy" per l'intervallo di date
        Turno turnoInizio = new Turno(oggi, null, null, null, null);
        Turno turnoFine = new Turno(traUnaSettimana, null, null, null, null);

        List<Turno> eventi = turnoDAO.selectAllTurniByRange(turnoInizio, turnoFine);
        assertEquals("Ci dovrebbero essere 3 turni nell'intervallo di una settimana", 3, eventi.size());
    }

    @Test
    public void testInsertTurno() {
        LocalDate nuovaData = LocalDate.now().plusMonths(1);
        Turno nuovoTurno = new Turno(nuovaData, "REC04", "REC05", "PT04", "PT05");
        assertTrue("L'inserimento del turno dovrebbe ritornare true", turnoDAO.insertTurno(nuovoTurno));
        
        // Per la verifica si usa l'oggetto evento, come richiesto dalla nuova interfaccia
        Turno turnoInserito = turnoDAO.selectTurno(nuovoTurno);
        assertNotNull("Il turno inserito non dovrebbe essere null", turnoInserito);
        assertEquals("L'ID del personal trainer del pomeriggio non corrisponde", "PT05", turnoInserito.getPtPom());
    }

    @Test
    public void testInsertTurnoFailsOnDuplicateKey() {
        // Tento di inserire un turno con una data che esiste già (chiave primaria duplicata)
        LocalDate oggi = LocalDate.now();
        Turno turnoDuplicato = new Turno(oggi, "REC06", "REC07", "PT06", "PT07");
        
        // L'inserimento dovrebbe fallire e ritornare false a causa del vincolo di chiave primaria
        boolean risultato = turnoDAO.insertTurno(turnoDuplicato);
        assertFalse("L'inserimento di un turno con chiave duplicata dovrebbe fallire", risultato);
    }

    @Test
    public void testUpdateTurno() {
        LocalDate domani = LocalDate.now().plusDays(1);
        // L'evento da modificare viene identificato dalla data
        Turno turnoDaModificare = new Turno(domani, "REC99", "REC98", "PT99", "PT98");
        assertTrue("L'aggiornamento del turno dovrebbe ritornare true", turnoDAO.updateTurno(turnoDaModificare));
        
        Turno chiaveRicerca = new Turno(domani, null, null, null, null);
        Turno turnoModificato = turnoDAO.selectTurno(chiaveRicerca);
        assertNotNull("Il turno modificato non dovrebbe essere null", turnoModificato); 
        assertEquals("L'ID del receptionist del mattino dovrebbe essere stato aggiornato", "REC99", turnoModificato.getRecMat());
    }

    @Test
    public void testDeleteTurno() {
        LocalDate domani = LocalDate.now().plusDays(1);
        // Per cancellare, creo un oggetto Turno che contiene la chiave primaria
        Turno turnoDaCancellare = new Turno(domani, null, null, null, null);
        assertTrue("La cancellazione dovrebbe ritornare true", turnoDAO.deleteTurno(turnoDaCancellare));
        
        // Verifico l'avvenuta cancellazione cercando lo stesso turno
        Turno turnoCancellato = turnoDAO.selectTurno(turnoDaCancellare);
        assertNull("Il turno cancellato dovrebbe essere null", turnoCancellato);
    }

    @Test
    public void testDeleteOldTurni() {
        // Prima della cancellazione, verifico che il turno di ieri esista
        Turno turnoIeri = turnoDAO.selectTurno(new Turno(LocalDate.now().minusDays(1), null, null, null, null));
        assertNotNull("Il turno di ieri dovrebbe esistere prima della cancellazione", turnoIeri);

        // Eseguo la cancellazione
        assertTrue("La cancellazione dei vecchi turni dovrebbe ritornare true", turnoDAO.deleteOldTurni());
        
        // Verifico che il turno di ieri sia stato cancellato
        turnoIeri = turnoDAO.selectTurno(new Turno(LocalDate.now().minusDays(1), null, null, null, null));
        assertNull("Il turno di ieri dovrebbe essere stato cancellato", turnoIeri);

        // Verifico che i turni futuri esistano ancora
        List<Turno> turniRimanenti = turnoDAO.selectAll();
        assertEquals("Dovrebbero rimanere 3 turni", 3, turniRimanenti.size());
    }
}
