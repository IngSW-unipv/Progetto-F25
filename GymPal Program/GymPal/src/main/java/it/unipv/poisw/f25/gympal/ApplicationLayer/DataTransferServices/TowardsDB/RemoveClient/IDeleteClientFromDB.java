package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IDeleteClientFromDB {
	
    public boolean huntAndKill(IDatiCliente cliente);
    
	//----------------------------------------------------------------
    
    public boolean eliminaClienteCompletamente(IDatiCliente cliente);
    
	//----------------------------------------------------------------

}
