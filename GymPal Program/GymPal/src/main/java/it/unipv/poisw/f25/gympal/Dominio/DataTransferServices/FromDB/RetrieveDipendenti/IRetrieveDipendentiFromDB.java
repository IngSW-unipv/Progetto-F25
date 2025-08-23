package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public interface IRetrieveDipendentiFromDB {
	
	public List<Dipendente> retrieve();

}
