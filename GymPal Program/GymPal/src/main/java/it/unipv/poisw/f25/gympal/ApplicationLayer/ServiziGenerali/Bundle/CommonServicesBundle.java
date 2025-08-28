package it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Autenticazione.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.FontManager.IFontManager;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;

public class CommonServicesBundle implements ICommonServicesBundle{
	
	//private IPersistenceFacade facade;
	
    private final IRegexCheck regexChecker;
    private final ICampoValidabileFactory campoValidabileFactory;
    private final IValidatoreCampi validatoreCampi;
    private final IStrategieCalcoloPrezzoFactory prezzoFactory;
    private final IRetrieveClientFromDB recuperaDati;
    private final IDeleteClientFromDB headHunter;
    private final IUpdateClientInsideDB updateClient;
    private final ICommitNewClientToDB immettiDati;
    private final IRetrieveDiscountsFromDB getDiscounts;
    private final IFontManager fontManager;
    private final IFontChangeRegister changeRegister;
    private final IAutenticaDipendente autenticatore;
    private final StaffFactory staffFactory;

    
	//----------------------------------------------------------------
    
    public CommonServicesBundle(IPersistenceFacade facade,
					            IRegexCheck regexChecker,
					            ICampoValidabileFactory campoValidabileFactory,
					            IValidatoreCampi validatoreCampi,
					            IStrategieCalcoloPrezzoFactory prezzoFactory,
					            IRetrieveClientFromDB recuperaDati,
					            IDeleteClientFromDB headHunter,
					            IUpdateClientInsideDB updateClient,
					            ICommitNewClientToDB immettiDati,
					            IRetrieveDiscountsFromDB getDiscounts,
					            IFontManager fontManager,
					            IFontChangeRegister changeRegister,
					            IAutenticaDipendente autenticatore,
					            StaffFactory staffFactory) {

		//this.facade = facade;
		this.regexChecker = regexChecker;
		this.campoValidabileFactory = campoValidabileFactory;
		this.validatoreCampi = validatoreCampi;
		this.prezzoFactory = prezzoFactory;
		this.recuperaDati = recuperaDati;
		this.headHunter = headHunter;
		this.updateClient = updateClient;
		this.immettiDati = immettiDati;
		this.getDiscounts = getDiscounts;
		this.fontManager = fontManager;
		this.changeRegister = changeRegister;
		this.autenticatore = autenticatore;
		this.staffFactory = staffFactory;
		
	}
    
	//----------------------------------------------------------------

    
	//----------------------------------------------------------------
    
    @Override
    public IRegexCheck getRegexChecker() {
    	
        return regexChecker;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
        return campoValidabileFactory;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public IValidatoreCampi getValidatoreCampi() {
    	
        return validatoreCampi;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public IStrategieCalcoloPrezzoFactory getPrezzoFactory() {
    	
    	return prezzoFactory;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public IRetrieveClientFromDB getRecuperaDati() {
    	
    	return recuperaDati;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public IDeleteClientFromDB getHeadHunter() {
    	
    	return headHunter;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public IUpdateClientInsideDB getUpdater() {
    	
    	return updateClient;
    	
    }
    
   //---------------------------------------------------------------- 
    
    @Override
    public ICommitNewClientToDB getImmettiDati() {
    	
    	return immettiDati;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public IRetrieveDiscountsFromDB getDiscounts() {
    	
    	return getDiscounts;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public IAutenticaDipendente getAutDipendente() {
    	
    	return autenticatore;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public StaffFactory getStaffFactory() {
    	
    	return staffFactory;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IFontManager getFontManager() {
    	
    	return fontManager;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IFontChangeRegister getFontChangeRegister() {
    	
    	return changeRegister;
    	
    }
    
    //----------------------------------------------------------------

}
