package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.DTOHandler;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class DTOHandlerRegistrazione {
	
	private IDatiCliente abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOHandlerRegistrazione (IDatiCliente abbDTO) {
		
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
	

	public void impostaScontoEta(boolean scontoEta) {
		
		abbDTO.setScontoEta(scontoEta);
		
	}
	
	public void impostaScontoOccasioni(boolean scontoOccasioni) {
		
		abbDTO.setScontoOccasioni(scontoOccasioni);
		
	}
	
	public void impostaDurataAbbonamento(DurataAbbonamento durataAbbonamento) {
		
		abbDTO.setDurataAbbonamento(durataAbbonamento);
		
	}
	
	//----------------------------------------------------------------
	
	public void inizializzaListaScontiOccasione(List<Sconto> scontiOccasioneSelezionati) {
		
		abbDTO.setScontiOccasioneSelezionati(scontiOccasioneSelezionati);
		
	}
	
	//----------------------------------------------------------------
	
	/*Strutturando così questo metodo, lo stato di pagamento è derivato in automati-
	 *co, riduncendo il rischio di commettere errori e/o duplicare codice.*/	
	public void impostaMetodoPagamento(MetodoPagamento metodoPagamento) {
		
	
		abbDTO.setMetodoPagamento(metodoPagamento);
		
		/*Fra le parentesi tonde: se "metodoPagamento" è diverso da "NESSUNO", allora
		 *lo stato di pagamento è impostanto a 'true', altrimenti, se "metodoPagamento"
		 *è pari a "NESSUNO", allora è passato 'false'.*/
		abbDTO.setStatoPagamento(metodoPagamento != MetodoPagamento.NESSUNO);
		
	}
	
	//----------------------------------------------------------------

}
