package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class RetrieveDipendentiFromDB implements IRetrieveDipendentiFromDB{
	
	private IPersistenceFacade persistence;
	
    //----------------------------------------------------------------
	
	public RetrieveDipendentiFromDB(IPersistenceFacade facade) {
		
		this.persistence = facade;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public List<Dipendente> retrieve() {
			
		return persistence.selectAllDipendenti();
		
	}
	
    //----------------------------------------------------------------

}
