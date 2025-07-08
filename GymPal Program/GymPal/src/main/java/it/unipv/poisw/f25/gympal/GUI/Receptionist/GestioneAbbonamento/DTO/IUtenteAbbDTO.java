package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public interface IUtenteAbbDTO {
	
	public String getCf();
	
    //----------------------------------------------------------------
	
	public void setCf(String cf);
	
    //----------------------------------------------------------------
	
	public String getNome();
	
    //----------------------------------------------------------------
	
	public void setNome(String nome);
	
    //----------------------------------------------------------------
	
	public String getCognome();
	
    //----------------------------------------------------------------
	
	public void setCognome(String cognome);
	
    //----------------------------------------------------------------
	
	public String getSesso();
	
    //----------------------------------------------------------------
	
	public void setSesso(String sesso);
	
    //----------------------------------------------------------------
	
	public boolean isMinorenne();
	
    //----------------------------------------------------------------
	
	public void setMinorenne(boolean isMinorenne);
	
    //----------------------------------------------------------------
	
	public String getContatto();
	
    //----------------------------------------------------------------
	
	public void setContatto(String contatto);
	
    //----------------------------------------------------------------
	
	public DurataAbbonamento getDurataAbbonamento();
	
    //----------------------------------------------------------------
	
	public void setAbbonamento(DurataAbbonamento abbonamento);
	
    //----------------------------------------------------------------
	
	public LocalDate getInizioAbbonamento();
	
    //----------------------------------------------------------------
	
	public void setInizioAbbonamento(LocalDate inizioAbbonamento);
	
    //----------------------------------------------------------------
	
	public LocalDate getFineAbbonamento();
	
    //----------------------------------------------------------------
	
	public void setFineAbbonamento(LocalDate fineAbbonamento);
	
    //----------------------------------------------------------------
	
	public boolean isPagamentoEffettuato();
	
    //----------------------------------------------------------------
	
	public void setPagamentoEffettuato(boolean pagamentoEffettuato);
	
    //----------------------------------------------------------------
	
	public String getComposizioneAbbonamento();
	
    //----------------------------------------------------------------
	
	public void setComposizioneAbbonamento(String composizioneAbbonamento);
	
    //----------------------------------------------------------------	
	
	public LocalDate getDataNascita();
	
    //----------------------------------------------------------------
	
	public void setDataNascita(LocalDate dataNascita);
	
    //----------------------------------------------------------------
	
	public List<String> getSezioniAbbonamento();

    //----------------------------------------------------------------
	
	public void setSezioniAbbonamento(List<String> sezioniAbbonamento);
	
    //----------------------------------------------------------------
	
	public List<String> getCorsiSelezionati();
	
    //----------------------------------------------------------------
	
	public void setCorsiSelezionati(List<String> corsiSelezionati);
	
    //----------------------------------------------------------------
	
	public MetodoPagamento getMetodoPagamento();
	
    //----------------------------------------------------------------
	
	public void setMetodoPagamento(MetodoPagamento metodoPagamento);
	
    //----------------------------------------------------------------
	
}
