package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.DTOHandler;

import java.util.List;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;

public class DTOHandlerRettifica {
	
	private IDatiCliente abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOHandlerRettifica (IDatiCliente abbDTO) {
		
		this.abbDTO = abbDTO;
		
	}
	
	//----------------------------------------------------------------
	
	public void impostaDatiAnagrafici(RawClientData raw) {

		abbDTO.setNome(raw.nome);
		abbDTO.setCognome(raw.cognome);
		abbDTO.setCodiceFiscale(raw.codiceFiscale);
		abbDTO.setContatto(raw.contatto);
		abbDTO.setSesso(raw.sesso);
		
		abbDTO.setDataNascita(raw.dataNascita);

	}
	
	//----------------------------------------------------------------
	
	public void aggiornaDatiAnagrafici(RawClientData raw) {
		
		abbDTO.setNome(raw.nome);
		abbDTO.setCognome(raw.cognome);
		abbDTO.setContatto(raw.contatto);
		abbDTO.setSesso(raw.sesso);
		
		abbDTO.setDataNascita(raw.dataNascita);
		
	}
	
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
	
	public List<String> getSezioniAbbonamento(){
		
		return abbDTO.getSezioniAbbonamento();
		
	}
	
	//----------------------------------------------------------------
	
	public List<String> getCorsiSelezionati(){
		
		return abbDTO.getCorsiSelezionati();
		
	}
	
	
	//----------------------------------------------------------------
	
	public void ripristinaListe() {
		
		abbDTO.ripristinaStatoIniziale();
		
	}
	
	
	//----------------------------------------------------------------

}
