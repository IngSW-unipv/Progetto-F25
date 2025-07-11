package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;

public interface IRegistrationCoordinator {
	
	
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
	
	//public void acquisisciStatoPagamento(boolean statoPagamento);
	
	//----------------------------------------------------------------
	
	public boolean isMinorenne(LocalDate dataNascita);
	
	//----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
	//----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
	//----------------------------------------------------------------
	
	public ICtrlReqAnagraficiService getCtrlReqAnagraficiService();
	
	//----------------------------------------------------------------
	
	public double getDiscountedPrice(ICalcolaPrezzo abbonamentoDTO);
	
	//----------------------------------------------------------------
	
	public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento);
	
	//----------------------------------------------------------------
	
	public void acquisisciScontiEDurata(boolean scontoEta, boolean scontoOccasioni,
										DurataAbbonamento durataAbbonamento);
	
	//----------------------------------------------------------------
	
	public IAbbonamentoDTO getAbbonamentoDTO();
	
	//----------------------------------------------------------------

}
