package it.unipv.poisw.f25.gympal.GUI.Receptionist;


public interface IReceptionistDashboardView {
	
    /* Registra un'azione associata a un comando identificato da nomeComando */
	public void aggiungiComando(String nomeComando, Runnable azione);
	
	//----------------------------------------------------------------

    /* Mostra la schermata associata al nome */
	public void mostraSchermata(String nome);
	
	//----------------------------------------------------------------

    /* Registra una nuova schermata (opzionale, se usi schermate dinamiche) */
	public void registraSchermata(String nome, javax.swing.JPanel schermata);
	
	//----------------------------------------------------------------
    
}

