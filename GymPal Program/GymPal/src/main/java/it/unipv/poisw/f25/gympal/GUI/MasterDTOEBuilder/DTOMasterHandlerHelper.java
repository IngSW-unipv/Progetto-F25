package it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public class DTOMasterHandlerHelper {

	private IDatiCliente abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOMasterHandlerHelper(IDatiCliente abbDTO) { this.abbDTO = abbDTO;}
	
	//----------------------------------------------------------------
	
	public void recuperaCf(String cf) {
		
		abbDTO.setCodiceFiscale(cf);
				
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
	
	public void impostaScontiEDurata(boolean scontoEta, boolean scontoOccasioni,
		     						 DurataAbbonamento durataAbbonamento) {
	
			abbDTO.setScontoEta(scontoEta);
			abbDTO.setScontoOccasioni(scontoOccasioni);
			abbDTO.setDurataAbbonamento(durataAbbonamento);
	
	}
	
	//----------------------------------------------------------------

	public void impostaMetodoPagamento(MetodoPagamento metodoPagamento) {
		
			abbDTO.setMetodoPagamento(metodoPagamento);
			abbDTO.setStatoPagamento(metodoPagamento != MetodoPagamento.NESSUNO);
	
	}
	
	//----------------------------------------------------------------
	
}
