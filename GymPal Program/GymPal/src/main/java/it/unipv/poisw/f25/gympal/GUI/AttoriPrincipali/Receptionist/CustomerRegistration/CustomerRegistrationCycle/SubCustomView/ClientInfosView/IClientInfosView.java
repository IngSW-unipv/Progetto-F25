package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView;


import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

public interface IClientInfosView {
    
    //----------------------------------------------------------------
    
    public JTextField getNome();
    
    //----------------------------------------------------------------
    
    public JTextField getCognome();
    
    //----------------------------------------------------------------
    
    public JTextField getCodiceFiscale();
    
    //----------------------------------------------------------------
    
    public JTextField getContatto();
    
    //----------------------------------------------------------------
    
    public JSpinner getDateSpinner();
    
    //----------------------------------------------------------------
    
    public void addDateSpinnerListener(ChangeListener listener);
    
    //----------------------------------------------------------------
    
    public JRadioButton getMaschio();
    
    //----------------------------------------------------------------
    
    public JRadioButton getFemmina();
    
    //----------------------------------------------------------------
    
    public JRadioButton getCertIdoneitàSi();
    
    //----------------------------------------------------------------
    
    public JRadioButton getCertIdoneitàNo();
    
    //----------------------------------------------------------------
    
    public JRadioButton getPermessoGenitoriSi();
    
    //----------------------------------------------------------------
    
    public JRadioButton getPermessoGenitoriNo();
    
    //----------------------------------------------------------------
    
    public void addAvantiListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    public void addIndietroListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    public void addAnnullaListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    public JSplitPane getMainSplitPanel();
    
    //----------------------------------------------------------------
    
    public JSplitPane getInformazioniUtenteSplitPanel();
    
    //----------------------------------------------------------------
    
    public JPanel getNavigationPanel();
    
    //----------------------------------------------------------------
    
    public JLabel getPermessoGenitoriLabel();
    
    //----------------------------------------------------------------
    
    public void addAcquisisciPermessoListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    public JPanel getMainPanel();
    
    //----------------------------------------------------------------
    
    public void setBtnsVisibility(boolean flag);
    
    //----------------------------------------------------------------

}
