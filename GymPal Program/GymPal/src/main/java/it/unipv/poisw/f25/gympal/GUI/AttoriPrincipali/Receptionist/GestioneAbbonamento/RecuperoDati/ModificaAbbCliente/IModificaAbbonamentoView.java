package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;

public interface IModificaAbbonamentoView {
	
	public List<JToggleButton> getBottoniToggle();
	
	//----------------------------------------------------------------
	
	public List<JCheckBox> getCheckBoxes();
	
	//----------------------------------------------------------------

	public void setLists(List<String> sezioniAbbonamento, 
			 			 List<String> corsiSelezionati);
	
	//----------------------------------------------------------------
	
	public JPanel getCorsiPanel();
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
	//----------------------------------------------------------------	
	
	public JSplitPane getSubEditorSplitPane();
	
	//----------------------------------------------------------------
	
	public void addAnnullaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addIndietroListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAvantiListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addResettaListener(ActionListener listener);
	
	//----------------------------------------------------------------

	public void addConfermaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public List<String> getNomiSezioni();

	//----------------------------------------------------------------
	
	public List<String> getNomiCorsi();
	
	//----------------------------------------------------------------
	
}
