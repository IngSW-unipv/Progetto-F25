package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers;

import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.DipendentiInfosDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDipendentiInfosDTO;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class DTOHandlerDipendentiInfos {
	
	private IRetrieveDipendentiFromDB retrieveListaDip;

    //----------------------------------------------------------------
	
	public DTOHandlerDipendentiInfos(IRetrieveDipendentiFromDB retrieveListaDip) {
		
		this.retrieveListaDip = retrieveListaDip;
	
	}
	
    //----------------------------------------------------------------
	
	public IDipendentiInfosDTO estraiIDsDipendenti() {
		
		List<String> IDs = new ArrayList<>();

		for(Dipendente d: retrieveListaDip.retrieve()) {
			
			IDs.add(d.getStaffId());
			
		}
		
		return new DipendentiInfosDTO(IDs);
		
	}
	
    //----------------------------------------------------------------
	
	public void estraiIDsDipendenti(IDipendentiInfosDTO dipInfos) {

		List<String> staffIDs = new ArrayList<>();
		
		for(Dipendente d: retrieveListaDip.retrieve()) {

			staffIDs.add(d.getStaffId());
			
		}
		
		dipInfos.getStaffIDs().clear();
		dipInfos.getStaffIDs().addAll(staffIDs);
		
	}
	
    //----------------------------------------------------------------

}
