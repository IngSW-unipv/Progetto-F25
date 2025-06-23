package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;

public interface ICommitNewClientToDB {

	public void transfer(IRiepilogoDTO abbDTO);
	
}
