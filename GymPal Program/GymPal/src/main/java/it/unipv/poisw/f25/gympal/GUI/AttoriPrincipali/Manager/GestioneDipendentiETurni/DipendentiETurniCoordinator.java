package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.ITurniEDipendentiServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.IStaffIdGeneratorService;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.DipendentiETurniController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.DipendentiETurniView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.IDipendentiETurniView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class DipendentiETurniCoordinator implements IDipendentiETurniCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*Servizi*/
    private ITurniCRUDFacadeService turniCRUDService;
    private ICRUDTurniSupportServices supportoCRUDTurni;
	private IDipendentiCRUDFacadeService dipCRUDService;
    private ICRUDDipendentiSupportServices supportoCRUDdip;
    private IStaffIdGeneratorService generatoreStaffIDs;
    private ICampoValidabileFactory campovalidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IFontChangeRegister changeRegister;
	
    //----------------------------------------------------------------
	
	public DipendentiETurniCoordinator(IRegistraEMostraSchermate viewHandler,
			    					   ITurniEDipendentiServicesBundle turniDipServices,
			    					   ICommonServicesBundle serviziComuni) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Servizi*/
    	this.turniCRUDService = turniDipServices.getTurniCRUD();
    	this.supportoCRUDTurni = turniDipServices.getTurniSupport();
    	this.dipCRUDService = turniDipServices.getDipCRUD();
    	this.supportoCRUDdip = turniDipServices.getDipSupport();
    	this.generatoreStaffIDs = turniDipServices.getStaffIdGenerator();
    	this.campovalidabileFactory = serviziComuni.getCampoValidabileFactory();
    	this.validatoreCampi = serviziComuni.getValidatoreCampi();
    	this.changeRegister = serviziComuni.getFontChangeRegister();
		
		/*Inizializzazione schermate GUI*/
		setupSchermataDipendentiETurni();
		
	}
	
    //----------------------------------------------------------------
	
	private void setupSchermataDipendentiETurni() {
		
		IDipendentiETurniView dipETurni = new DipendentiETurniView(new DynamicButtonSizeSetter(), changeRegister);
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
		
		return supportoCRUDdip;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public IStaffIdGeneratorService getGeneratoreStaffIDs() {
		
		return generatoreStaffIDs;
		
	}
	
    //----------------------------------------------------------------
	
    @Override
    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
    	return campovalidabileFactory;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IValidatoreCampi getValidatoreCampi() {
    	
    	return validatoreCampi;
    	
    }
     
    //----------------------------------------------------------------

}
