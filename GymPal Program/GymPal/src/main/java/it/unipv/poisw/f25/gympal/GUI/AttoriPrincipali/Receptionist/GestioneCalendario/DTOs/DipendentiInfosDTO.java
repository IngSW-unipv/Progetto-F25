package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs;

import java.util.ArrayList;
import java.util.List;

public class DipendentiInfosDTO implements IDipendentiInfosDTO{
	
	private List<String> staffIDs = new ArrayList<>();
	
    //----------------------------------------------------------------
	
	public DipendentiInfosDTO() {}
	
	//----------------------------------------------------------------
	
	public DipendentiInfosDTO (List<String> staffIDs) {
		
		this.staffIDs = staffIDs;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public List<String> getStaffIDs(){return staffIDs;}
	
    //----------------------------------------------------------------	

}
