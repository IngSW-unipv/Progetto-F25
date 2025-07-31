package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IDeleteClientFromDB {
	
	public boolean huntAndKill(IDatiCliente abbDTO);

}
