package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo;

public interface IDialogUtils {

	public void mostraErrore(String msg);
	
	//----------------------------------------------------------------
	
	public void mostraInfo(String msg);
	
	//----------------------------------------------------------------
	
	public int conferma(String msg, String titolo);
	
	//----------------------------------------------------------------
	
}
