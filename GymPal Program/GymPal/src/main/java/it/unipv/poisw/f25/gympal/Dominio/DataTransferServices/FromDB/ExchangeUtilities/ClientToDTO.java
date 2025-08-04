package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.ExchangeUtilities;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.ExchangeUtilities.Codecs.IListsToStringCodec;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class ClientToDTO implements IClientToDTO{
	
	private IListsToStringCodec codec;
	
    //----------------------------------------------------------------
	
	public ClientToDTO(IListsToStringCodec codec) {
		
		this.codec = codec;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean extractAndUpdateDTO (Cliente cliente, IDatiCliente abbDTO) {

		if(cliente != null) {
		
			abbDTO.setNome(cliente.getNome());
			abbDTO.setCognome(cliente.getCognome());
			abbDTO.setSesso(cliente.getSesso());
			abbDTO.setMinorenne(cliente.getIsMinorenne());
			abbDTO.setContatto(cliente.getContatto());
			
			try {
				
			    DurataAbbonamento durata = DurataAbbonamento.valueOf(cliente.getAbbonamento()
			    															.toUpperCase());
			    abbDTO.setDurataAbbonamento(durata);
			    
			} catch (IllegalArgumentException | NullPointerException e) {
				
			    System.out.println("Errore: valore non valido per DurataAbbonamento: " + cliente.getAbbonamento());
			    abbDTO.setDurataAbbonamento(DurataAbbonamento.NESSUNO);
			    
			}
		    
			abbDTO.setInizioAbbonamento(cliente.getInizioAbbonamento());
			abbDTO.setFineAbbonamento(cliente.getFineAbbonamento());
			abbDTO.setStatoPagamento(cliente.getPagamentoEffettuato());
			abbDTO.setComposizioneAbbonamento(cliente.getComposizioneAbbonamento());
			abbDTO.setDataNascita(BDayFromCf.estraiDataNascita(abbDTO.getCodiceFiscale()));
			
			abbDTO.setSezioniAbbonamento(new ArrayList<>());
			abbDTO.setCorsiSelezionati(new ArrayList<>());
			codec.espandiAbbonamento(cliente.getComposizioneAbbonamento(), 
									 abbDTO.getSezioniAbbonamento(),
									 abbDTO.getCorsiSelezionati());
			
			return true;
		
		} else {
			
			abbDTO.setNome("Nessun cliente corrispondente al CF fornito");
			abbDTO.setCognome("Nessun cliente corrispondente al CF fornito");
			abbDTO.setSesso("n/a");
			abbDTO.setMinorenne(false);
			abbDTO.setContatto("n/a");
			abbDTO.setDurataAbbonamento(DurataAbbonamento.NESSUNO);
			abbDTO.setInizioAbbonamento(LocalDate.of(1, 1, 1));
			abbDTO.setFineAbbonamento(LocalDate.of(1, 1, 1));
			abbDTO.setStatoPagamento(false);
			abbDTO.setComposizioneAbbonamento("n/a");
			abbDTO.setDataNascita(LocalDate.of(1, 1, 1));
			
			abbDTO.setSezioniAbbonamento(new ArrayList<>());
			abbDTO.setCorsiSelezionati(new ArrayList<>());
			codec.espandiAbbonamento(abbDTO.getComposizioneAbbonamento(), 
									 abbDTO.getSezioniAbbonamento(),
									 abbDTO.getCorsiSelezionati());
			
			return false;
			
		}
				
	}
	
    //----------------------------------------------------------------

}
