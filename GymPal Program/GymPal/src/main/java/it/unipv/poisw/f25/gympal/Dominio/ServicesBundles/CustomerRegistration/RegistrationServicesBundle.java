package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.CommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.CtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class RegistrationServicesBundle {
	

    private ICalcoloEtaService calcoloEtaService;
    private IValidatoreCampi validatoreCampi;
    private ICtrlReqAnagraficiService controlloRequisiti;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    
    private ICommitNewClientToDB veicoloDati;
    private IPersistenceFacade facade;
    
    
	//----------------------------------------------------------------

    public RegistrationServicesBundle() {
    	

        this.calcoloEtaService = new CalcoloEtaService();
        this.validatoreCampi = new ValidatoreCampi();
        this.controlloRequisiti = new CtrlReqAnagraficiService(calcoloEtaService);
        this.prezzoFactory = new StrategieCalcoloPrezzoFactory();
        
        
        
        this.facade = PersistenceFacade.getInstance();
        this.veicoloDati = new CommitNewClientToDB(facade);
        

        
    }
    
	//----------------------------------------------------------------

    public ICalcoloEtaService getCalcoloEtaService() {
    	
        return calcoloEtaService;
        
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
