package it.unipv.poisw.f25.gympal.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactory {
    //Apre e restituisce una nuova Connection
    Connection createConnection() throws SQLException;

    //Controlla se una Connection è aperta (non null e non chiusa)
    boolean isOpen(Connection conn);

    //Chiude la Connection passata (ignora se già chiusa o null)
    void closeConnection(Connection conn);
    
    //Controlla se MySQL è disponibile per poter usare metodi di scrittura dei DAO
    boolean isReadOnlyMode();
}