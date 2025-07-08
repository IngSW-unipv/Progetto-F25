package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public class UtenteAbbDTO implements IUtenteAbbDTO, ICalcolaPrezzo{

	private String cf;
	private String nome;
	private String cognome;
	private String sesso;
	private boolean isMinorenne;
	private String contatto;
	private DurataAbbonamento abbonamento;
	private LocalDate inizioAbbonamento;      
	private LocalDate fineAbbonamento;        
	private boolean pagamentoEffettuato; 
	private String composizioneAbbonamento;
	
	private LocalDate dataNascita;
    private List<String> componentiAbbonamento;
    private List<String> corsiSelezionati;
    
    private MetodoPagamento metodoPagamento;
    private boolean scontoEta;
    private boolean scontoOccasioni;
	    
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
	public DurataAbbonamento getDurataAbbonamento() {
		
		return abbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void setAbbonamento(DurataAbbonamento abbonamento) {
		
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
	
    @Override
    public LocalDate getDataNascita() {
    	
        return dataNascita;
        
    }
    
    //----------------------------------------------------------------

    public void setDataNascita(LocalDate dataNascita) {
    	
        this.dataNascita = dataNascita;
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> getSezioniAbbonamento() {
    	
        return componentiAbbonamento;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public void setSezioniAbbonamento(List<String> sezioniAbbonamento) {
    	
        this.componentiAbbonamento = sezioniAbbonamento;
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> getCorsiSelezionati() {
    	
        return corsiSelezionati;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public void setCorsiSelezionati(List<String> corsiSelezionati) {
    	
        this.corsiSelezionati = corsiSelezionati;
    
    }
    
    //----------------------------------------------------------------
    
    @Override
    public MetodoPagamento getMetodoPagamento() {
    	
    	return metodoPagamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
    	
    	this.metodoPagamento = metodoPagamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoEta() {
    	
    	return scontoEta;
    	
    }
    
    //----------------------------------------------------------------
    
    
    public void setScontoEta(boolean scontoEta) {
    	
    	this.scontoEta = scontoEta;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoOccasioni() {
    	
    	return scontoOccasioni;
    	
    }
    
    //----------------------------------------------------------------
    
    
    public void setScontoOccasioni(boolean scontoOccasioni) {
    	
    	this.scontoOccasioni = scontoOccasioni;
    	
    }
    
    //----------------------------------------------------------------

}
