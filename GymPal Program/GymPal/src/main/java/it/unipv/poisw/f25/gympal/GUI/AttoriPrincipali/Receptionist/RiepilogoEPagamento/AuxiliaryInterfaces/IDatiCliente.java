package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public interface IDatiCliente {
	
	public String getNome();
	
	public void setNome(String nome);
	
    //----------------------------------------------------------------
	
	public String getCognome();
	
	public void setCognome(String cognome);
	
    //----------------------------------------------------------------
	
	public String getCodiceFiscale();
	
	public void setCodiceFiscale(String codiceFiscale);
	
    //----------------------------------------------------------------
	
	public String getSesso();
	
	public void setSesso(String sesso);
	
    //----------------------------------------------------------------
	
	public String getContatto();
	
	public void setContatto(String contatto);
	
    //----------------------------------------------------------------
	
	public LocalDate getDataNascita();
	
	public void setDataNascita(LocalDate dataNascita);
	
    //----------------------------------------------------------------
	
	public boolean getCertificatoIdoneita();

	public void setCertificatoIdoneita(boolean certificatoIdoneita);

	
    //----------------------------------------------------------------
	
	public boolean getPermessoGenitori();
	
	public void setPermessoGenitori(Boolean permessoGenitori);
	
    //----------------------------------------------------------------
	
	public List<String> getSezioniAbbonamento();
	
	public void setSezioniAbbonamento(List<String> sezioniAbbonamento);
	
    //----------------------------------------------------------------
	
	public List<String> getCorsiSelezionati();
	
	public void setCorsiSelezionati(List<String> corsiSelezionati);
	
    //----------------------------------------------------------------
	
	public DurataAbbonamento getDurataAbbonamento();
	
	public void setDurataAbbonamento(DurataAbbonamento abbonamento);
	
    //----------------------------------------------------------------
	
	public boolean getStatoPagamento();
	
	public void setStatoPagamento(boolean pagamento);
	
    //----------------------------------------------------------------

	public boolean isMinorenne();
	
	public void setMinorenne(boolean isMinorenne);
	
    //----------------------------------------------------------------
	
	public LocalDate getInizioAbbonamento();
	
	public void setInizioAbbonamento(LocalDate inizioAbbonamento);
	
    //----------------------------------------------------------------
	
	public LocalDate getFineAbbonamento();
	
	public void setFineAbbonamento(LocalDate fineAbbonamento);
	
    //----------------------------------------------------------------
	
	public String getComposizioneAbbonamento();
	
	public void setComposizioneAbbonamento(String composizioneAbbonamento);
	
    //----------------------------------------------------------------
	
	public MetodoPagamento getMetodoPagamento();
	
	public void setMetodoPagamento(MetodoPagamento metodoPagamento);
	
    //----------------------------------------------------------------
	
	public boolean getScontoEta();
	
	public void setScontoEta(boolean scontoEta);
	
    //----------------------------------------------------------------
	
	public boolean getScontoOccasioni();
	
	public void setScontoOccasioni(boolean scontoOccasioni);
	
    //----------------------------------------------------------------
	
	public void ripristinaStatoIniziale();
	
    //----------------------------------------------------------------	
	
	public void setScontiOccasioneSelezionati(List<Sconto> scontiOccasioneSelezionati);
    
	//----------------------------------------------------------------
	

}
