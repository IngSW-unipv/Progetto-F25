package it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean;

import java.time.LocalDate;

public class Cliente {
	
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

    
    
    //Costruttore di default
    public Cliente() {
    }
    
  //Costruttore parametrizzato
    public Cliente(String cf, String nome, String cognome, String sesso, boolean isMinorenne, String contatto,
			String abbonamento, LocalDate inizioAbbonamento, LocalDate fineAbbonamento, boolean pagamentoEffettuato,
			String composizioneAbbonamento) {
		super();
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.isMinorenne = isMinorenne;
		this.contatto = contatto;
		this.abbonamento = abbonamento;
		this.inizioAbbonamento = inizioAbbonamento;
		this.fineAbbonamento = fineAbbonamento;
		this.pagamentoEffettuato = pagamentoEffettuato;
		this.composizioneAbbonamento = composizioneAbbonamento;
	}

	//Getters e setters
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public boolean getIsMinorenne() {
		return isMinorenne;
	}
	
	public void setIsMinorenne(boolean isMinorenne) {
		this.isMinorenne = isMinorenne;
	}

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}

	public String getAbbonamento() {
		return abbonamento;
	}

	public void setAbbonamento(String abbonamento) {
		this.abbonamento = abbonamento;
	}
	
	public LocalDate getInizioAbbonamento() {
		return inizioAbbonamento;
	}

	public void setInizioAbbonamento(LocalDate inizioAbbonamento) {
		this.inizioAbbonamento = inizioAbbonamento;
	}

	public LocalDate getFineAbbonamento() {
		return fineAbbonamento;
	}

	public void setFineAbbonamento(LocalDate fineAbbonamento) {
		this.fineAbbonamento = fineAbbonamento;
	}

	public boolean getPagamentoEffettuato() {
		return pagamentoEffettuato;
	}

	public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
		this.pagamentoEffettuato = pagamentoEffettuato;
	}

	public String getComposizioneAbbonamento() {
		return composizioneAbbonamento;
	}

	public void setComposizioneAbbonamento(String composizioneAbbonamento) {
		this.composizioneAbbonamento = composizioneAbbonamento;
	}

	@Override
	public String toString() {
		return "Cliente [cf=" + cf + ", nome=" + nome + ", cognome=" + cognome + ", sesso=" + sesso + ", isMinorenne="
				+ isMinorenne + ", contatto=" + contatto + ", abbonamento=" + abbonamento + ", inizioAbbonamento="
				+ inizioAbbonamento + ", fineAbbonamento=" + fineAbbonamento + ", pagamentoEffettuato="
				+ pagamentoEffettuato + ", composizioneAbbonamento=" + composizioneAbbonamento + "]";
	}  
		
	
}
