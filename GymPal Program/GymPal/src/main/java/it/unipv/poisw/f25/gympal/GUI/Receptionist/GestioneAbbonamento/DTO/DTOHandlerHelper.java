package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO;

public class DTOHandlerHelper {
	
	private UtenteAbbDTO abbDTO;
	
	//----------------------------------------------------------------
	
	public DTOHandlerHelper (UtenteAbbDTO abbDTO) {
		
		this.abbDTO = abbDTO;
		
	}
	
	//----------------------------------------------------------------
	
	public void recuperaCf(String cf) {
		
		abbDTO.setCf(cf);
				
	}
	
	//----------------------------------------------------------------

}
