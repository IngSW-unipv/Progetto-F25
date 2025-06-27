package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.DTOtoCliente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.IDTOtoCliente;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class CommitNewClientToDB implements ICommitNewClientToDB {
	
	
	private IDTOtoCliente mapper;
	private IPersistenceFacade facade;
	
	
    //----------------------------------------------------------------
	
	public CommitNewClientToDB(IPersistenceFacade facade) {
		
		mapper = new DTOtoCliente(LocalDate.now());
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void transfer(IAbbonamentoDTO abbDTO) {
			
		try {
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert(abbDTO);
			
			result = facade.insertCliente(cliente);
			
			System.out.println("Transferimento avvenuto: " + result);
			
		}catch (Exception e) {
	        
	        System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nella scrittura del cliente nel DB", e);
	        
	    }
		
	}
	
    //----------------------------------------------------------------

}
