package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class RetrieveDiscountsFromDB implements IRetrieveDiscountsFromDB{
	
	private IPersistenceFacade facade;
	
	//----------------------------------------------------------------
	
	public RetrieveDiscountsFromDB(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public List<Sconto> retrieveAllDiscounts() {
		
		return facade.selectAllSconti();
		
	}
	
	//----------------------------------------------------------------

}
