package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public interface IRetrieveDipendentiFromDB {
	
	public List<Dipendente> retrieve();

}
