package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.IDateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;

public interface ICRUDTurniSupportServices {

    public IDateUtils getDateUtils();
    
    //------------------------------------------------------------

    public IDialogUtils getDialogUtils();
    
    //------------------------------------------------------------
    
    public IDateRangeUtils getDateRangeUtils();
    
    //------------------------------------------------------------
    
    public IRetrieveDipendentiFromDB getRetrieveDipendentiService();
    
    //------------------------------------------------------------
	
}
