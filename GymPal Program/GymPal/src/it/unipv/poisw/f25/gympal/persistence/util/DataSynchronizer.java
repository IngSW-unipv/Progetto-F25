package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.jdbcx.JdbcDataSource;

public class DataSynchronizer {

    private final IConnectionFactory primaryFactory; // Per connettersi a MySQL
   // private final JdbcDataSource localH2DataSource;  // Per connettersi a H2
    private final FailoverConnectionFactoryProxy proxy;
    private final String schemaToSync = "gympal";
    
    //Costruttore, per sincronizzare ho bisogno che MySQL sia online e che H2 sia inizializzato 
    public DataSynchronizer(IConnectionFactory primaryFactory, FailoverConnectionFactoryProxy proxy) {
        this.primaryFactory = primaryFactory;
        //this.localH2DataSource = localH2DataSource;
        this.proxy = proxy;
    }

    
    //Esegue la sincronizzazione da MySQL a H2 se MySQL è disponibile. 
    public void synchronizeMySQLToH2OnStartup() {
        System.out.println("Tentativo di sincronizzazione dati da MySQL a H2 all'avvio...");
        
        //try-with-resources, non ho bisogno di invocare closeConnection
        try (Connection mySQLConnectionForCheck = primaryFactory.createConnection(schemaToSync)) {
            if (primaryFactory.isOpen(mySQLConnectionForCheck)) {
                System.out.println("MySQL è online. Avvio della sincronizzazione dei dati...");
                
                // Sincronizza DIPENDENTI
                synchronizeTable(
                        "DIPENDENTI",
                        "SELECT STAFF_ID, NOME, COGNOME, CONTATTO FROM DIPENDENTI",
                        "INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES (?, ?, ?, ?)",
                        new String[]{"STAFF_ID", "NOME", "COGNOME", "CONTATTO"}
                );

                // Sincronizza CLIENTI
                synchronizeTable(
                        "CLIENTI",
                        "SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO FROM CLIENTI",
                        "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        new String[]{"CF", "NOME", "COGNOME", "SESSO", "FLAG_MINOR", "CONTATTO", "ABBONAMENTO"}
                );

                // Sincronizza CALENDARIO
                synchronizeTable(
                        "CALENDARIO",
                        "SELECT NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO FROM CALENDARIO",
                        "INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES (?, ?, ?, ?)",
                        new String[]{"NOME_EVENTO", "DATA_EVENTO", "MESSAGGIO", "DESTINATARIO_MESSAGGIO"}
                );

                System.out.println("Sincronizzazione dati da MySQL a H2 completata con successo");
            } else {
                System.out.println("MySQL sembra essere offline (connessione non aperta). Sincronizzazione saltata");
            }
        } catch (SQLException e) {
            System.err.println("MySQL è offline o si è verificato un errore durante il check: " + e.getMessage());
            System.out.println("Sincronizzazione dati da MySQL a H2 saltata. H2 opererà con i dati esistenti (se presenti)");
        } catch (Exception e) {
            System.err.println("Errore imprevisto durante il tentativo di sincronizzazione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
     //Metodo per sincronizzare una singola tabella.
     //Legge tutte le righe dalla tabella sorgente (MySQL) e le inserisce nella tabella destinazione (H2).
     //La tabella destinazione viene prima svuotata (strategia "clear and insert").
   
    private void synchronizeTable(String tableName, String selectAllQuery, String insertQuery, String[] columnNames) {
        System.out.println("Sincronizzazione table '" + tableName + "'...");
        final int BATCH_SIZE = 100; //Non faccio un insert riga per riga, passo una batch
        int totalRowsProcessed = 0;
        
        JdbcDataSource localH2DataSource = this.proxy.getLocalH2DataSource();
        
      //Try-with-resources, non ho bisogno di invocare closeConnection
        try (Connection primaryConn = primaryFactory.createConnection(schemaToSync);
             Connection localConn = localH2DataSource.getConnection();
             Statement primaryStmt = primaryConn.createStatement();
             PreparedStatement localInsertStmt = localConn.prepareStatement(insertQuery)) {
        	
        	//Disabilito la modalità autocommit di H2
            localConn.setAutoCommit(false); 

            //Svuoto la tabella locale H2 prima di inserire nuovi dati
            try (Statement localDeleteStmt = localConn.createStatement()) {
                localDeleteStmt.executeUpdate("DELETE FROM " + tableName);
            }

            //Blocco try-with-resources (chiude rs alla fine del blocco) che legge da MySQL e inserisce in H2 usando PreparedStatement e batching
            try (ResultSet rs = primaryStmt.executeQuery(selectAllQuery)) {
                while (rs.next()) {
                	//Itero nella riga attuale di rs le varie colonne
                    for (int i = 0; i < columnNames.length; i++) {
                        //Copio il valore di una singola cella da MySQL a H2
                    	//rs.getObject(columnNames[i]) prende il valore, restituito come oggetto, alla riga attuale puntata da rs nella colonna i
                        //localInsertStmt.setObject(i + 1,...) inserisce nel ? i+1 dell'insert il valore di rs
                    	localInsertStmt.setObject(i + 1, rs.getObject(columnNames[i]));
                    }
                    //dopo aver associato l'insert coi i valori dei ? aggiungop tutto alla batch, incremento il counter delle rows processate
                    localInsertStmt.addBatch();
                    totalRowsProcessed++;
                    // se il numero di righe processate è un multiplo di 100 invio tutte le 100 insert a H2
                    if (totalRowsProcessed % BATCH_SIZE == 0) {
                        localInsertStmt.executeBatch();
                        System.out.println("  - Inserito batch di " + BATCH_SIZE + " righe in " + tableName);
                    }
                }
                //eseguo l'ultimo batch con le rige rimanenti
                if (totalRowsProcessed % BATCH_SIZE != 0 && totalRowsProcessed > 0) {
                    localInsertStmt.executeBatch();
                }
            }
            //Commit della transazione su H2
            localConn.commit(); 
            System.out.println("Tabella '" + tableName + "' sincronizzata con successo. Righe processate: " + totalRowsProcessed);

        } catch (SQLException e) {
            System.err.println("ERRORE durante la sincronizzazione della tabella '" + tableName + "': " + e.getMessage());
            e.printStackTrace();
            //Rollback sulla connessione H2 se possibile e se è attiva, chiude localConn e ripristina H2 in caso di errori
            try (Connection localConnForRollback = localH2DataSource.getConnection()) { 
                if (localConnForRollback != null && !localConnForRollback.getAutoCommit()) {
                    System.err.println("Tentativo di rollback per la tabella '" + tableName + "' su H2.");
                    localConnForRollback.rollback();
                }
            } catch (SQLException exRollback) {
                 System.err.println("Errore durante il tentativo di rollback per H2: " + exRollback.getMessage());
            }
        }
    }
}