package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.ICRUDTurniSupportServices;

public interface IDipendentiETurniCoordinator {

	public ITurniCRUDFacadeService getTurniCRUDService();
	
    //----------------------------------------------------------------
	
	public ICRUDTurniSupportServices getTurniCRUDSupportServices();
	
    //----------------------------------------------------------------
	
	public IDipendentiCRUDFacadeService getDipendentiCRUDService();
	
    //----------------------------------------------------------------
	
	public ICRUDDipendentiSupportServices getDipendentiCRUDSupportServices();
	
    //----------------------------------------------------------------
	
}
