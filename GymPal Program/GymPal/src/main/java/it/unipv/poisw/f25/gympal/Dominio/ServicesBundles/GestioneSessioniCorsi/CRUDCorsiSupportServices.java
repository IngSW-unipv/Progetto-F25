package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneSessioneCorso.ISessioneCorsoValidator;

public class CRUDCorsiSupportServices implements ICRUDCorsiSupportServices{
	
	private final IFasciaOrariaValidator fasciaValidator;
    private final ISessioneCorsoValidator sessioneValidator;
    private final IDateUtils dateUtils;
    private final IDialogUtils dialogUtils;
        
	//----------------------------------------------------------------

    public CRUDCorsiSupportServices(IFasciaOrariaValidator fasciaValidator,
                                        ISessioneCorsoValidator sessioneValidator,
                                        IDateUtils dateUtils,
                                        IDialogUtils dialogUtils) {
    	
        this.fasciaValidator = fasciaValidator;
        this.sessioneValidator = sessioneValidator;
        this.dateUtils = dateUtils;
        this.dialogUtils = dialogUtils;
        
    }

	//----------------------------------------------------------------
    
    @Override
    public IFasciaOrariaValidator getFasciaValidator() {
    	
        return fasciaValidator;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public ISessioneCorsoValidator getSessioneValidator() {
    	
        return sessioneValidator;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public IDateUtils getDateUtils() {
    	
        return dateUtils;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public IDialogUtils getDialogUtils() {
    	
        return dialogUtils;
        
    }
    
	//----------------------------------------------------------------

}
