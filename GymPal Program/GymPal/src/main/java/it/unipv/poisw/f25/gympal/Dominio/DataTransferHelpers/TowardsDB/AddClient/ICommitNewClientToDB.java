package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;

public interface ICommitNewClientToDB {

	public void transfer(IAbbonamentoDTO abbDTO);
	
}
