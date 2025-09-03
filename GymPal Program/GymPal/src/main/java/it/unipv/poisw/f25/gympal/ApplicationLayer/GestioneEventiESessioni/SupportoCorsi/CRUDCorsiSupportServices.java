package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneSessioneCorso.ISessioneCorsoValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;

public class CRUDCorsiSupportServices implements ICRUDCorsiSupportServices{
	
	private IFasciaOrariaValidator fasciaValidator;
    private ISessioneCorsoValidator sessioneValidator;
    private IDateUtils dateUtils;
    private IDialogUtils dialogUtils;
        
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
