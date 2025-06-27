package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public interface IRecuperoDatiView {
	
	public JTextField getCodiceFiscale();
	
	//----------------------------------------------------------------
	
	public JLabel getCfLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getNomeLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getCognomeLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getContattoLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getSessoLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getDurataAbbonamentoLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getInizioAbbLabel();
	
	//----------------------------------------------------------------
	
	public JLabel getFineAbbLabel();
	
	//----------------------------------------------------------------
	
	public JButton getEstraiButton();
	
	//----------------------------------------------------------------
	
	public JButton getAnnullaButton();
	
	//----------------------------------------------------------------
	
	public JButton getRinnovaButton();
	
	//----------------------------------------------------------------
	
	public JButton getModificaButton();
	
	//----------------------------------------------------------------
	
	public JButton getEliminaButton();
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
	//----------------------------------------------------------------
	
	public void setRinnovaEnabled(boolean enabled);
	
	//----------------------------------------------------------------
	
	public void setModificaEnabled(boolean enabled);
	
	//----------------------------------------------------------------
	
	public void setEliminaEnabled(boolean enabled);
	
	//----------------------------------------------------------------

}
