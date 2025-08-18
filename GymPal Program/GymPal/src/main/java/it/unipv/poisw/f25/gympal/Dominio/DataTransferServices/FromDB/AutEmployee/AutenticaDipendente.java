package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class AutenticaDipendente implements IAutenticaDipendente {

	private IPersistenceFacade facade;
	
	//----------------------------------------------------------------
	
	public AutenticaDipendente(IPersistenceFacade facade) {
		
		this.facade = facade;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public boolean autenticazione(String nome, String cognome, String staffId) {
		
		/*Inizializzazione istanza locale 'dip', pre-query*/
		Dipendente dip = new Dipendente();
		dip.setStaffId(staffId);
		
		/*Query al database*/
		Dipendente dipEstratto = facade.selectDipendente(dip);
		
		/*Checks*/		
		if(dipEstratto != null) {
			
			if(nome.equals(dipEstratto.getNome()) 
			   && cognome.equals(dipEstratto.getCognome()) 
			   && staffId.equals(dipEstratto.getStaffId())) {
				
				return true;
				
			} else {return false;}
		
		}		
		return false;		
	}
	
	//----------------------------------------------------------------
	
}
