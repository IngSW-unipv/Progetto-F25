package it.unipv.poisw.f25.gympal.GUI.Receptionist.ModAbbContract;

import java.util.List;

public interface IModAbbContract {

	public List<String> retrieveSezioniAbbonamento();
	
    //----------------------------------------------------------------
	
	public List<String> retrieveCorsiSelezionati();
	
    //----------------------------------------------------------------
	
	public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
												List<String> corsiSelezionati);
	
    //----------------------------------------------------------------
	
	public void annullaModsCompAbbonamento();
	
    //----------------------------------------------------------------
	
}
