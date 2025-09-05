package it.unipv.poisw.f25.gympal.GUI.LoginELogout.LogoutView;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class LogoutConfirmationView extends JPanel implements ILogoutConfirmationView {

    private static final long serialVersionUID = 1L;
    
    private JButton confirmButton;
    private JButton cancelButton;
    
	//----------------------------------------------------------------    

    public LogoutConfirmationView(IFontChangeRegister fontChangeRegister,
    							  IDynamicButtonSizeSetter buttonSizeSetter) {
    	
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel("Sei sicuro di voler uscire?");
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);

        confirmButton = new JButton("Sì");
        confirmButton.setAlignmentX(CENTER_ALIGNMENT);

        cancelButton = new JButton("No");
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        
        buttonSizeSetter.uniformButtonSize(confirmButton, cancelButton);

        add(Box.createVerticalGlue());
        add(messageLabel);
        add(Box.createVerticalStrut(20));
        add(confirmButton);
        add(Box.createVerticalStrut(10));
        add(cancelButton);
        add(Box.createVerticalGlue());
        
        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
        
    }
    
	//----------------------------------------------------------------

    @Override
    public JButton getConfirmButton() {
        return confirmButton;
    }
    
	//----------------------------------------------------------------

    @Override
    public JButton getCancelButton() {
        return cancelButton;
    }
    
	//----------------------------------------------------------------
    
    @Override
    public JPanel getMainPanel() {return this;}
    
	//----------------------------------------------------------------
    
}
