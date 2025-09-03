package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs;

import java.time.LocalDate;
import java.util.List;

public interface IDatiCellaCalendarioDTO {
	
	public LocalDate getData();
	
    //----------------------------------------------------------------
	
	public int getOra();
	
    //----------------------------------------------------------------
	
	public int getMinuti();
	
    //----------------------------------------------------------------
	
	public List<String> getCorsi();
	
    //----------------------------------------------------------------
	
	public List<String> getAppuntamentiPT();
	
    //----------------------------------------------------------------
	
	public List<String> getEventiGenerici();
	
    //----------------------------------------------------------------
	
	public List<String> getTurni();
	
    //----------------------------------------------------------------

}
