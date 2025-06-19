package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public class AbbonamentoDTO implements IRiepilogoDTO{
	
    // Anagrafica
	
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String sesso; // "Maschio" o "Femmina"
    private String contatto; //indirizzo posta elettronica
    
    private LocalDate dataNascita;    
        
    private boolean certificatoIdoneita;
    private boolean permessoGenitori; //Posto a 'true', di default
    private boolean statoPagamento;
    
    
    //----------------------------------------------------------------

    // Abbonamento
    
    private List<String> componentiAbbonamento;  // Es: ["Sala Pesi", "Personal Trainer", ecc.]
    private List<String> corsiSelezionati;     // Es: ["Yoga", "Zumba", ecc.]
    
    private MetodoPagamento metodoPagamento;
    private DurataAbbonamento durataAbbonamento;
    
    private boolean scontoEta;
    private boolean scontoOccasioni;

    
    // --- Getters & Setters -----------------------------------------
    
     @Override
    public String getNome() {
    	
        return nome;
        
    }
    
    //----------------------------------------------------------------

    public void setNome(String nome) {
    	
        this.nome = nome;
        
    }

    //----------------------------------------------------------------
    
    @Override
    public String getCognome() {
    	
        return cognome;
        
    }
    
    //----------------------------------------------------------------

    public void setCognome(String cognome) {
    	
        this.cognome = cognome;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public String getCodiceFiscale() {
    	
        return codiceFiscale;
        
    }
    
    //----------------------------------------------------------------

    public void setCodiceFiscale(String codiceFiscale) {
    	
        this.codiceFiscale = codiceFiscale;
        
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
    public String getSesso() {
    	
        return sesso;
        
    }
    
    //----------------------------------------------------------------

    public void setSesso(String sesso) {
    	
        this.sesso = sesso;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public boolean getCertificatoIdoneita() {
    	
        return certificatoIdoneita;
        
    }
    
    //----------------------------------------------------------------

    public void setCertificatoIdoneita(boolean certificatoIdoneita) {
    	
        this.certificatoIdoneita = certificatoIdoneita;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public boolean getPermessoGenitori() {
    	
        return permessoGenitori;
        
    }
    
    //----------------------------------------------------------------

    public void setPermessoGenitori(Boolean permessoGenitori) {
    	
        this.permessoGenitori = permessoGenitori;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public List<String> getSezioniAbbonamento() {
    	
        return componentiAbbonamento;
        
    }
    
    //----------------------------------------------------------------

    public void setSezioniAbbonamento(List<String> sezioniAbbonamento) {
    	
        this.componentiAbbonamento = sezioniAbbonamento;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public List<String> getCorsiSelezionati() {
    	
        return corsiSelezionati;
        
    }
    
    //----------------------------------------------------------------

    public void setCorsiSelezionati(List<String> corsiSelezionati) {
    	
        this.corsiSelezionati = corsiSelezionati;
    
    }
    
    //----------------------------------------------------------------
    
    public void setContatto(String contatto) {
    	
    	this.contatto = contatto;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public String getContatto() {
    	
    	return contatto;
    	
    }
    
    //----------------------------------------------------------------
    
    public void setStatoPagamento(boolean pagamento) {
    	
    	statoPagamento = pagamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getStatoPagamento() {
    	
    	return statoPagamento;
    	
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
    public DurataAbbonamento getDurataAbbonamento() {
    	
    	return durataAbbonamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void setDurataAbbonamento(DurataAbbonamento durataAbbonamento) {
    	
    	this.durataAbbonamento = durataAbbonamento;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoEta() {
    	
    	return scontoEta;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void setScontoEta(boolean scontoEta) {
    	
    	this.scontoEta = scontoEta;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean getScontoOccasioni() {
    	
    	return scontoOccasioni;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void setScontoOccasioni(boolean scontoOccasioni) {
    	
    	this.scontoOccasioni = scontoOccasioni;
    	
    }
    
    //----------------------------------------------------------------
    
}
