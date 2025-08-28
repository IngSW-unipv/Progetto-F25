package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.IDateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;

public class CRUDTurniSupportServices implements ICRUDTurniSupportServices{

    private final IDateUtils dateUtils;
    private final IDialogUtils dialogUtils;
    private final IDateRangeUtils dateRangeGen;
    private final IRetrieveDipendentiFromDB retrieveDips;
    
    //------------------------------------------------------------

    public CRUDTurniSupportServices(IDateUtils dateUtils,
                                    IDialogUtils dialogUtils,
                                    IDateRangeUtils dateRangeGen,
                                    IRetrieveDipendentiFromDB retrieveDips) {
    	
        this.dateUtils = dateUtils;
        this.dialogUtils = dialogUtils;
        this.dateRangeGen = dateRangeGen;
        this.retrieveDips = retrieveDips;
        
    }
    
    //------------------------------------------------------------

    @Override
    public IDateUtils getDateUtils() {
    	
        return dateUtils;
        
    }
    
    //------------------------------------------------------------

    @Override
    public IDialogUtils getDialogUtils() {
    	
        return dialogUtils;
        
    }
    
    //------------------------------------------------------------
    
    @Override
    public IDateRangeUtils getDateRangeUtils() {
    	
    	return dateRangeGen;
    	
    }
    
    //------------------------------------------------------------
    
    @Override
    public IRetrieveDipendentiFromDB getRetrieveDipendentiService() {
    	
    	return retrieveDips;
    	
    }
    
    //------------------------------------------------------------    
	
}
