package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class RetrieveDipendentiFromDB implements IRetrieveDipendentiFromDB{
	
	private IPersistenceFacade facade;
	
    //----------------------------------------------------------------
	
	public RetrieveDipendentiFromDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public List<Dipendente> retrive() {
			
		return facade.selectAllDipendenti();
		
	}
	
    //----------------------------------------------------------------

}
