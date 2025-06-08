package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public interface ISubscriptionCustomizationView {

    //----------------------------------------------------------------
    
    List<JToggleButton> getBottoniToggle();
    
    //----------------------------------------------------------------
    
    List<JCheckBox> getCheckBoxes();
    
    //----------------------------------------------------------------
    
    JPanel getCorsiPanel();
    
    //----------------------------------------------------------------
    
    JSplitPane getSplitPaneChild();
    
    //----------------------------------------------------------------
    
    JButton getAvantiButton();
    
    //----------------------------------------------------------------
    
    JButton getAnnullaButton();
    
    //----------------------------------------------------------------
    
    // Metodo aggiunto per ottenere il componente Swing principale
    JPanel getMainPanel();
    
    //----------------------------------------------------------------
}
