package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTOHandler;

import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class DTOHandlerGestione {
	
	private IDatiCliente abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOHandlerGestione (IDatiCliente abbDTO) {
		
		this.abbDTO = abbDTO;
		
	}
	
	//----------------------------------------------------------------
	
	public void recuperaCf(String cf) {
		
		abbDTO.setCodiceFiscale(cf);
				
	}
	
	public String esponiCf() {
		
		return abbDTO.getCodiceFiscale();
		
	}
	
	//----------------------------------------------------------------
	
	public void composizioneAbbonamento(List<String> sezioniSelezionate,
		    							List<String> corsiSelezionati) {


		abbDTO.setSezioniAbbonamento(sezioniSelezionate);
		abbDTO.setCorsiSelezionati(corsiSelezionati);		

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

	public void impostaMetodoPagamento(MetodoPagamento metodoPagamento) {
		
	
		abbDTO.setMetodoPagamento(metodoPagamento);

		abbDTO.setStatoPagamento(metodoPagamento != MetodoPagamento.NESSUNO);
		
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
