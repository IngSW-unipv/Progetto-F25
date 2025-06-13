package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO;

import java.time.LocalDate;
import java.util.List;

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
	
	public void statoPagamento(boolean statoPagamento) {
		
		abbDTO.setStatoPagamento(statoPagamento);
		
	}
	
	//----------------------------------------------------------------

}
