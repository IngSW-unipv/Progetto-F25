package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

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
	
	public void acquisisciStatoPagamento(boolean statoPagamento);
	
	//----------------------------------------------------------------
	
	public boolean isMinorenne(LocalDate dataNascita);
	
	//----------------------------------------------------------------

}
