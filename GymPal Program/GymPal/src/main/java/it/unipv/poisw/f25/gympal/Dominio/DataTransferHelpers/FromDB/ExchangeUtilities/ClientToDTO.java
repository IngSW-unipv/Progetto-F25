package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.ExchangeUtilities;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class ClientToDTO implements IClientToDTO{
	
	public ClientToDTO() {}
	
    //----------------------------------------------------------------
	
	@Override
	public boolean extractAndUpdateDTO (Cliente cliente, IUtenteAbbDTO abbDTO) {

		if(cliente != null) {
		
			abbDTO.setNome(cliente.getNome());
			abbDTO.setCognome(cliente.getCognome());
			abbDTO.setSesso(cliente.getSesso());
			abbDTO.setMinorenne(cliente.getIsMinorenne());
			abbDTO.setContatto(cliente.getContatto());
			abbDTO.setAbbonamento(cliente.getAbbonamento());
			abbDTO.setInizioAbbonamento(cliente.getInizioAbbonamento());
			abbDTO.setFineAbbonamento(cliente.getFineAbbonamento());
			abbDTO.setPagamentoEffettuato(cliente.getPagamentoEffettuato());
			abbDTO.setComposizioneAbbonamento(cliente.getComposizioneAbbonamento());
			
			return true;
		
		} else {
			
			abbDTO.setNome("Nessun cliente corrispondente al CF fornito");
			abbDTO.setCognome("Nessun cliente corrispondente al CF fornito");
			abbDTO.setSesso("n/a");
			abbDTO.setMinorenne(false);
			abbDTO.setContatto("n/a");
			abbDTO.setAbbonamento("n/a");
			abbDTO.setInizioAbbonamento(LocalDate.of(1, 1, 1));
			abbDTO.setFineAbbonamento(LocalDate.of(1, 1, 1));
			abbDTO.setPagamentoEffettuato(false);
			abbDTO.setComposizioneAbbonamento("n/a");
			
			return false;
			
		}
				
	}
	
    //----------------------------------------------------------------

}
