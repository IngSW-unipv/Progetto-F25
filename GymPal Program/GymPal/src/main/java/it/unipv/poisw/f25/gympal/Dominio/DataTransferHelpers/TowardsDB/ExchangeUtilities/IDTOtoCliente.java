package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public interface IDTOtoCliente {
	
	public Cliente extractAndInsert(IAbbonamentoDTO abbDTO);

}
