package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.DTO;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public class DTOHandlerRettifica {
	
	private IDatiCliente abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOHandlerRettifica (IDatiCliente abbDTO) {
		
		this.abbDTO = abbDTO;
		
	}
	
	//----------------------------------------------------------------
	
	public void impostaDatiAnagrafici(String nome, String cognome, String codiceFiscale,
									  String contatto, String sesso,
									  LocalDate dataNascita) {

		abbDTO.setNome(nome);
		abbDTO.setCognome(cognome);
		abbDTO.setCodiceFiscale(codiceFiscale);
		abbDTO.setContatto(contatto);
		abbDTO.setSesso(sesso);
		
		abbDTO.setDataNascita(dataNascita);

	}
	
	//----------------------------------------------------------------
	
	public void aggiornaDatiAnagrafici(String nome, String cognome,String contatto, 
									   String sesso,LocalDate dataNascita) {
		
		abbDTO.setNome(nome);
		abbDTO.setCognome(cognome);
		abbDTO.setContatto(contatto);
		abbDTO.setSesso(sesso);
		
		abbDTO.setDataNascita(dataNascita);
		
	}
	
	//----------------------------------------------------------------
	
	public void recuperaCf(String cf) {
		
		abbDTO.setCodiceFiscale(cf);
				
	}
	
	//----------------------------------------------------------------

}
