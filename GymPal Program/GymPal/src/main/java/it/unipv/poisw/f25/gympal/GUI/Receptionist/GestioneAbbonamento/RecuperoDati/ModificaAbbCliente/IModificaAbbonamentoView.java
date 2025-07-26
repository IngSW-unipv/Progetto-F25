package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IModificaAbbonamentoView {
	
	public List<JToggleButton> getBottoniToggle();
	
	//----------------------------------------------------------------
	
	public List<JCheckBox> getCheckBoxes();
	
	//----------------------------------------------------------------

	public void setLists(IDatiCliente abbDTO);
	
	//----------------------------------------------------------------
	
	public JPanel getCorsiPanel();
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
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
	
	public JSplitPane getSubEditorSplitPane();
	
	//----------------------------------------------------------------
	
}
