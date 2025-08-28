package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;

public interface IRettificaCoordinator {
	
	public void acquisisciCfCliente(String Cf);
	
    //----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
    //----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
    //----------------------------------------------------------------
	
	public IDatiCliente getDTO();
	
    //----------------------------------------------------------------
	
	public void acquisisciDatiAnagrafici(RawClientData raw);
	
	//----------------------------------------------------------------
	
	public void aggiornaDatiAnagrafici(RawClientData raw);
	
	//----------------------------------------------------------------

}
