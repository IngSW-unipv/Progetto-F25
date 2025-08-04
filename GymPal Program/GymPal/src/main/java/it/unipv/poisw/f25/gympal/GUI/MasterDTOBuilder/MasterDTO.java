package it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class MasterDTO implements IDatiCliente, IDatiClienteReadOnly, ICalcolaPrezzo{
	
	private String cf;
	private String nome;
	private String cognome;
	private String sesso;
	private boolean isMinorenne;
	private String contatto;
	private DurataAbbonamento abbonamento;
	private LocalDate dataNascita;
	private LocalDate inizioAbbonamento;      
	private LocalDate fineAbbonamento;        
	private String composizioneAbbonamento;
	
    private boolean certificatoIdoneita = true;
    private boolean permessoGenitori = true;
	
	private List<String> componentiAbbonamento;
    private List<String> corsiSelezionati;
    
    /*Ad uso di Reset*////////
    private List<String> componentiAbbonamentoOriginali;
    private List<String> corsiSelezionatiOriginali;
    //////////////////////////
    
    private MetodoPagamento metodoPagamento;
    private boolean scontoEta;
    private boolean scontoOccasioni;
    private boolean statoPagamento;
    private List<Sconto> scontiOccasioneSelezionati;
	    
    // --- Getters & Setters -----------------------------------------
	
	@Override
	public String getCodiceFiscale() {
		
		return cf;
		
	}

	@Override
	public void setCodiceFiscale(String cf) {
		
		this.cf = cf;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getNome() {
		
		return nome;
		
	}

	@Override
	public void setNome(String nome) {
		
		this.nome = nome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getCognome() {
		
		return cognome;
		
	}
	
	@Override
	public void setCognome(String cognome) {
		
		this.cognome = cognome;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public String getSesso() {
		
		return sesso;
		
	}
	
	@Override
	public void setSesso(String sesso) {
		
		this.sesso = sesso;
		
	}
	
    //----------------------------------------------------------------

	@Override
	public boolean isMinorenne() {
		
		return isMinorenne;
		
	}
	
	@Override
	public void setMinorenne(boolean isMinorenne) {
		
		this.isMinorenne = isMinorenne;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String getContatto() {
		
		return contatto;
		
	}
	
	@Override
	public void setContatto(String contatto) {
		
		this.contatto = contatto;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public DurataAbbonamento getDurataAbbonamento() {
		
		return abbonamento;
		
	}
	
	@Override
	public void setDurataAbbonamento(DurataAbbonamento abbonamento) {
		
		this.abbonamento = abbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public LocalDate getInizioAbbonamento() {
		
		return inizioAbbonamento;
		
	}
	
	@Override
	public void setInizioAbbonamento(LocalDate inizioAbbonamento) {
		
		this.inizioAbbonamento = inizioAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public LocalDate getFineAbbonamento() {
		
		return fineAbbonamento;
		
	}
	
	@Override
	public void setFineAbbonamento(LocalDate fineAbbonamento) {
		
		this.fineAbbonamento = fineAbbonamento;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String getComposizioneAbbonamento() {
		
		return composizioneAbbonamento;
		
	}
	
	@Override
	public void setComposizioneAbbonamento(String composizioneAbbonamento) {
		
		this.composizioneAbbonamento = composizioneAbbonamento;
		
	}
	
    //----------------------------------------------------------------	
	
    @Override
    public LocalDate getDataNascita() {
    	
        return dataNascita;
        
    }

    public void setDataNascita(LocalDate dataNascita) {
    	
        this.dataNascita = dataNascita;
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> getSezioniAbbonamento() {
    	
        return componentiAbbonamento;
        
    }

    @Override
    public void setSezioniAbbonamento(List<String> sezioniAbbonamento) {
    	
        this.componentiAbbonamento = sezioniAbbonamento;
        
        if (componentiAbbonamentoOriginali == null) {
        	
            componentiAbbonamentoOriginali = sezioniAbbonamento;
            
        }
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> getCorsiSelezionati() {
    	
        return corsiSelezionati;
        
    }

    @Override
    public void setCorsiSelezionati(List<String> corsiSelezionati) {
    	
        this.corsiSelezionati = corsiSelezionati;
        
        if (corsiSelezionatiOriginali == null) {
        	
            corsiSelezionatiOriginali = corsiSelezionati;
            
        }
    
    }
    
    //----------------------------------------------------------------
    
    @Override
    public MetodoPagamento getMetodoPagamento() {
    	
    	return metodoPagamento;
    	
    }
    
    @Override
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
    	
    	this.metodoPagamento = metodoPagamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoEta() {
    	
    	return scontoEta;
    	
    }
    
    @Override
    public void setScontoEta(boolean scontoEta) {
    	
    	this.scontoEta = scontoEta;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoOccasioni() {
    	
    	return scontoOccasioni;
    	
    }
    
    @Override
    public void setScontoOccasioni(boolean scontoOccasioni) {
    	
    	this.scontoOccasioni = scontoOccasioni;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getStatoPagamento() {
    	
    	return statoPagamento;
    	
    }
    
    @Override
    public void setStatoPagamento(boolean pagamento) {
    	
    	statoPagamento = pagamento;
    	
    }
   
    //----------------------------------------------------------------
    
    @Override
    public boolean getCertificatoIdoneita() {
    	
        return certificatoIdoneita;
        
    }
    
	@Override
	public void setCertificatoIdoneita(boolean certificatoIdoneita) {
		
		this.certificatoIdoneita = certificatoIdoneita;
		
	}
	
    //----------------------------------------------------------------
    
    @Override
    public boolean getPermessoGenitori() {
    	
        return permessoGenitori;
        
    }
    
	@Override
	public void setPermessoGenitori(Boolean permessoGenitori) {
		
		this.permessoGenitori = permessoGenitori;
		
	}
	
    //----------------------------------------------------------------
    
    @Override
    public void ripristinaStatoIniziale() {
    	
        if (componentiAbbonamentoOriginali != null) {
            componentiAbbonamento = componentiAbbonamentoOriginali;
        }
        
        if (corsiSelezionatiOriginali != null) {
            corsiSelezionati = corsiSelezionatiOriginali;
        }
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<Sconto> getScontiOccasioneSelezionati() {
    	
        return scontiOccasioneSelezionati;
        
    }
    
    @Override
    public void setScontiOccasioneSelezionati(List<Sconto> scontiOccasioneSelezionati) {
    	
        this.scontiOccasioneSelezionati = scontiOccasioneSelezionati;
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public String toString() {
        return "MasterDTO {" +
                "\n  cf='" + cf + '\'' +
                ",\n  nome='" + nome + '\'' +
                ",\n  cognome='" + cognome + '\'' +
                ",\n  sesso='" + sesso + '\'' +
                ",\n  isMinorenne=" + isMinorenne +
                ",\n  contatto='" + contatto + '\'' +
                ",\n  abbonamento=" + abbonamento +
                ",\n  dataNascita=" + dataNascita +
                ",\n  inizioAbbonamento=" + inizioAbbonamento +
                ",\n  fineAbbonamento=" + fineAbbonamento +
                ",\n  composizioneAbbonamento='" + composizioneAbbonamento + '\'' +
                ",\n  certificatoIdoneita=" + certificatoIdoneita +
                ",\n  permessoGenitori=" + permessoGenitori +
                ",\n  componentiAbbonamento=" + componentiAbbonamento +
                ",\n  corsiSelezionati=" + corsiSelezionati +
                ",\n  componentiAbbonamentoOriginali=" + componentiAbbonamentoOriginali +
                ",\n  corsiSelezionatiOriginali=" + corsiSelezionatiOriginali +
                ",\n  metodoPagamento=" + metodoPagamento +
                ",\n  scontoEta=" + scontoEta +
                ",\n  scontoOccasioni=" + scontoOccasioni +
                ",\n  statoPagamento=" + statoPagamento +
                "\n}";
    }
    
    //----------------------------------------------------------------

}
