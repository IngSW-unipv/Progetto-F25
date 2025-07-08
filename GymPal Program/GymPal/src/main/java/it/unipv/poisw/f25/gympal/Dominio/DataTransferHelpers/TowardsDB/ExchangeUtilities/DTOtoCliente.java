package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class DTOtoCliente implements IDTOtoCliente{
	
	private LocalDate giornoRegistrazione;
	
    //----------------------------------------------------------------

	public DTOtoCliente(LocalDate giornoRegistrazione) {
		
		this.giornoRegistrazione = giornoRegistrazione;
	
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public Cliente extractAndInsert(IAbbonamentoDTO abbDTO) {
		
		ICalcoloEtaService calcoloEta = new CalcoloEtaService();
		
		String sessoFormattato = abbDTO.getSesso().substring(0, 1).toUpperCase();
		
		String durata = abbDTO.getDurataAbbonamento().toString();
		String durataCapitalizzata = durata.substring(0, 1).toUpperCase() 
								   + durata.substring(1).toLowerCase();
		
		LocalDate fineAbbonamento = CalcolaFineAbbonamento.
									calcolaFineAbbonamento(giornoRegistrazione, 
												abbDTO.getDurataAbbonamento());
		
		String listsContent = ListsToStringCodec.
							  condensaAbbonamento(abbDTO.getSezioniAbbonamento(),
									  			   abbDTO.getCorsiSelezionati());
		
		Cliente cliente = new Cliente(abbDTO.getCodiceFiscale(),
									  abbDTO.getNome(),
									  abbDTO.getCognome(),
									  sessoFormattato,
									  calcoloEta.isMinorenne(abbDTO.getDataNascita()),
									  abbDTO.getContatto(),
									  durataCapitalizzata,
									  giornoRegistrazione,
									  fineAbbonamento,
									  abbDTO.getStatoPagamento(),
									  listsContent);
		
		return cliente;
		
	}

}
