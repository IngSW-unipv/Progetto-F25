package it.unipv.poisw.f25.gympal.persistence.setup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;


 //Classe di test per DataSynchronizer.
 //Questo test verifica che la classe sia in grado di copiare correttamente i dati da un database sorgente a un database di destinazione
 //Utilizza due database H2 in-memory

public class DataSynchronizerTest {

    // Simula il database sorgente (MySQL)
    private JdbcDataSource sourceDataSource;

    // Simula il database di destinazione (H2)
    private JdbcDataSource destDataSource;


    //Metodo di setup eseguito da JUnit prima di ogni test, prepara due database H2 in-memory, crea gli schemi e popola il db sorgente

    @Before
    public void setupDatabases() throws SQLException {
        System.out.println("INIZIO TEST -> DataSynchronizerTest");

        //Configura il DataSource sorgente in-memory
        sourceDataSource = new JdbcDataSource();

        sourceDataSource.setURL("jdbc:h2:mem:sourceDB;DB_CLOSE_DELAY=-1;MODE=MySQL");
        sourceDataSource.setUser("sa");
        sourceDataSource.setPassword("");

        //Configura il DataSource di destinazione in-memory
        destDataSource = new JdbcDataSource();
        destDataSource.setURL("jdbc:h2:mem:destDB;DB_CLOSE_DELAY=-1;MODE=MySQL");
        destDataSource.setUser("sa");
        destDataSource.setPassword("");

        //Crea le tabelle nel DB SORGENTE e inserisce i dati di test
        try (Connection conn = sourceDataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Aggiornato lo schema della tabella DIPENDENTI
            stmt.execute("CREATE TABLE DIPENDENTI (STAFF_ID VARCHAR(50) PRIMARY KEY, NOME VARCHAR(50), COGNOME VARCHAR(50), CONTATTO VARCHAR(100))");
            // Aggiornato lo schema della tabella CLIENTI per includere le nuove colonne
            stmt.execute("CREATE TABLE CLIENTI (CF VARCHAR(16) PRIMARY KEY, NOME VARCHAR(50), COGNOME VARCHAR(50), SESSO CHAR(1), FLAG_MINOR TINYINT, CONTATTO VARCHAR(100), ABBONAMENTO VARCHAR(50), INIZIO_ABBONAMENTO DATE, FINE_ABBONAMENTO DATE, PAGAMENTO_EFFETTUATO BOOLEAN, COMPOSIZIONE_ABBONAMENTO VARCHAR(255))");
            // Aggiornato lo schema della tabella CALENDARIO
            stmt.execute("CREATE TABLE CALENDARIO (NOME_EVENTO VARCHAR(100), DATA_EVENTO DATE, ORA_INIZIO TIME, ORA_FINE TIME, MESSAGGIO TEXT, DESTINATARIO_MESSAGGIO VARCHAR(100), PRIMARY KEY (NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE))");

            // Inserisci dati completi e realistici per il test
            String insertClientSQL = "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertClientSQL)) {
                ps.setString(1, "MRORSS80A01Z123X");
                ps.setString(2, "Mario");
                ps.setString(3, "Rossi");
                ps.setString(4, "M");
                ps.setBoolean(5, false); // FLAG_MINOR (isMinorenne)
                ps.setString(6, "mario.rossi@example.com");
                ps.setString(7, "Mensile Base");
                ps.setObject(8, LocalDate.of(2025, 1, 1)); // Inizio Abbonamento
                ps.setObject(9, LocalDate.of(2025, 1, 31)); // Fine Abbonamento
                ps.setBoolean(10, true); // Pagamento Effettuato
                ps.setString(11, "Accesso sala pesi, corsi collettivi");
                ps.executeUpdate();

                ps.setString(1, "VNRBRD75B02Y456W");
                ps.setString(2, "Anna");
                ps.setString(3, "Verdi");
                ps.setString(4, "F");
                ps.setBoolean(5, false);
                ps.setString(6, "anna.verdi@example.com");
                ps.setString(7, "Annuale Premium");
                ps.setObject(8, LocalDate.of(2025, 2, 1));
                ps.setObject(9, LocalDate.of(2026, 1, 31));
                ps.setBoolean(10, true);
                ps.setString(11, "Accesso illimitato, personal trainer");
                ps.executeUpdate();
            }

            // Inserisci dati per DIPENDENTI
            stmt.execute("INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES ('EMP001', 'Luca', 'Bianchi', 'luca.bianchi@gympal.com')");

            // Inserisci dati per CALENDARIO (esempio)
            stmt.execute("INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES ('Corso Yoga', '2025-06-15', '10:00:00', '11:00:00', 'Corso di Yoga base', 'Tutti i membri')");
        }

        // 4. Crea le tabelle nel DB DESTINAZIONE (vuote o con dati "vecchi" da sovrascrivere)
        try (Connection conn = destDataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Aggiornato lo schema della tabella DIPENDENTI
            stmt.execute("CREATE TABLE DIPENDENTI (STAFF_ID VARCHAR(50) PRIMARY KEY, NOME VARCHAR(50), COGNOME VARCHAR(50), CONTATTO VARCHAR(100))");
            // Aggiornato lo schema della tabella CLIENTi
            stmt.execute("CREATE TABLE CLIENTI (CF VARCHAR(16) PRIMARY KEY, NOME VARCHAR(50), COGNOME VARCHAR(50), SESSO CHAR(1), FLAG_MINOR TINYINT, CONTATTO VARCHAR(100), ABBONAMENTO VARCHAR(50), INIZIO_ABBONAMENTO DATE, FINE_ABBONAMENTO DATE, PAGAMENTO_EFFETTUATO BOOLEAN, COMPOSIZIONE_ABBONAMENTO VARCHAR(255))");
            // Aggiornato lo schema della tabella CALENDARIO
            stmt.execute("CREATE TABLE CALENDARIO (NOME_EVENTO VARCHAR(100), DATA_EVENTO DATE, ORA_INIZIO TIME, ORA_FINE TIME, MESSAGGIO TEXT, DESTINATARIO_MESSAGGIO VARCHAR(100), PRIMARY KEY (NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE))");
            stmt.execute("CREATE TABLE SINCRO_INFO (CHIAVE VARCHAR(255) PRIMARY KEY, VALORE VARCHAR(255))");

            // Inseriamo un dato "vecchio" per verificare che la sincronizzazione lo cancelli
            stmt.execute("INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO) VALUES ('DTALGH65C03Z789X', 'Dante', 'Alighieri', 'M', FALSE, 'dante@example.com', 'Mensile', '2024-01-01', '2024-01-31', TRUE, 'Solo sala pesi')");
        }
    }


    @Test
    public void testFullSynchronization() throws SQLException {
        //Crea una ConnectionFactory finta che punta al nostro H2 sorgente di test
        IConnectionFactory sourceFactory = new H2TestConnectionFactory(sourceDataSource);

        //Crea l'istanza di DataSynchronizer da testare
        DataSynchronizer synchronizer = new DataSynchronizer(sourceFactory, destDataSource);


        synchronizer.synchronizeMySQLToH2OnStartup();

        //Verifica i risultati sul database di destinazione
        try (Connection conn = destDataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Verifica che i dati vecchi siano stati cancellati e i nuovi inseriti
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM CLIENTI")) {
                assertTrue("Il ResultSet dovrebbe avere un risultato", rs.next());
                int rowCount = rs.getInt(1);
                assertEquals("La tabella CLIENTI di destinazione dovrebbe avere 2 righe", 2, rowCount);
            }

            // Verifica la correttezza di un dato specifico e delle nuove colonne
            try (ResultSet rs = stmt.executeQuery("SELECT NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO FROM CLIENTI WHERE CF = 'MRORSS80A01Z123X'")) {
                assertTrue("Dovrebbe esistere un cliente con CF MRORSS80A01Z123X", rs.next());
                assertEquals("Il nome dovrebbe essere 'Mario'", "Mario", rs.getString("NOME"));
                assertEquals("Il cognome dovrebbe essere 'Rossi'", "Rossi", rs.getString("COGNOME"));
                assertEquals("Il sesso dovrebbe essere 'M'", "M", rs.getString("SESSO"));
                assertEquals("isMinorenne dovrebbe essere false", 0, rs.getInt("FLAG_MINOR"));
                assertEquals("Il contatto dovrebbe essere 'mario.rossi@example.com'", "mario.rossi@example.com", rs.getString("CONTATTO"));
                assertEquals("L'abbonamento dovrebbe essere 'Mensile Base'", "Mensile Base", rs.getString("ABBONAMENTO"));
                assertEquals("L'inizio abbonamento dovrebbe essere 2025-01-01", LocalDate.of(2025, 1, 1), rs.getObject("INIZIO_ABBONAMENTO", LocalDate.class));
                assertEquals("La fine abbonamento dovrebbe essere 2025-01-31", LocalDate.of(2025, 1, 31), rs.getObject("FINE_ABBONAMENTO", LocalDate.class));
                assertTrue("Pagamento Effettuato dovrebbe essere true", rs.getBoolean("PAGAMENTO_EFFETTUATO"));
                assertEquals("La composizione abbonamento dovrebbe essere corretta", "Accesso sala pesi, corsi collettivi", rs.getString("COMPOSIZIONE_ABBONAMENTO"));
            }

            // Verifica che anche DIPENDENTI sia stato sincronizzato (count base)
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM DIPENDENTI")) {
                assertTrue("Il ResultSet dovrebbe avere un risultato", rs.next());
                int rowCount = rs.getInt(1);
                assertEquals("La tabella DIPENDENTI di destinazione dovrebbe avere 1 riga", 1, rowCount);
            }

            // Verifica che il timestamp della sincronizzazione sia stato scritto
            try (ResultSet rs = stmt.executeQuery("SELECT VALORE FROM SINCRO_INFO WHERE CHIAVE = 'LAST_SYNC_TIMESTAMP'")) {
                assertTrue("La tabella SINCRO_INFO dovrebbe contenere il timestamp", rs.next());
                assertNotNull("Il valore del timestamp non deve essere null", rs.getString("VALORE"));
            }

            System.out.println("FINE TEST -> DataSynchronizerTest");
        }
    }

   //Implementa IConnectionFactory per fornire connessioni al nostro DataSource H2 di test,simulando il comportamento di MySQLConnectionFactory

    private static class H2TestConnectionFactory implements IConnectionFactory {
        private final JdbcDataSource dataSource;

        public H2TestConnectionFactory(JdbcDataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public Connection createConnection() throws SQLException {
            return dataSource.getConnection();
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
                    e.printStackTrace();
                }
            }
        }

        @Override
        public boolean isReadOnlyMode() {
            return false;
        }
    }
}