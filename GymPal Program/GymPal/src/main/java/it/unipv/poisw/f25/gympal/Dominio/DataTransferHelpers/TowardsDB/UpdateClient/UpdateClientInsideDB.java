package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.DTOtoCliente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.IDTOtoCliente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.Codecs.ListsToStringCodec;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class UpdateClientInsideDB implements IUpdateClientInsideDB{
	
	private IDTOtoCliente mapper;
	private IPersistenceFacade facade;
	
    //----------------------------------------------------------------
	
	public UpdateClientInsideDB(IPersistenceFacade facade) {
		
		mapper = new DTOtoCliente(LocalDate.now(), new ListsToStringCodec());
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean update(IUtenteAbbDTO abbDTO) {
		
		try {
			
			boolean result;
			
			Cliente cliente = mapper.extractAndInsert((IDatiCliente)abbDTO);
			
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
