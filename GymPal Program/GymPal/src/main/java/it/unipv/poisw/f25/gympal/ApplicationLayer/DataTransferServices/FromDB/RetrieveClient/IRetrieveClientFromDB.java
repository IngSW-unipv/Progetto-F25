package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IRetrieveClientFromDB {
	
	public void retrieve(IDatiCliente abbDTO);

}
