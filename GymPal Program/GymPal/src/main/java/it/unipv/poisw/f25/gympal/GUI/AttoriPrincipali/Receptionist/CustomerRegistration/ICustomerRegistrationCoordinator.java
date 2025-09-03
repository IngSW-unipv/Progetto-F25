package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface ICustomerRegistrationCoordinator {
	
	
	public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
												List<String> corsiSelezionati);
	
	//----------------------------------------------------------------
	
	public void acquisisciDatiAnagrafici(String nome, String cognome,
										 String codiceFiscale,
										 String contatto, String sesso,
										 boolean certificatoIdoneita,
										 boolean permessoGenitori, 
										 LocalDate dataNascita);
	
	//----------------------------------------------------------------
	
	public boolean isMinorenne(LocalDate dataNascita);
	
	//----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
	//----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
	//----------------------------------------------------------------
	
	public ICtrlReqAnagraficiService getCtrlReqAnagraficiService();
	
	//----------------------------------------------------------------
	
	public IDateUtils getDateUtils();
	
	//----------------------------------------------------------------
	
	public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento);
	
	//----------------------------------------------------------------
	
	public IDatiCliente getDTO();
	
	//----------------------------------------------------------------

}
