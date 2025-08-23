package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.DipendentiETurniController;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.DipendentiETurniView;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.IDipendentiETurniView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class DipendentiETurniCoordinator implements IDipendentiETurniCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*Viste*/
	private IDipendentiETurniView dipETurni;
	
	/*Servizi*/
    private ITurniCRUDFacadeService turniCRUDService;
    private ICRUDTurniSupportServices supportoCRUDTurni;
	private IDipendentiCRUDFacadeService dipCRUDService;
    private ICRUDDipendentiSupportServices supportpCRUDdip;
	
    //----------------------------------------------------------------
	
	public DipendentiETurniCoordinator(IRegistraEMostraSchermate viewHandler,
									   ITurniCRUDFacadeService turniCRUDService,
									   ICRUDTurniSupportServices supportoCRUDTurni,
									   IDipendentiCRUDFacadeService dipCRUDService,
			    					   ICRUDDipendentiSupportServices supportpCRUDdip) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Servizi*/
    	this.turniCRUDService = turniCRUDService;
    	this.supportoCRUDTurni = supportoCRUDTurni;
    	this.dipCRUDService = dipCRUDService;
    	this.supportpCRUDdip = supportpCRUDdip;
		
		/*Inizializzazione schermate GUI*/
		setupSchermataDipendentiETurni();
		
	}
	
    //----------------------------------------------------------------
	
	private void setupSchermataDipendentiETurni() {
		
		dipETurni = new DipendentiETurniView(new DynamicButtonSizeSetter());
		viewHandler.registraSchermata("DIPENDENTI_E_TURNI", dipETurni.getMainPanel());
		
		new DipendentiETurniController(dipETurni, this);
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ITurniCRUDFacadeService getTurniCRUDService() {
		
		return turniCRUDService;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICRUDTurniSupportServices getTurniCRUDSupportServices() {
		
		return supportoCRUDTurni;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public IDipendentiCRUDFacadeService getDipendentiCRUDService() {
		
		return dipCRUDService;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICRUDDipendentiSupportServices getDipendentiCRUDSupportServices() {
		
		return supportpCRUDdip;
		
	}
	
    //----------------------------------------------------------------

}
