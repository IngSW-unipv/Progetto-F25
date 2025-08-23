package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.IDateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneEvento.IEventoValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneOra.IOraUtils;

public class CRUDEventiSupportServices implements ICRUDEventiSupportServices{
	
	private final IEventoValidator eventoValidator;
    private final IDialogUtils dialogUtils;
    private final IDateUtils dateUtils;
    private final IFasciaOrariaValidator fasciaValidator;
    private final IOraUtils validatoreOra;
    private final IDateRangeUtils datesRangeGen;

    public CRUDEventiSupportServices(IEventoValidator eventoValidator,
                                     IDialogUtils dialogUtils,
                                     IDateUtils dateUtils,
                                     IFasciaOrariaValidator fasciaValidator,
                                     IOraUtils validatoreOra,
                                     IDateRangeUtils datesRangeGen) {
    	
        this.eventoValidator = eventoValidator;
        this.dialogUtils = dialogUtils;
        this.dateUtils = dateUtils;
        this.fasciaValidator = fasciaValidator;
        this.validatoreOra = validatoreOra;
        this.datesRangeGen = datesRangeGen;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public IEventoValidator getEventoValidator() {
    	
        return eventoValidator;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public IDialogUtils getDialogUtils() {
    	
        return dialogUtils;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public IDateUtils getDateUtils() {
    	
        return dateUtils;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public IFasciaOrariaValidator getFasciaValidator() {
    	
        return fasciaValidator;
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IOraUtils getOraUtils() {
    	
    	return validatoreOra;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IDateRangeUtils getDateRangeUtils() {
    	
    	return datesRangeGen;
    	
    }
    
    //----------------------------------------------------------------

}
