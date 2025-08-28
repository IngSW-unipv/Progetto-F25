package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;

public class CRUDDipendentiSupportServices implements ICRUDDipendentiSupportServices{
	
	private final IDialogUtils dialogUtils;
	
    //------------------------------------------------------------

    public CRUDDipendentiSupportServices(IDialogUtils dialogUtils) {
    	
        this.dialogUtils = dialogUtils;
        
    }
    
    //------------------------------------------------------------

    @Override
    public IDialogUtils getDialogUtils() {
        return dialogUtils;
    }
    
    //------------------------------------------------------------

}
