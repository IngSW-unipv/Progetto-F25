package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneEvento.IEventoValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneOra.IOraUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.IDateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;

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
