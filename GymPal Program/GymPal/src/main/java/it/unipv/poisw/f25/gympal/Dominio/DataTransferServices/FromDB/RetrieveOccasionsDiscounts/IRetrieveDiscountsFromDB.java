package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public interface IRetrieveDiscountsFromDB {

	public List<Sconto> retrieveAllDiscounts();
	
}
