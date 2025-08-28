package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
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
