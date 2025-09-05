package it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.DashboardsCommonInterface;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface IDashboard {
	
	public void dispose();
	
    //----------------------------------------------------------------  
	
	public void mostraSchermata(String nome);
	
    //----------------------------------------------------------------  
	
    /* Registra un'azione associata ad un comando identificato da nomeComando */
	public void aggiungiComando(String nomeComando, Runnable azione);
	
    //----------------------------------------------------------------
	
    /* Registra una nuova schermata (opzionale, se usi schermate dinamiche) */
	public void registraSchermata(String nome, JPanel schermata);
	
	//----------------------------------------------------------------
	
	public JFrame getMainFrame();
	
	//----------------------------------------------------------------

}
