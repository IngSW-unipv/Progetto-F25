package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class DeleteClientFromDB implements IDeleteClientFromDB{
	
	private IPersistenceFacade facade;
	
    //----------------------------------------------------------------
	
	public DeleteClientFromDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean huntAndKill(IUtenteAbbDTO abbDTO){
		
		Cliente cliente = new Cliente();
		
		cliente.setCf(abbDTO.getCf());
		
		return facade.deleteCliente(cliente);
		
	}
	
    //----------------------------------------------------------------

}
