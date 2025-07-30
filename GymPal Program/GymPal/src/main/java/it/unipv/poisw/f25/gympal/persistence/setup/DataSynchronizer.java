package it.unipv.poisw.f25.gympal.persistence.setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.h2.jdbcx.JdbcDataSource;
import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

// Classe responsabile per la sincronizzazione dei dati da un database primario MySQL a un database locale di backup H2.

/*
La sincronizzazione avviene in due fasi distinte (cancellazione e inserimento)
per rispettare i vincoli di integrità referenziale del database.
  
Ordine di Cancellazione:
Per poter cancellare i dati da una tabella, è necessario prima cancellare tutti i dati
dalle tabelle che dipendono da essa (cioè che contengono una foreign key verso di essa)
 
Ordine di Inserimento:
Per poter inserire un record con una foreign key, il record a cui fa riferimento deve già esistere
L'ordine è quindi l'opposto: si inseriscono prima i dati nelle tabelle "referenziate"
e solo dopo in quelle "referenzianti"
*/


public class DataSynchronizer {

    private final IConnectionFactory primaryFactory;
    private final JdbcDataSource localH2DataSource;

    // Costruttore
    // Riceve una factory per la connessione al database primario
    // Riceve una JdbcDataSource pper la connessione ad H2
    public DataSynchronizer(IConnectionFactory primaryFactory, JdbcDataSource localH2DataSource) {
        if (primaryFactory == null || localH2DataSource == null) {
            throw new IllegalArgumentException("Factory e DataSource non possono essere null");
        }
        this.primaryFactory = primaryFactory;
        this.localH2DataSource = localH2DataSource;
    }
    
    // Metodo principale che gestisce la sincronizzazione
    public void synchronizeMySQLToH2OnStartup() {
        System.out.println("Tentativo di sincronizzazione dati da MySQL a H2");
        // Tenta una connessione di prova per verificare lo stato del DB primario
        try (Connection check = primaryFactory.createConnection()) {
            if (primaryFactory.isOpen(check)) {
                System.out.println("Database primario ONLINE. Avvio della sincronizzazione dei dati");

                // Mappa per contenere tutte le informazioni necessarie per ogni tabella
                Map<String, TableSyncInfo> tablesInfo = new HashMap<>();
                
                // Mappatura per la tabella DIPENDENTI
                tablesInfo.put("DIPENDENTI", new TableSyncInfo(
                    "SELECT STAFF_ID, NOME, COGNOME, CONTATTO FROM DIPENDENTI", 
                    "INSERT INTO DIPENDENTI (STAFF_ID, NOME, COGNOME, CONTATTO) VALUES (?, ?, ?, ?)", 
                    new String[]{"STAFF_ID", "NOME", "COGNOME", "CONTATTO"}
                ));

                // Mappatura per la tabella CLIENTI
                tablesInfo.put("CLIENTI", new TableSyncInfo(
                    "SELECT CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO FROM CLIENTI", 
                    "INSERT INTO CLIENTI (CF, NOME, COGNOME, SESSO, FLAG_MINOR, CONTATTO, ABBONAMENTO, INIZIO_ABBONAMENTO, FINE_ABBONAMENTO, PAGAMENTO_EFFETTUATO, COMPOSIZIONE_ABBONAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
                    new String[]{"CF", "NOME", "COGNOME", "SESSO", "FLAG_MINOR", "CONTATTO", "ABBONAMENTO", "INIZIO_ABBONAMENTO", "FINE_ABBONAMENTO", "PAGAMENTO_EFFETTUATO", "COMPOSIZIONE_ABBONAMENTO"}
                ));
                
                // Mappatura per la tabella CALENDARIO
                tablesInfo.put("CALENDARIO", new TableSyncInfo(
                    "SELECT NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE, MESSAGGIO, DESTINATARIO_MESSAGGIO FROM CALENDARIO", 
                    "INSERT INTO CALENDARIO (NOME_EVENTO, DATA_EVENTO, ORA_INIZIO, ORA_FINE, MESSAGGIO, DESTINATARIO_MESSAGGIO) VALUES (?, ?, ?, ?, ?, ?)", 
                    new String[]{"NOME_EVENTO", "DATA_EVENTO", "ORA_INIZIO", "ORA_FINE", "MESSAGGIO", "DESTINATARIO_MESSAGGIO"}
                ));

                // Mappatura per la tabella TURNI
                tablesInfo.put("TURNI", new TableSyncInfo(
                    "SELECT DATA, REC_MAT, REC_POM, PT_MAT, PT_POM FROM TURNI", 
                    "INSERT INTO TURNI (DATA, REC_MAT, REC_POM, PT_MAT, PT_POM) VALUES (?, ?, ?, ?, ?)", 
                    new String[]{"DATA", "REC_MAT", "REC_POM", "PT_MAT", "PT_POM"}
                ));

                // Mappatura per la tabella SESSIONI_CORSI
                tablesInfo.put("SESSIONI_CORSI", new TableSyncInfo(
                    "SELECT ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI FROM SESSIONI_CORSI", 
                    "INSERT INTO SESSIONI_CORSI (ID_SESSIONE, STAFF_ID, DATA, FASCIA_ORARIA, NUM_ISCRITTI) VALUES (?, ?, ?, ?, ?)", 
                    new String[]{"ID_SESSIONE", "STAFF_ID", "DATA", "FASCIA_ORARIA", "NUM_ISCRITTI"}
                ));

                // Mappatura per la tabella PARTECIPAZIONI_CORSI
                tablesInfo.put("PARTECIPAZIONI_CORSI", new TableSyncInfo(
                    "SELECT CF, ID_SESSIONE FROM PARTECIPAZIONI_CORSI", 
                    "INSERT INTO PARTECIPAZIONI_CORSI (CF, ID_SESSIONE) VALUES (?, ?)", 
                    new String[]{"CF", "ID_SESSIONE"}
                ));
                
                // Mappatura per la tabella DATE_SCONTI
                tablesInfo.put("DATE_SCONTI", new TableSyncInfo(
                    "SELECT NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO FROM DATE_SCONTI", 
                    "INSERT INTO DATE_SCONTI (NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO) VALUES (?, ?, ?)", 
                    new String[]{"NOME_SCONTO", "DATA_SCONTO", "AMOUNT_SCONTO"}
                ));
                
                // Mappatura per la tabella APPUNTAMENTI_PT
                tablesInfo.put("APPUNTAMENTI_PT", new TableSyncInfo(
                    "SELECT CF, STAFF_ID, DATA, FASCIA_ORARIA FROM APPUNTAMENTI_PT",
                    "INSERT INTO APPUNTAMENTI_PT (CF, STAFF_ID, DATA, FASCIA_ORARIA) VALUES (?, ?, ?, ?)",
                    new String[]{"CF", "STAFF_ID", "DATA", "FASCIA_ORARIA"}
                ));
                
                // Definizione dell'ordine di esecuzione, necessario per evitare errori dovuti ai vincoli tra chiavi primarie e esterne
                // Ordine di cancellazione
                List<String> deletionOrder = Arrays.asList("APPUNTAMENTI_PT", "PARTECIPAZIONI_CORSI", "TURNI", "SESSIONI_CORSI", "CLIENTI", "DIPENDENTI", "CALENDARIO", "DATE_SCONTI");
                
                // Ordine di inserimento
                List<String> insertionOrder = Arrays.asList("DIPENDENTI", "CLIENTI", "CALENDARIO", "DATE_SCONTI", "SESSIONI_CORSI", "TURNI", "PARTECIPAZIONI_CORSI", "APPUNTAMENTI_PT");

                // Svuotaamento tabelle
                clearAllTables(deletionOrder);

                // Popolamento tabelle coi dati del database primario
                populateAllTables(insertionOrder, tablesInfo);

                // Se tutto è andato a buon fine, aggiornamento del timestamp
                updateLastSyncTimestamp();
                
                System.out.println("Sincronizzazione completata con successo.");

            }
        } 
        catch (SQLException e) {
            System.err.println("Database primario OFFLINE\nImpossibile sincronizzare i dati: " + e.getMessage());
            DatabaseInfrastructureSetup.reportLastSyncStatus(this.localH2DataSource);
        }
    }

    // Metodo chesvuota tutte le tabelle nel database H2
    private void clearAllTables(List<String> deletionOrder) throws SQLException {
       
        try (Connection localConn = localH2DataSource.getConnection();
             Statement localDeleteStmt = localConn.createStatement()) {
            
            for (String tableName : deletionOrder) {
                localDeleteStmt.executeUpdate("DELETE FROM " + tableName);
            }
        }
    }

    // Metodo per la FASE 2: popola tutte le tabelle H2 con i dati da MySQL.
    private void populateAllTables(List<String> insertionOrder, Map<String, TableSyncInfo> tablesInfo) throws SQLException {
    	//Try-with-resources, apre le connessioni ai db e ne garantisce la chiusura
    	try (Connection primaryConn = primaryFactory.createConnection();
             Connection localConn = localH2DataSource.getConnection()) {

    		//Disabilita il commit automatico 
        	//Permette di eseguire più operazioni che saranno lasciate in sospeso fino a esplicito commit
            localConn.setAutoCommit(false); 

            for (String tableName : insertionOrder) {
                TableSyncInfo info = tablesInfo.get(tableName);
                
                final int BATCH_SIZE = 100;
                int totalRowsProcessed = 0;
                
              //Preparo gli statements di lettura dal db principale e inserimento su H2
              //Eseguo la query di select all dal db principale e ottengo un result set
                try (PreparedStatement localInsertStmt = localConn.prepareStatement(info.insertQuery);
                     Statement primaryStmt = primaryConn.createStatement();
                     ResultSet rs = primaryStmt.executeQuery(info.selectAllQuery)) {
                    
                	//Itero ogni singola riga del result set
                    while (rs.next()) {
                    	//Per ogni riga passo ciascuna colonna
                        for (int i = 0; i < info.columnNames.length; i++) {
                        	
                        	//rs.getObject(columnNames[i]) prende il valore dalla colonna corrente della riga letta
                        	//localInsertStmt.setObject(i + 1,... imposta il valore nel PreparedStatement di inserimento per H2
                        	//i + 1 perché i parametri JDBC partono da 1 e non da 0
                            localInsertStmt.setObject(i + 1, rs.getObject(info.columnNames[i]));
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
                    if (totalRowsProcessed > 0 && totalRowsProcessed % BATCH_SIZE != 0) {
                        localInsertStmt.executeBatch();
                    }
                    System.out.println("Tabella '" + tableName + "' sincronizzata. Righe processate: " + totalRowsProcessed);
                }
            }
            //Eseguo il commit, rendendo permanenti le modifiche ad H2
            //Qualunque errore prima del commit causerebbe un rollback dei dati di H2 riportandolo allo stato in cui si trovava all'inizio
            localConn.commit(); 
        }
    }

    // Metodo che aggiorna il timestamp dell'ultima sincronizzazione avvenuta con successo
    private void updateLastSyncTimestamp() {
    	//Ottiene la data e l'ora attuali e le formatta in una stringa standard (ISO_LOCAL_DATE_TIME)
        String timestampString = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        //Merge permette di cercare attraverso una copppia chiave valore in SINCRO_INFO e sovrascrivere i dati se presenti 
        //Se non trova una corrispondenza per la chiave inserisce una nuova riga con quella chiave e quel valore.
        String sql = "MERGE INTO SINCRO_INFO (CHIAVE, VALORE) KEY(CHIAVE) VALUES (?, ?)";
        
        //Creo un timestamp in formato più leggibile
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter userFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
        System.out.println("\nAggiornamento timestamp dell'ultima sincronizzazione a: " + timestamp.format(userFormatter));
        
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

    // Classe privata fa da contenitore per le informazioni di ogni tabella
    private static class TableSyncInfo {
        final String selectAllQuery;
        final String insertQuery;
        final String[] columnNames;

        TableSyncInfo(String selectAllQuery, String insertQuery, String[] columnNames) {
            this.selectAllQuery = selectAllQuery;
            this.insertQuery = insertQuery;
            this.columnNames = columnNames;
        }
    }
}
