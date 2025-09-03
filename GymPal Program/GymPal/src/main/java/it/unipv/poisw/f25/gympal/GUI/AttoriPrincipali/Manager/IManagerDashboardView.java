package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager;

import javax.swing.JPanel;

public interface IManagerDashboardView {
	
	public void aggiungiComando(String nomeComando, Runnable azione);
	
    //----------------------------------------------------------------
	
	public void mostraSchermata(String nome);
	
    //----------------------------------------------------------------
	
	public void registraSchermata(String nome, JPanel schermata);
	
    //----------------------------------------------------------------

}
