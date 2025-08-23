package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.IDateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneEvento.IEventoValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneOra.IOraUtils;

public interface ICRUDEventiSupportServices {
	
    public IEventoValidator getEventoValidator();
    
    //---------------------------------------------------------------
    
    public IDialogUtils getDialogUtils();
    
    //---------------------------------------------------------------
    
    public IDateUtils getDateUtils();
    
    //---------------------------------------------------------------
    
    public IFasciaOrariaValidator getFasciaValidator();
    
    //---------------------------------------------------------------
    
    public IOraUtils getOraUtils();
    
    //---------------------------------------------------------------
    
    public IDateRangeUtils getDateRangeUtils();
    
    //---------------------------------------------------------------

}
