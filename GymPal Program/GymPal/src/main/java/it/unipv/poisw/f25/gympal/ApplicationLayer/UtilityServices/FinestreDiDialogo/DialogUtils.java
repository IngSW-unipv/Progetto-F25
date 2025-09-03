package it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo;

import javax.swing.JOptionPane;

public class DialogUtils implements IDialogUtils{
	
	@Override
    public void mostraErrore(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
	//----------------------------------------------------------------

	@Override
    public void mostraInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
	//----------------------------------------------------------------
	
	/* Usato ovunque sia necessario far comparire un popup con una richiesta di
	 * conferma da sottoporre all'utente.*/
	@Override
    public int conferma(String msg, String titolo) {
        return JOptionPane.showConfirmDialog(null, msg, titolo, JOptionPane.YES_NO_OPTION);
    }
    
	//----------------------------------------------------------------

}
