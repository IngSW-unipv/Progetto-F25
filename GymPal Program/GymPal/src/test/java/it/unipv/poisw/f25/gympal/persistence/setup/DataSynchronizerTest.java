package it.unipv.poisw.f25.gympal.persistence.setup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            stmt.execute("CREATE TABLE DIPENDENTI (STAFF_ID INT PRIMARY KEY, NOME VARCHAR(255), COGNOME VARCHAR(255), CONTATTO VARCHAR(255))");
            stmt.execute("CREATE TABLE CLIENTI (CF VARCHAR(255) PRIMARY KEY, NOME VARCHAR(255), COGNOME VARCHAR(255), SESSO CHAR(1), FLAG_MINOR BOOLEAN, CONTATTO VARCHAR(255), ABBONAMENTO VARCHAR(255))");
            stmt.execute("CREATE TABLE CALENDARIO (NOME_EVENTO VARCHAR(255), DATA_EVENTO TIMESTAMP, MESSAGGIO VARCHAR(255), DESTINATARIO_MESSAGGIO VARCHAR(255))");
            
            stmt.execute("INSERT INTO CLIENTI (CF, NOME, COGNOME) VALUES ('MRORSS80', 'Mario', 'Rossi')");
            stmt.execute("INSERT INTO CLIENTI (CF, NOME, COGNOME) VALUES ('VNRBRD75', 'Anna', 'Verdi')");
        }
        
        // 4. Crea le tabelle nel DB DESTINAZIONE (vuote o con dati "vecchi" da sovrascrivere)
        try (Connection conn = destDataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE DIPENDENTI (STAFF_ID INT PRIMARY KEY, NOME VARCHAR(255), COGNOME VARCHAR(255), CONTATTO VARCHAR(255))");
            stmt.execute("CREATE TABLE CLIENTI (CF VARCHAR(255) PRIMARY KEY, NOME VARCHAR(255), COGNOME VARCHAR(255), SESSO CHAR(1), FLAG_MINOR BOOLEAN, CONTATTO VARCHAR(255), ABBONAMENTO VARCHAR(255))");
            stmt.execute("CREATE TABLE CALENDARIO (NOME_EVENTO VARCHAR(255), DATA_EVENTO TIMESTAMP, MESSAGGIO VARCHAR(255), DESTINATARIO_MESSAGGIO VARCHAR(255))");
            stmt.execute("CREATE TABLE SINCRO_INFO (CHIAVE VARCHAR(255) PRIMARY KEY, VALORE VARCHAR(255))");

            // Inseriamo un dato "vecchio" per verificare che la sincronizzazione lo cancelli
            stmt.execute("INSERT INTO CLIENTI (CF, NOME, COGNOME) VALUES ('DTALGH65', 'Dante', 'Alighieri')");
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
            
            // Verifica la correttezza di un dato specifico
            try (ResultSet rs = stmt.executeQuery("SELECT NOME FROM CLIENTI WHERE CF = 'MRORSS80'")) {
                assertTrue("Dovrebbe esistere un cliente con CF MRORSS80", rs.next());
                assertEquals("Il nome dovrebbe essere 'Mario'", "Mario", rs.getString("NOME"));
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