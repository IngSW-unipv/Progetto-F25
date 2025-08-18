package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee.AutenticaDipendente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveClient.RetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.RetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.AddClient.CommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.RemoveClient.DeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.UpdateClient.UpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
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
    private IRetrieveDiscountsFromDB getDiscounts;
    
    private IAutenticaDipendente autenticatore;
    private StaffFactory staffFactory;

    
	//----------------------------------------------------------------
    
    public CommonServicesBundle(){
    	
    	facade = PersistenceFacade.getInstance();
    	
    	/*Servizi Dominio*/
        regexChecker = new RegexCheck();
        campoValidabileFactory = new CampoValidabileFactory(regexChecker);
        validatoreCampi = new ValidatoreCampi();
        prezzoFactory = new StrategieCalcoloPrezzoFactory();
        
        /*Servizi DB*/
        recuperaDati = new RetrieveClientFromDB(facade);
        headHunter = new DeleteClientFromDB(facade);
        updateClient = new UpdateClientInsideDB(facade);
        immettiDati = new CommitNewClientToDB(facade);
        getDiscounts = new RetrieveDiscountsFromDB(facade);
        autenticatore = new AutenticaDipendente(facade);
        staffFactory = new StaffFactory();
    	
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
    
    public IRetrieveDiscountsFromDB getDiscounts() {
    	
    	return getDiscounts;
    	
    }
    
   //----------------------------------------------------------------
    
    public IAutenticaDipendente getAutDipendente() {
    	
    	return autenticatore;
    	
    }
    
    //----------------------------------------------------------------
    
    public StaffFactory getStaffFactory() {
    	
    	return staffFactory;
    	
    }
    
    //----------------------------------------------------------------

}
