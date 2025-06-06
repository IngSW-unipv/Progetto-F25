package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.h2.jdbcx.JdbcDataSource;

// Classe responsabile per la sincronizzazione dei dati da un database primario MySQL a un database locale di backup H2.
public class DataSynchronizer {

    private final IConnectionFactory primaryFactory;
    private final JdbcDataSource localH2DataSource;

    //Costruttore
    //Riceve una factory per la connessione al database primario
    //Riceve una JdbcDataSource pper la connessione ad H2
    public DataSynchronizer(IConnectionFactory primaryFactory, JdbcDataSource localH2DataSource) {
        if (primaryFactory == null || localH2DataSource == null) {
            throw new IllegalArgumentException("Factory e DataSource non possono essere null");
        }
        this.primaryFactory = primaryFactory;
        this.localH2DataSource = localH2DataSource;
    }
    
    //Metodo principale che gestisce la sincronizzazione
    public void synchronizeMySQLToH2OnStartup() {
        System.out.println("Tentativo di sincronizzazione dati da MySQL a H2");
        try (Connection mySQLConnectionForCheck = primaryFactory.createConnection()) {
            if (primaryFactory.isOpen(mySQLConnectionForCheck)) {
                System.out.println("Database primario ONLINE. Avvio della sincronizzazione dei dati");
                
                synchronizeTable("DIPENDENTI", 
                		"SELECT STAFF_ID, NOME, COGNOME, CONTATTO FROM DIPENDENTI", 
                		"INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES (?, ?, ?, ?)", 
                		new String[]{"STAFF_ID", "NOME", "COGNOME", "CONTATTO"});
                
                synchronizeTable("CLIENTI", 
                		"SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO FROM CLIENTI", 
                		"INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?)", 
                		new String[]{"CF", "NOME", "COGNOME", "SESSO", "FLAG_MINOR", "CONTATTO", "ABBONAMENTO"});
                
                synchronizeTable("CALENDARIO", 
                		"SELECT NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO FROM CALENDARIO", 
                		"INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES (?, ?, ?, ?)", 
                		new String[]{"NOME_EVENTO", "DATA_EVENTO", "MESSAGGIO", "DESTINATARIO_MESSAGGIO"});
                
                //Aggiorno il timestamp dell'ultima sincronizzazione corretta
                updateLastSyncTimestamp();
                
                System.out.println("Sincronizzazione completata con successo.");
            }
        } catch (SQLException e) {
            System.err.println("Database primario OFFLINE\nImpossibile sincronizzare i dati: " + e.getMessage());
            //Mostro lo stato del backup H2 esistente
            DatabaseInfrastructureSetup.reportLastSyncStatus(this.localH2DataSource);
        }
    }

    //Metodo per sincronizzare una singola tabella.
    private void synchronizeTable(String tableName, String selectAllQuery, String insertQuery, String[] columnNames) {
        System.out.println("Sincronizzazione tabella '" + tableName + "'...");
        final int BATCH_SIZE = 100;
        int totalRowsProcessed = 0;
        //Try-with-resources, apre le connessioni ai db e ne garantisce la chiusura
        try (Connection primaryConn = primaryFactory.createConnection();
             Connection localConn = localH2DataSource.getConnection();
        	 //Preparo gli statements di lettura dal db principale e inserimento su H2
             Statement primaryStmt = primaryConn.createStatement();
             PreparedStatement localInsertStmt = localConn.prepareStatement(insertQuery)) {
        	
            //Disabilita il commit automatico 
        	//Permette di eseguire più operazioni che saranno lasciate in sospeso fino a esplicito commit
        	localConn.setAutoCommit(false); 
        	 
            //Creo uno statement per la connessione locale
        	//Lo uso per svuotare la tabella che successivamente andrà riempita
            try (Statement localDeleteStmt = localConn.createStatement()) {
                localDeleteStmt.executeUpdate("DELETE FROM " + tableName);
            }
            
            //Eseguo la query di select all dal db priincipale e ottengo un result set
            try (ResultSet rs = primaryStmt.executeQuery(selectAllQuery)) {
            	//Itero ogni singola riga del result set
            	while (rs.next()) {
            		//Per ogni riga passo ciascuna colonna
                    for (int i = 0; i < columnNames.length; i++) {
                    	//rs.getObject(columnNames[i]) prende il valore dalla colonna corrente della riga letta
                    	//localInsertStmt.setObject(i + 1,... imposta il valore nel PreparedStatement di inserimento per H2
                    	//i + 1 perché i parametri JDBC partono da 1 e non da 0
                        localInsertStmt.setObject(i + 1, rs.getObject(columnNames[i]));
                    }
                    //Aggiungo l'istruzione di insert nella batch per un successivo commit e incremento il counter delle righe
                    localInsertStmt.addBatch();
                    totalRowsProcessed++;
                    
                    //Quando raggiungo un numero di righe processate multiplo di 100 mando in esecuzione la batch
                    if (totalRowsProcessed % BATCH_SIZE == 0) {
                        localInsertStmt.executeBatch();
                    }
                }
            	
            	//Questo permette di mandare in esecuzione una batch finale il cui numero di righe è arbitrario
                if (totalRowsProcessed % BATCH_SIZE != 0 && totalRowsProcessed > 0) {
                    localInsertStmt.executeBatch();
                }
            }
            //Eseguo il commit, rendendo permanenti le modifiche ad H2
            //Qualunque errore prima del commit causerebbe un rollback dei dati di H2 riportandolo allo stato in cui si trovava all'inizio.
            localConn.commit();
            System.out.println("Tabella '" + tableName + "' sincronizzata con successo. Righe processate: " + totalRowsProcessed);

        } catch (SQLException e) {
            System.err.println("ERRORE durante la sincronizzazione della tabella '" + tableName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Metodo che aggiorna il timestamp dell'ultima sincronizzazione avvenuta con successo
    private void updateLastSyncTimestamp() {
    	//Ottiene la data e l'ora attuali e le formatta in una stringa standard (ISO_LOCAL_DATE_TIME)
        String timestampString = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        //Merge permette di cercare attraverso una copppia chiave valore in SINCRO_INFO e sovrascrivere i dati se presenti 
        //Se non trova una corrispondenza per la chiave inserisce una nuova riga con quella chiave e quel valore.
        String sql = "MERGE INTO SINCRO_INFO (CHIAVE, VALORE) KEY(CHIAVE) VALUES (?, ?)";

        System.out.println("Aggiornamento timestamp dell'ultima sincronizzazione a: " + timestampString);
        //Try-with-resources che esegue la query
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