package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class SubscriptionCustomizationView extends JPanel implements ISubscriptionCustomizationView {

    private static final long serialVersionUID = 1L;

    /**/
    private final List<String> nomiSezioni = Arrays.asList(
        "Allenamento mirato con personal trainer",
        "Sala allenamento a corpo libero",
        "Sala pesi",
        "Sala corsi"
    );

    private final List<String> nomiCorsi = Arrays.asList(
        "Crossfit", "Yoga", "Pilates", "Zumba", "Fullbody"
    );

    /*Sezioni & Corsi*/
    private List<JToggleButton> toggleButtons;
    private List<JCheckBox> checkBoxes;

    /*Pannelli & SplitPane*/
    private JPanel corsiPanel;
    private JSplitPane splitPaneChild;
    private JSplitPane splitPaneFather;

    /*Navigazione*/
    private JButton avanti;
    private JButton annulla;

    /*Servizi*/
    private IDynamicButtonSizeSetter buttonSizeSetter;

	//----------------------------------------------------------------

	public SubscriptionCustomizationView (IDynamicButtonSizeSetter setter,
										  IFontChangeRegister fontChangeRegister) {
		
		/*Servizi*/
        this.buttonSizeSetter = setter;
        
        /*Layout Pannello principale*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 5, 10, 5));

        JLabel messageLabel = new JLabel("-=Componi l'abbonamento=-");
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);

        /*Costruzione contenuto pannello*/
        initToggleButtons();
        initCheckBoxes();
        styleButtons();

        initCorsiPanel();
        initSplitPanes(messageLabel);
        initNavigationPanel();

        add(splitPaneFather);
        add(Box.createVerticalGlue());

        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
		
	}

	//----------------------------------------------------------------

    private void initToggleButtons() {
    	
    	toggleButtons = new ArrayList<>();
    	
        for (String nome : nomiSezioni) {
        	
            JToggleButton toggle = new JToggleButton(nome);
            toggle.setAlignmentX(CENTER_ALIGNMENT);
            toggleButtons.add(toggle);
            
        }
        
    }
	
	//----------------------------------------------------------------

    private void initCheckBoxes() {
    	
    	checkBoxes = new ArrayList<>();
    	
        for (String nome : nomiCorsi) {
        	
            JCheckBox checkBox = new JCheckBox(nome);
            checkBox.setAlignmentX(CENTER_ALIGNMENT);
            checkBoxes.add(checkBox);
            
        }
        
    }

	//----------------------------------------------------------------
	
    private void styleButtons() {
    	
        List<AbstractButton> all = new ArrayList<>();
        all.addAll(toggleButtons);
        all.addAll(checkBoxes);

        for (AbstractButton btn : all) {
        	
            btn.setBackground(Color.decode("#ffcccc"));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);
            btn.setFocusPainted(true);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
        }
        
    }

	//----------------------------------------------------------------
	
    private void initCorsiPanel() {
    	
    	corsiPanel = new JPanel();
        corsiPanel.setLayout(new BoxLayout(corsiPanel, BoxLayout.Y_AXIS));
        corsiPanel.setBorder(BorderFactory.createCompoundBorder(
        					 BorderFactory.createTitledBorder("Selezione Corsi"),
        					 BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        for (JCheckBox cb : checkBoxes) {
        	
            corsiPanel.add(Box.createVerticalStrut(10));
            corsiPanel.add(cb);
            
        }

        corsiPanel.setVisible(false);
        
    }
	
	//----------------------------------------------------------------
	
    private void initSplitPanes(JLabel messageLabel) {
    	
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBorder(BorderFactory.createCompoundBorder(
        					 BorderFactory.createTitledBorder(""),
        					 BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        upperPanel.add(messageLabel);
        upperPanel.add(Box.createVerticalStrut(30));

        for (JToggleButton btn : toggleButtons) {
        	
            upperPanel.add(btn);
            upperPanel.add(Box.createVerticalStrut(10));
            
        }

        splitPaneChild = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneChild.setTopComponent(upperPanel);
        splitPaneChild.setBottomComponent(corsiPanel);
        splitPaneChild.setEnabled(false);

        splitPaneFather = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneFather.setTopComponent(splitPaneChild);

        // Divider set after layout is ready
        SwingUtilities.invokeLater(() -> splitPaneFather.setDividerLocation(0.8));
        splitPaneFather.setEnabled(false);
        
    }
	
	//----------------------------------------------------------------
	
    private void initNavigationPanel() {
    	
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        navPanel.setBorder(BorderFactory.createTitledBorder(""));
        
        avanti = new JButton("Avanti");
        annulla = new JButton("Annulla");

        buttonSizeSetter.uniformButtonSize(avanti, annulla);

        navPanel.add(Box.createHorizontalGlue());
        navPanel.add(annulla);
        navPanel.add(Box.createHorizontalStrut(200));
        navPanel.add(avanti);
        navPanel.add(Box.createHorizontalGlue());

        splitPaneFather.setBottomComponent(navPanel);
        
    }
	
	//----------------------------------------------------------------	
	
    @Override
    public List<JToggleButton> getBottoniToggle() {
    	
        return toggleButtons;
        
    }
	
	//----------------------------------------------------------------
	
    @Override
    public List<JCheckBox> getCheckBoxes() {
    	
        return checkBoxes;
        
    }
	
	//----------------------------------------------------------------
	
    @Override
    public JPanel getCorsiPanel() {
    	
        return corsiPanel;
        
    }
	
	//----------------------------------------------------------------
    

    @Override
    public JSplitPane getSplitPaneChild() {
    	
        return splitPaneChild;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public List<String> getNomiSezioni() {
    	
        return nomiSezioni;
        
    }
    
	//----------------------------------------------------------------
    

    @Override
    public List<String> getNomiCorsi() {
    	
        return nomiCorsi;
        
    }
    
	//----------------------------------------------------------------
    

    @Override
    public void addAvantiListener(ActionListener listener) {
    	
        avanti.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public void addAnnullaListener(ActionListener listener) {
    	
        annulla.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------    

    @Override
    public JPanel getMainPanel() {
    	
        return this;
        
    }
    
	//----------------------------------------------------------------

	
}
