package it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ControlloRequisitiAnagrafica.CtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.CommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class DomainServicesBundle {
	
	private IRegexCheck regexChecker;
    private ICalcoloEtaService calcoloEtaService;
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private ICtrlReqAnagraficiService controlloRequisiti;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    
    private ICommitNewClientToDB veicoloDati;
    private IPersistenceFacade facade;
    
    
	//----------------------------------------------------------------

    public DomainServicesBundle() {
    	
        this.regexChecker = new RegexCheck();
        this.calcoloEtaService = new CalcoloEtaService();
        this.campoValidabileFactory = new CampoValidabileFactory(regexChecker);
        this.validatoreCampi = new ValidatoreCampi();
        this.controlloRequisiti = new CtrlReqAnagraficiService(calcoloEtaService);
        this.prezzoFactory = new StrategieCalcoloPrezzoFactory();
        
        
        
        this.facade = PersistenceFacade.getInstance();
        this.veicoloDati = new CommitNewClientToDB(facade);
        

        
    }
    
	//----------------------------------------------------------------

    public IRegexCheck getRegexChecker() {
    	
        return regexChecker;
        
    }
    
	//----------------------------------------------------------------

    public ICalcoloEtaService getCalcoloEtaService() {
    	
        return calcoloEtaService;
        
    }
    
	//----------------------------------------------------------------

    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
        return campoValidabileFactory;
        
    }
    
	//----------------------------------------------------------------

    public IValidatoreCampi getValidatoreCampi() {
    	
        return validatoreCampi;
        
    }
    
	//----------------------------------------------------------------

    public ICtrlReqAnagraficiService getControlloRequisiti() {
    	
        return controlloRequisiti;
        
    }
    
	//----------------------------------------------------------------
    
    public IStrategieCalcoloPrezzoFactory getPrezzoFactory() {
    	
    	return prezzoFactory;
    	
    }
    
   //----------------------------------------------------------------
    
    public ICommitNewClientToDB getVeicoloDati() {
    	
    	return veicoloDati;
    	
    }
    
   //----------------------------------------------------------------

}
