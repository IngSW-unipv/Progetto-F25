package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.h2.jdbcx.JdbcDataSource;

// Classe responsabile per la sincronizzazione dei dati da un database primario (MySQL) a un database locale di backup (H2).
public class DataSynchronizer {

    private final IConnectionFactory primaryFactory;
    private final JdbcDataSource localH2DataSource;

    //Costruttore
    public DataSynchronizer(IConnectionFactory primaryFactory, JdbcDataSource localH2DataSource) {
        if (primaryFactory == null || localH2DataSource == null) {
            throw new IllegalArgumentException("Factory e DataSource non possono essere null.");
        }
        this.primaryFactory = primaryFactory;
        this.localH2DataSource = localH2DataSource;
    }
    
    //Metodo principale che gestisce la sincronizzazione
    public void synchronizeMySQLToH2OnStartup() {
        System.out.println("Tentativo di sincronizzazione dati da MySQL a H2 all'avvio...");
        try (Connection mySQLConnectionForCheck = primaryFactory.createConnection()) {
            if (primaryFactory.isOpen(mySQLConnectionForCheck)) {
                System.out.println("MySQL è online. Avvio della sincronizzazione dei dati...");
                
                synchronizeTable("DIPENDENTI", "SELECT STAFF_ID, NOME, COGNOME, CONTATTO FROM DIPENDENTI", "INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES (?, ?, ?, ?)", new String[]{"STAFF_ID", "NOME", "COGNOME", "CONTATTO"});
                synchronizeTable("CLIENTI", "SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO FROM CLIENTI", "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?)", new String[]{"CF", "NOME", "COGNOME", "SESSO", "FLAG_MINOR", "CONTATTO", "ABBONAMENTO"});
                synchronizeTable("CALENDARIO", "SELECT NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO FROM CALENDARIO", "INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES (?, ?, ?, ?)", new String[]{"NOME_EVENTO", "DATA_EVENTO", "MESSAGGIO", "DESTINATARIO_MESSAGGIO"});
                
                //Aggiorno il timestamp dell'ultima sincronizzazione corretta
                updateLastSyncTimestamp();
                
                System.out.println("Sincronizzazione dati da MySQL a H2 completata con successo.");
            }
        } catch (SQLException e) {
            System.err.println("MySQL è offline o si è verificato un errore durante il check: " + e.getMessage());
            System.out.println("Sincronizzazione dati da MySQL a H2 saltata.");
            //Mostro lo stato del backup H2 esistente
            DatabaseInfrastructureSetup.reportLastSyncStatus(this.localH2DataSource);
        }
    }

    //Metodo per sincronizzare una singola tabella.
    private void synchronizeTable(String tableName, String selectAllQuery, String insertQuery, String[] columnNames) {
        System.out.println("Sincronizzazione tabella '" + tableName + "'...");
        final int BATCH_SIZE = 100;
        int totalRowsProcessed = 0;
        
        try (Connection primaryConn = primaryFactory.createConnection();
             Connection localConn = localH2DataSource.getConnection();
             Statement primaryStmt = primaryConn.createStatement();
             PreparedStatement localInsertStmt = localConn.prepareStatement(insertQuery)) {
            localConn.setAutoCommit(false); 
            try (Statement localDeleteStmt = localConn.createStatement()) {
                localDeleteStmt.executeUpdate("DELETE FROM " + tableName);
            }
            try (ResultSet rs = primaryStmt.executeQuery(selectAllQuery)) {
                while (rs.next()) {
                    for (int i = 0; i < columnNames.length; i++) {
                        localInsertStmt.setObject(i + 1, rs.getObject(columnNames[i]));
                    }
                    localInsertStmt.addBatch();
                    totalRowsProcessed++;

                    if (totalRowsProcessed % BATCH_SIZE == 0) {
                        localInsertStmt.executeBatch();
                    }
                }
                if (totalRowsProcessed % BATCH_SIZE != 0 && totalRowsProcessed > 0) {
                    localInsertStmt.executeBatch();
                }
            }
            localConn.commit();
            System.out.println("Tabella '" + tableName + "' sincronizzata con successo. Righe processate: " + totalRowsProcessed);

        } catch (SQLException e) {
            System.err.println("ERRORE durante la sincronizzazione della tabella '" + tableName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Metodo che aggiorna il timestamp dell'ultima sincronizzazione
    private void updateLastSyncTimestamp() {
        String timestampString = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String sql = "MERGE INTO SINCRO_INFO (CHIAVE, VALORE) KEY(CHIAVE) VALUES (?, ?)";

        System.out.println("Aggiornamento timestamp dell'ultima sincronizzazione a: " + timestampString);
        try (Connection localConn = localH2DataSource.getConnection();
             PreparedStatement stmt = localConn.prepareStatement(sql)) {
            
            stmt.setString(1, "LAST_SYNC_TIMESTAMP");
            stmt.setString(2, timestampString);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("ERRORE: Impossibile aggiornare il timestamp dell'ultima sincronizzazione su H2.");
            e.printStackTrace();
        }
    }
}