package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;

public class EliminaProfiloView extends JPanel implements IEliminaProfiloView {

	private static final long serialVersionUID = 1L;
	
	/*Labels*/
	private JLabel messaggio;
	private JLabel cf;
	
	/*Operazioni*/
	private JButton conferma;
	private JButton annulla;
	
	/*Navigazione*/
	private JButton indietro;
	
	/*Pannelli & SplitPane*/
	private JSplitPane mainSplitPanel;
	
	/*Servizi*/
	private IDynamicButtonSizeSetter buttonSizeSetter;
	
	//----------------------------------------------------------------
	
	public EliminaProfiloView(IDynamicButtonSizeSetter setter,
							  IFontChangeRegister fontChangeRegister){
		
		this.buttonSizeSetter = setter;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 5, 10, 5));

        initLabels();
        JPanel upperPanel = buildUpperPanel();
        JPanel navigationPanel = buildNavigationPanel();
        this.mainSplitPanel = buildMainSplitPanel(upperPanel, navigationPanel);

        add(mainSplitPanel);

        fontChangeRegister.register(this, buttonSizeSetter);
    }

    //--------------------------------------------------------------
	
    private void initLabels() {
    	
        cf = new JLabel("Codice fiscale cliente: ");
        messaggio = new JLabel("Procedere all'eliminazione del profilo cliente"
        					 + " corrispondente al CF fornito dal DataBase?");
        cf.setAlignmentX(CENTER_ALIGNMENT);
        messaggio.setAlignmentX(CENTER_ALIGNMENT);
        
    }

    //--------------------------------------------------------------
    
    private JPanel buildUpperPanel() {
    	
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBorder(BorderFactory.createTitledBorder("ATTENZIONE - OPERAZIONE IRREVERSIBILE"));

        conferma = new JButton("Conferma");
        buttonSizeSetter.uniformButtonSize(conferma);

        JLabel titleLabel = new JLabel("-=Eliminazione profilo Cliente=-");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        conferma.setAlignmentX(CENTER_ALIGNMENT);

        upperPanel.add(Box.createVerticalGlue());
        upperPanel.add(titleLabel);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(cf);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(messaggio);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(conferma);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(Box.createVerticalGlue());

        return upperPanel;
        
    }

    //--------------------------------------------------------------
    
    private JPanel buildNavigationPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        annulla = new JButton("Annulla");
        indietro = new JButton("Indietro");

        buttonSizeSetter.uniformButtonSize(indietro, annulla);

        panel.add(Box.createHorizontalGlue());
        panel.add(annulla);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(indietro);
        panel.add(Box.createHorizontalGlue());

        return panel;
        
    }

    //--------------------------------------------------------------
    
    private JSplitPane buildMainSplitPanel(JPanel upper, JPanel navigation) {
    	
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(upper);
        split.setBottomComponent(navigation);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.80));
        split.setEnabled(false);
        return split;
        
    }

    //--------------------------------------------------------------
    
    @Override
    public void addConfermaListener(ActionListener listener) {
    	
        conferma.addActionListener(listener);
        
    }
    
    //--------------------------------------------------------------

    @Override
    
    public void addAnnullaListener(ActionListener listener) {
    	
        annulla.addActionListener(listener);
        
    }

    //--------------------------------------------------------------
    
    @Override
    public void addIndietroListener(ActionListener listener) {
    	
        indietro.addActionListener(listener);
        
    }
    
    //--------------------------------------------------------------

    @Override
    public JLabel getCfLabel() {
    	
        return cf;
        
    }

    //--------------------------------------------------------------
    
    @Override
    public JPanel getMainPanel() {
    	
        return this;
        
    }
    
    //--------------------------------------------------------------
    
}
