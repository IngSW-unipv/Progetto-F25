package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public interface ISubscriptionCustomizationView {

    //----------------------------------------------------------------
    
    public List<JToggleButton> getBottoniToggle();
    
    //----------------------------------------------------------------
    
    public List<JCheckBox> getCheckBoxes();
    
    //----------------------------------------------------------------
    
    public JPanel getCorsiPanel();
    
    //----------------------------------------------------------------
    
    public JSplitPane getSplitPaneChild();
    
    //----------------------------------------------------------------
    
    public JButton getAvantiButton();
    
    //----------------------------------------------------------------
    
    public JButton getAnnullaButton();
    
    //----------------------------------------------------------------
    
    // Metodo aggiunto per ottenere il componente Swing principale
    public JPanel getMainPanel();
    
    //----------------------------------------------------------------
}
