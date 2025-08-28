package it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Autenticazione.AutenticaDipendente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Autenticazione.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.RetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.RetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient.CommitNewClientToDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.DeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.UpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.FontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.FontManager.FontManager;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.FontManager.IFontManager;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;

public class CommonServicesBundleFactory implements ICommonServicesBundleFactory{
	
	private final IPersistenceFacade persistence;
	private final ICalendarioFacadeService calendarioFacade;
	
	//----------------------------------------------------------------

    public CommonServicesBundleFactory(IPersistenceFacade persistence,
    								   ICalendarioFacadeService calendarioFacade) {
    	
        this.persistence = persistence;
        this.calendarioFacade = calendarioFacade;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public ICommonServicesBundle create() {
        // -------------------------------
        // Autenticazione ed Utenti
        // -------------------------------
        IAutenticaDipendente autenticatore = new AutenticaDipendente(persistence);
        StaffFactory staffFactory = new StaffFactory();

        // -------------------------------
        // Servizi DB (clienti, sconti)
        // -------------------------------
        IRetrieveClientFromDB retrieveClient = new RetrieveClientFromDB(persistence);
        IDeleteClientFromDB deleteClient = new DeleteClientFromDB(persistence, calendarioFacade);
        IUpdateClientInsideDB updateClient = new UpdateClientInsideDB(persistence);
        ICommitNewClientToDB addClient = new CommitNewClientToDB(persistence);
        IRetrieveDiscountsFromDB discounts = new RetrieveDiscountsFromDB(persistence);

        // -------------------------------
        // Validazione ed Utility Campi
        // -------------------------------
        IRegexCheck regexChecker = new RegexCheck();
        ICampoValidabileFactory campoFactory = new CampoValidabileFactory(regexChecker);
        IValidatoreCampi validatore = new ValidatoreCampi();

        // -------------------------------
        // Calcolo prezzo e Strategie
        // -------------------------------
        IStrategieCalcoloPrezzoFactory prezzoFactory = new StrategieCalcoloPrezzoFactory();

        // -------------------------------
        // Gestione Font GUI
        // -------------------------------
        IFontManager fontManager = new FontManager();
        IFontChangeRegister changeRegister = new FontChangeRegister(fontManager);

        // -------------------------------
        // Bundle Finale
        // -------------------------------
        return new CommonServicesBundle(persistence,
                						regexChecker,
                						campoFactory,
                						validatore,
                						prezzoFactory,
                						retrieveClient,
                						deleteClient,
                						updateClient,
                						addClient,
                						discounts,
                						fontManager,
                						changeRegister,
                						autenticatore,
                						staffFactory
                						
        );
        
    }
    
	//----------------------------------------------------------------

}
