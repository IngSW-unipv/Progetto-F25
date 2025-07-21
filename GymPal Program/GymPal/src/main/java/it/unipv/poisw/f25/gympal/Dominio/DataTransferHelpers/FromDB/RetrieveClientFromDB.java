package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.ExchangeUtilities.ClientToDTO;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.ExchangeUtilities.IClientToDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class RetrieveClientFromDB implements IRetrieveClientFromDB {

	private IPersistenceFacade facade;
	private IClientToDTO mapper;
	
    //----------------------------------------------------------------
	
	public RetrieveClientFromDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		this.mapper = new ClientToDTO();
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void transfer(IUtenteAbbDTO abbDTO) {
		
		try {
		
			Cliente cliente = new Cliente();
			
			cliente.setCf(abbDTO.getCodiceFiscale());
			
			mapper.extractAndUpdateDTO(facade.selectCliente(cliente), abbDTO);
			
			System.out.println(abbDTO.getNome() + " " + abbDTO.getCognome()
							   + " " + abbDTO.getDataNascita());
			
			System.out.println("Componenti abbonamento: " + abbDTO.getSezioniAbbonamento());
			System.out.println("Corsi selezionati: " + abbDTO.getCorsiSelezionati());
		
		} catch (Exception e) {
			
			System.err.println("Errore durante caricamento dati cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nel recupero dati da DB", e);
			
		}
		
	}
	
    //----------------------------------------------------------------
	
	
    //----------------------------------------------------------------
	
}
