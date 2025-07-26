package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;

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
    
    public void addAvantiListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    public void addAnnullaListener(ActionListener listener);
    
    //----------------------------------------------------------------
    
    // Metodo aggiunto per ottenere il componente Swing principale
    public JPanel getMainPanel();
    
    //----------------------------------------------------------------
}
