package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.Codecs.ListsToStringCodec;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.DTOtoCliente.DTOtoCliente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.DTOtoCliente.IDTOtoCliente;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class CommitNewClientToDB implements ICommitNewClientToDB {
	
	
	private IDTOtoCliente mapper;
	private IPersistenceFacade facade;
	
	
    //----------------------------------------------------------------
	
	public CommitNewClientToDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean commit(IDatiCliente abbDTO) {
			
		try {
			
			mapper = new DTOtoCliente(LocalDate.now(), new ListsToStringCodec());
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert(abbDTO);
			
			result = facade.insertCliente(cliente);
			
			System.out.println("Transferimento avvenuto: " + result);
			
			return result;
			
		}catch (Exception e) {
	        
			e.printStackTrace();
	        System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nella scrittura del cliente nel DB", e);
	        
	    }
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean commit(IDatiCliente abbDTO, LocalDate giornoRegistrazione) {
			
		try {
			
			mapper = new DTOtoCliente(giornoRegistrazione, new ListsToStringCodec());
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert(abbDTO);
			
			result = facade.insertCliente(cliente);
			System.out.println("Risultato insertCliente(): " + result);
			
			return result;
			
		}catch (Exception e) {
	        
			e.printStackTrace();
	        System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nella scrittura del cliente nel DB", e);
	        
	    }
		
	}
	
	//----------------------------------------------------------------

}
