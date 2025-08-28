package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.Codecs.ListsToStringCodec;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.DTOtoCliente.DTOtoCliente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.DTOtoCliente.IDTOtoCliente;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class UpdateClientInsideDB implements IUpdateClientInsideDB{
	
	private IDTOtoCliente mapper;
	private IPersistenceFacade facade;
	
    //----------------------------------------------------------------
	
	public UpdateClientInsideDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	/*Usare per rinnovo/modifica abbonamento*/
	@Override
	public boolean update(IDatiCliente abbDTO) {
		
		try {
			
			mapper = new DTOtoCliente(LocalDate.now(), new ListsToStringCodec());
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert(abbDTO);
			
			result = facade.updateCliente(cliente);
			
			System.out.println("Transferimento avvenuto: " + result);
			
			return result;
			
		}catch (Exception e) {
	        
	        System.err.println("Errore durante update cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nella scrittura dei dati nel DB", e);
	        
	    }		
		
	}
	
    //----------------------------------------------------------------
	
	/*Usare nella correzione di dati anagrafici*/
	@Override
	public boolean update(IDatiCliente abbDTO, LocalDate giornoRegistrazione) {
		
		try {
			
			mapper = new DTOtoCliente(giornoRegistrazione, new ListsToStringCodec());
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert(abbDTO);
			
			result = facade.updateCliente(cliente);
			
			System.out.println("Transferimento avvenuto: " + result);
			
			return result;
			
		}catch (Exception e) {
	        
	        System.err.println("Errore durante update cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nella scrittura dei dati nel DB", e);
	        
	    }		
		
	}
	
    //----------------------------------------------------------------

}
