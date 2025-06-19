package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public class CostruttoreDTOHelper {
	
	private AbbonamentoDTO abbDTO;
	
	//----------------------------------------------------------------
	
	public CostruttoreDTOHelper (AbbonamentoDTO abbDTO) {
		
		this.abbDTO = abbDTO;
		
	}
	
	//----------------------------------------------------------------
	
	public void composizioneAbbonamento(List<String> sezioniSelezionate,
									    List<String> corsiSelezionati) {
		
		
		abbDTO.setSezioniAbbonamento(sezioniSelezionate);
		abbDTO.setCorsiSelezionati(corsiSelezionati);		
		
	}
	
	//----------------------------------------------------------------
	
	public void impostaDatiAnagrafici(String nome, String cognome, String codiceFiscale,
									  String contatto, String sesso,
									  boolean certificatoIdoneita,
									  boolean permessoGenitori, LocalDate dataNascita) {
		
		abbDTO.setNome(nome);
		abbDTO.setCognome(cognome);
		abbDTO.setCodiceFiscale(codiceFiscale);
		abbDTO.setContatto(contatto);
		abbDTO.setSesso(sesso);
		
		abbDTO.setDataNascita(dataNascita);
		
		abbDTO.setCertificatoIdoneita(certificatoIdoneita);
		abbDTO.setPermessoGenitori(permessoGenitori);		
		
	}
	
	//----------------------------------------------------------------
	
	/*public void statoPagamento(boolean statoPagamento) {
		
		abbDTO.setStatoPagamento(statoPagamento);
		
	}*/
	
	//----------------------------------------------------------------
	
	public void impostaScontiEDurata(boolean scontoEta, boolean scontoOccasioni,
								     DurataAbbonamento durataAbbonamento) {
		
		abbDTO.setScontoEta(scontoEta);
		abbDTO.setScontoOccasioni(scontoOccasioni);
		abbDTO.setDurataAbbonamento(durataAbbonamento);
		
	}
	
	//----------------------------------------------------------------
	
	public void impostaMetodoPagamento(MetodoPagamento metodoPagamento) {
		
		/*Strutturando così questo metodo, lo stato di pagamento è derivato in automati-
		 *co, riduncendo il rischio di commettere errori e/o duplicare codice.*/
		
		abbDTO.setMetodoPagamento(metodoPagamento);
		
		/*Fra le parentesi tonde: se "metodoPagamento" è diverso da "NESSUNO", allora
		 *lo stato di pagamento è impostanto a 'true', altrimenti, se "metodoPagamento"
		 *è pari a "NESSUNO", allora è passato 'false'.*/
		abbDTO.setStatoPagamento(metodoPagamento != MetodoPagamento.NESSUNO);
		
	}
	
	//----------------------------------------------------------------

}
