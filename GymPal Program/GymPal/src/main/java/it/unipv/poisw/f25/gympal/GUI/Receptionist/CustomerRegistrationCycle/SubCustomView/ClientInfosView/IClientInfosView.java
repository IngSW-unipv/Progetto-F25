package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView;

import java.awt.Component;
import javax.swing.*;

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
    
    public JButton getAvantiButton();
    
    //----------------------------------------------------------------
    
    public JButton getIndietroButton();
    
    //----------------------------------------------------------------
    
    public JButton getAnnullaButton();
    
    //----------------------------------------------------------------
    
    public JSplitPane getMainSplitPanel();
    
    //----------------------------------------------------------------
    
    public JSplitPane getInformazioniUtenteSplitPanel();
    
    //----------------------------------------------------------------
    
    public JPanel getAvantiIndietroPanel();
    
    //----------------------------------------------------------------
    
    public JLabel getPermessoGenitoriLabel();
    
    //----------------------------------------------------------------
    
    public JButton getAcquisisciPermesso();
    
    //----------------------------------------------------------------
    
    public Component getRootComponent();
    
    //----------------------------------------------------------------

}
