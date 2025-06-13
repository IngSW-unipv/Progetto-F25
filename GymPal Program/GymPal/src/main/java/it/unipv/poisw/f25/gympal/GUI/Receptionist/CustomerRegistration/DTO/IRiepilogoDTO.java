package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO;

import java.time.LocalDate;
import java.util.List;

public interface IRiepilogoDTO {
	
    //----------------------------------------------------------------
	
	public String getNome();
	
    //----------------------------------------------------------------

	public String getCognome();
	
	//----------------------------------------------------------------
	
	public String getCodiceFiscale();
	
	//----------------------------------------------------------------
	
	public LocalDate getDataNascita();
	
	//----------------------------------------------------------------
	
	public String getSesso();
	
	//----------------------------------------------------------------
	
	public boolean getCertificatoIdoneita();
	
	//----------------------------------------------------------------
	
	public boolean getPermessoGenitori();
	
	//----------------------------------------------------------------
	
	public List<String> getSezioniAbbonamento();
	
	//----------------------------------------------------------------
	
	public List<String> getCorsiSelezionati();
	
	//----------------------------------------------------------------
	
	public String getContatto();
	
	//----------------------------------------------------------------
	
	public boolean getStatoPagamento();
	
	//----------------------------------------------------------------
	

}
