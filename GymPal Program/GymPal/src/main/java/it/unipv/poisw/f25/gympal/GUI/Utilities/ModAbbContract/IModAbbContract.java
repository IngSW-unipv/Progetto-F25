package it.unipv.poisw.f25.gympal.GUI.Utilities.ModAbbContract;

import java.util.List;

public interface IModAbbContract {
	
	public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
												List<String> corsiSelezionati);

	//----------------------------------------------------------------

	public List<String> retrieveSezioniAbbonamento();
	
    //----------------------------------------------------------------
	
	public List<String> retrieveCorsiSelezionati();
	
    //----------------------------------------------------------------
	
	public void annullaModsCompAbbonamento();
	
    //----------------------------------------------------------------
	
}
