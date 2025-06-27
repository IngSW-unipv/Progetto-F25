package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO;

import java.time.LocalDate;

public class UtenteAbbDTO implements IUtenteAbbDTO{

	private String cf;
	private String nome;
	private String cognome;
	private String sesso;
	private boolean isMinorenne;
	private String contatto;
	private String abbonamento;
	private LocalDate inizioAbbonamento;      
	private LocalDate fineAbbonamento;        
	private boolean pagamentoEffettuato; 
	private String composizioneAbbonamento;
	    
    // --- Getters & Setters -----------------------------------------
	
	@Override
	public String getCf() {
		
		return cf;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public void setCf(String cf) {
		
		this.cf = cf;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getNome() {
		
		return nome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public void setNome(String nome) {
		
		this.nome = nome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getCognome() {
		
		return cognome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public void setCognome(String cognome) {
		
		this.cognome = cognome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getSesso() {
		
		return sesso;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public void setSesso(String sesso) {
		
		this.sesso = sesso;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public boolean isMinorenne() {
		
		return isMinorenne;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setMinorenne(boolean isMinorenne) {
		
		this.isMinorenne = isMinorenne;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String getContatto() {
		
		return contatto;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setContatto(String contatto) {
		
		this.contatto = contatto;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String getAbbonamento() {
		
		return abbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setAbbonamento(String abbonamento) {
		
		this.abbonamento = abbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public LocalDate getInizioAbbonamento() {
		
		return inizioAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setInizioAbbonamento(LocalDate inizioAbbonamento) {
		
		this.inizioAbbonamento = inizioAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public LocalDate getFineAbbonamento() {
		
		return fineAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setFineAbbonamento(LocalDate fineAbbonamento) {
		
		this.fineAbbonamento = fineAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean isPagamentoEffettuato() {
		
		return pagamentoEffettuato;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
		
		this.pagamentoEffettuato = pagamentoEffettuato;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String getComposizioneAbbonamento() {
		
		return composizioneAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setComposizioneAbbonamento(String composizioneAbbonamento) {
		
		this.composizioneAbbonamento = composizioneAbbonamento;
		
	}
	
    //----------------------------------------------------------------	

}
