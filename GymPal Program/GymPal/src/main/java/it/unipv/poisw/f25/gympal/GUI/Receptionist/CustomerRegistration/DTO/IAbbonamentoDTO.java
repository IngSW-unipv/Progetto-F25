package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public interface IAbbonamentoDTO {
	
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
	
	public MetodoPagamento getMetodoPagamento();
	
	//----------------------------------------------------------------
	
	public void setMetodoPagamento(MetodoPagamento metodoPagamento);
	
	//----------------------------------------------------------------
	
	public DurataAbbonamento getDurataAbbonamento();
	
	//----------------------------------------------------------------
	
	public void setDurataAbbonamento(DurataAbbonamento durataAbbonamento);
	
	//----------------------------------------------------------------
	
	public boolean getScontoEta();
	
	//----------------------------------------------------------------
	
	public void setScontoEta(boolean scontoEta);
	
	//----------------------------------------------------------------
	
	public boolean getScontoOccasioni();
	
	//----------------------------------------------------------------
	
	public void setScontoOccasioni(boolean scontoOccasioni);
	
	//----------------------------------------------------------------
	

}
