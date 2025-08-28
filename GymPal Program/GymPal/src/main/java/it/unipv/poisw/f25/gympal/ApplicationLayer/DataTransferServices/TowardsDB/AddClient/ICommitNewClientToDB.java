package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface ICommitNewClientToDB {

	public boolean commit(IDatiCliente abbDTO);
	
	//----------------------------------------------------------------
	
	public boolean commit(IDatiCliente abbDTO, LocalDate giornoRegistrazione);
	
	//----------------------------------------------------------------
}
