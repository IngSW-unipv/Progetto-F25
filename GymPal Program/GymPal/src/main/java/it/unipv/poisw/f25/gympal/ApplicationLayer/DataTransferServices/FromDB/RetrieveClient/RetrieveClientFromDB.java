package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.ClienteToDTO.ClientToDTO;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.ClienteToDTO.IClientToDTO;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.Codecs.ListsToStringCodec;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class RetrieveClientFromDB implements IRetrieveClientFromDB {

	private IPersistenceFacade facade;
	private IClientToDTO mapper;
	
    //----------------------------------------------------------------
	
	public RetrieveClientFromDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		this.mapper = new ClientToDTO(new ListsToStringCodec());
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void retrieve(IDatiCliente abbDTO) {
		
		try {
		
			Cliente cliente = new Cliente();
			
			cliente.setCf(abbDTO.getCodiceFiscale());
			
			mapper.extractAndUpdateDTO(facade.selectCliente(cliente), abbDTO);
		
		} catch (Exception e) {
			
			System.err.println("Errore durante caricamento dati cliente: " + e.getMessage());
	        throw new RuntimeException("Errore nel recupero dati da DB", e);
			
		}
		
	}
	
    //----------------------------------------------------------------
	
}
