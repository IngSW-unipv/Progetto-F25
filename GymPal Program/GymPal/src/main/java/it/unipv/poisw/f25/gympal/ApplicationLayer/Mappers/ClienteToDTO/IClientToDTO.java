package it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.ClienteToDTO;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public interface IClientToDTO {
	
	public boolean extractAndUpdateDTO (Cliente cliente, IDatiCliente abbDTO);

}
