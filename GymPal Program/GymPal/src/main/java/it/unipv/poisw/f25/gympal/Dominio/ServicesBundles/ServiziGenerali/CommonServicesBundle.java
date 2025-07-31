package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.RetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.CommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.DeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.UpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class CommonServicesBundle {
	
	private IPersistenceFacade facade;
	
	private IRegexCheck regexChecker;
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    private IRetrieveClientFromDB recuperaDati;
    private IDeleteClientFromDB headHunter;
    private IUpdateClientInsideDB updateClient;
    private ICommitNewClientToDB immettiDati;

    
	//----------------------------------------------------------------
    
    public CommonServicesBundle(){
    	
    	this.facade = PersistenceFacade.getInstance();
    	
    	/*Servizi Dominio*/
        this.regexChecker = new RegexCheck();
        this.campoValidabileFactory = new CampoValidabileFactory(regexChecker);
        this.validatoreCampi = new ValidatoreCampi();
        this.prezzoFactory = new StrategieCalcoloPrezzoFactory();
        
        /*Servizi DB*/
        this.recuperaDati = new RetrieveClientFromDB(facade);
        this.headHunter = new DeleteClientFromDB(facade);
        this.updateClient = new UpdateClientInsideDB(facade);
        this.immettiDati = new CommitNewClientToDB(facade);
    	
    }
    
	//----------------------------------------------------------------

    public IRegexCheck getRegexChecker() {
    	
        return regexChecker;
        
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
    
    public IStrategieCalcoloPrezzoFactory getPrezzoFactory() {
    	
    	return prezzoFactory;
    	
    }
    
   //----------------------------------------------------------------
    
    public IRetrieveClientFromDB getRecuperaDati() {
    	
    	return recuperaDati;
    	
    }
    
   //----------------------------------------------------------------
    
    public IDeleteClientFromDB getHeadHunter() {
    	
    	return headHunter;
    	
    }
    
   //----------------------------------------------------------------
    
    public IUpdateClientInsideDB getUpdater() {
    	
    	return updateClient;
    	
    }
    
   //---------------------------------------------------------------- 
    
    public ICommitNewClientToDB getImmettiDati() {
    	
    	return immettiDati;
    	
    }
    
   //----------------------------------------------------------------


}
