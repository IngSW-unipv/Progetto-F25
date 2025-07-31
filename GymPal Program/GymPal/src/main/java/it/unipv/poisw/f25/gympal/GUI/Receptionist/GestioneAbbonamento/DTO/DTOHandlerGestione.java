package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO;

import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

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
	
	//----------------------------------------------------------------
	
	public void composizioneAbbonamento(List<String> sezioniSelezionate,
		    							List<String> corsiSelezionati) {


		abbDTO.setSezioniAbbonamento(sezioniSelezionate);
		abbDTO.setCorsiSelezionati(corsiSelezionati);		

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
