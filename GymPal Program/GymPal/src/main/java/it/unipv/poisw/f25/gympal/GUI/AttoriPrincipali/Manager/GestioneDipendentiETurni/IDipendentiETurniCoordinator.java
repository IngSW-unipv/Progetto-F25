package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.IStaffIdGeneratorService;

public interface IDipendentiETurniCoordinator {

	public ITurniCRUDFacadeService getTurniCRUDService();
	
    //----------------------------------------------------------------
	
	public ICRUDTurniSupportServices getTurniCRUDSupportServices();
	
    //----------------------------------------------------------------
	
	public IDipendentiCRUDFacadeService getDipendentiCRUDService();
	
    //----------------------------------------------------------------
	
	public ICRUDDipendentiSupportServices getDipendentiCRUDSupportServices();
	
    //----------------------------------------------------------------
	
	public IStaffIdGeneratorService getGeneratoreStaffIDs();
	
    //----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
	//----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
	//----------------------------------------------------------------
	
}
