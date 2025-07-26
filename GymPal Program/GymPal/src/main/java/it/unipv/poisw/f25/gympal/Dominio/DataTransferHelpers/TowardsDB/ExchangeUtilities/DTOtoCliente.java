package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities.Codecs.IListsToStringCodec;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;

public class DTOtoCliente implements IDTOtoCliente{
	
	private LocalDate giornoRegistrazione;
	private IListsToStringCodec codec;
	
    //----------------------------------------------------------------

	public DTOtoCliente(LocalDate giornoRegistrazione, IListsToStringCodec codec) {
		
		this.giornoRegistrazione = giornoRegistrazione;
		this.codec = codec;
	
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public Cliente extractAndInsert(IDatiCliente abbDTO) {
		
		ICalcoloEtaService calcoloEta = new CalcoloEtaService();
		
		String sessoFormattato = abbDTO.getSesso().substring(0, 1).toUpperCase();
		
		String durata = abbDTO.getDurataAbbonamento().toString();
		String durataCapitalizzata = durata.substring(0, 1).toUpperCase() 
								   + durata.substring(1).toLowerCase();
		
		LocalDate fineAbbonamento = CalcolaFineAbbonamento.
									calcolaFineAbbonamento(giornoRegistrazione, 
												abbDTO.getDurataAbbonamento());
		
		String listsContent = codec.
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
