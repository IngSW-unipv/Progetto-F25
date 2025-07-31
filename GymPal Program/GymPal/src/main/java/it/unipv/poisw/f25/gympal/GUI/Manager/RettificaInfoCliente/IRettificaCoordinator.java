package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IRettificaCoordinator {
	
	public void acquisisciCfCliente(String Cf);
	
    //----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
    //----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
    //----------------------------------------------------------------
	
	public IDatiCliente getDTO();
	
    //----------------------------------------------------------------
	
	public void acquisisciDatiAnagrafici(String nome, String cognome,
									     String codiceFiscale, String contatto, 
									     String sesso, LocalDate dataNascita);
	
	//----------------------------------------------------------------
	
	public void aggiornaDatiAnagrafici(String nome, String cognome, String contatto, 
			   						   String sesso, LocalDate dataNascita);
	
	//----------------------------------------------------------------

}
