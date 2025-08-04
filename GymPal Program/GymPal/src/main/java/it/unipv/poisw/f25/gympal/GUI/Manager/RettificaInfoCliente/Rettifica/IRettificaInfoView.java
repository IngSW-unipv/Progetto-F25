package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder.IDatiClienteReadOnly;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;

public interface IRettificaInfoView {
	
	public JTextField getCodiceFiscale();
	
	//----------------------------------------------------------------
	
	public JTextField getNome();
	
	//----------------------------------------------------------------
	
	public JTextField getCognome();
	
	//----------------------------------------------------------------
	
	public JTextField getCfAnagrafico();
	
	//----------------------------------------------------------------
	
	public JTextField getContatto();
	
	//----------------------------------------------------------------
	
	public void addDateSpinnerListener(ChangeListener listener);
	
	//----------------------------------------------------------------
	
	public JSpinner getDateSpinner();
	
	//----------------------------------------------------------------
	
	public JRadioButton getMaschio();
	
	//----------------------------------------------------------------
	
	public JRadioButton getFemmina();
	
	//----------------------------------------------------------------
	
	public void addEstraiListenr(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addEliminaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addSaveModsListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addInsDataListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAnnullaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAvantiListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
	//----------------------------------------------------------------

	public void setEliminaEnabled(boolean enabled);
	
	//----------------------------------------------------------------
	
	public void setSaveModsEnabled(boolean enabled);
	
	//----------------------------------------------------------------
	
	public void setInsDataEnabled(boolean enabled);
	
	//----------------------------------------------------------------
	
	public void setTextFieldsContent(IDatiClienteReadOnly abbDTO);
	
	//----------------------------------------------------------------
	
	public RawClientData getDatiClienteRaw();
	
	//----------------------------------------------------------------
	
	
}
