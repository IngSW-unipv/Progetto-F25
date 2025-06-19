package it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean;

import java.util.List;

public interface IClienteDAO {
	
	//Recupera tutti i clienti dal database
    List<Cliente> selectAll();

    //Recupera un singolo cliente basato sul suo codice fiscale
    Cliente selectCliente(Cliente cliente);

    //Inserisce un nuovo cliente nel database
    boolean insertCliente(Cliente cliente);

    //Aggiorna i dati di un cliente esistente nel database
    boolean updateCliente(Cliente cliente);

    //Cancella un cliente dal database usando il suo codice fiscale
    boolean deleteCliente(Cliente cliente);

}