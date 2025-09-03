package it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.DTOtoCliente;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public interface IDTOtoCliente {
	
	public Cliente extractAndInsert(IDatiCliente abbDTO);

}
