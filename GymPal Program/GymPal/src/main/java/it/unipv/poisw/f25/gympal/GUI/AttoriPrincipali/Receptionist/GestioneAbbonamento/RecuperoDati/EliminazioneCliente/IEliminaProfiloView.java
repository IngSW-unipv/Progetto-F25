package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public interface IEliminaProfiloView {
	
	public void addConfermaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAnnullaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addIndietroListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public JLabel getCfLabel();
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
	//----------------------------------------------------------------

}
