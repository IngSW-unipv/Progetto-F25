package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IUpdateClientInsideDB {
	
	public boolean update(IDatiCliente abbDTO);
	
	//----------------------------------------------------------------
	
	public boolean update(IDatiCliente abbDTO, LocalDate giornoRegistrazione);
	
	//----------------------------------------------------------------

}
