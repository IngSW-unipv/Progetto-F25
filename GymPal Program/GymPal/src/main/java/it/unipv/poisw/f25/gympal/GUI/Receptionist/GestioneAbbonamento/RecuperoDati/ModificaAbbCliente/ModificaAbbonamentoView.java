package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class ModificaAbbonamentoView extends JPanel implements IModificaAbbonamentoView {

    private static final long serialVersionUID = 1L;

    private JButton annulla;
    private JButton avanti;
    private JButton indietro;

    private JButton conferma;
    private JButton resetta;
    
    private List<JToggleButton> toggleButtons;
    private List<JCheckBox> checkBoxes;

    private JSplitPane mainSplitPanel;
    private JSplitPane displayAndModifyAbb;
    private JSplitPane subscriptionEditor;
    private JSplitPane navAndAppRevChanges;

    private JPanel componentiPanel;
    private JPanel corsiPanel;
    private JPanel listsPanel;
    private JPanel applyReverseChanges;
    private JPanel navigationPanel;
    
    private final List<String> nomiSezioni = Arrays.asList(
    		
    	    "Allenamento mirato con personal trainer",
    	    "Sala allenamento a corpo libero",
    	    "Sala pesi",
    	    "Sala corsi"
    	    
    	);

    private final List<String> nomiCorsi = Arrays.asList(
    			
    	    "Crossfit", "Yoga", "Pilates", "Zumba", "Fullbody"
    	    
    	);

    private final IDynamicButtonSizeSetter buttonSizeSetter;

    //----------------------------------------------------------------

    public ModificaAbbonamentoView(IDynamicButtonSizeSetter setter) {

    	buttonSizeSetter = setter;
    	initVista();

    }

    //----------------------------------------------------------------
    
    private void initVista() {
    	
    	setLayout(new BorderLayout());

        /*############################################################*/

        // Inizializzazione componentiPanel con layout verticale
        componentiPanel = new JPanel();
        componentiPanel.setLayout(new BoxLayout(componentiPanel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel("-=Componi l'abbonamento=-");
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);

        componentiPanel.add(messageLabel);
        componentiPanel.add(Box.createVerticalStrut(30));

        /* Creazione dinamica di toggle button dalle nomiSezioni e li aggiungo
         * a componentiPanel*/
        toggleButtons = new ArrayList<>();
        
        for (String nomeSezione : nomiSezioni) {
            JToggleButton toggleBtn = new JToggleButton(nomeSezione);
            toggleButtons.add(toggleBtn);
            componentiPanel.add(toggleBtn);
            componentiPanel.add(Box.createVerticalStrut(10));
        }

        /*############################################################*/

        // Inizializzazione corsiPanel con layout verticale
        corsiPanel = new JPanel();
        corsiPanel.setLayout(new BoxLayout(corsiPanel, BoxLayout.Y_AXIS));

        // Creazione dinamica dei checkbox da nomiCorsi e le aggiungo a corsiPanel
        checkBoxes = new ArrayList<>();
        
        for (String nomeCorso : nomiCorsi) {
            JCheckBox checkBox = new JCheckBox(nomeCorso);
            checkBoxes.add(checkBox);
            corsiPanel.add(checkBox);
            corsiPanel.add(Box.createVerticalStrut(10));
        }

        corsiPanel.setVisible(false);

        /*############################################################*/

        // Uniformazione aspetto e dimensioni di tutti i toggle e checkbox
        List<AbstractButton> allButtons = new ArrayList<>();
        allButtons.addAll(toggleButtons);
        allButtons.addAll(checkBoxes);

        for (AbstractButton btn : allButtons) {
            btn.setBackground(Color.decode("#ffcccc"));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);
            btn.setFocusPainted(true);
            btn.setAlignmentX(CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }

        /*############################################################*/

        /*Configurazione split pane verticale per componentiPanel 
         *(toggle) e corsiPanel (checkbox)*/
        subscriptionEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        subscriptionEditor.setTopComponent(componentiPanel);
        subscriptionEditor.setBottomComponent(corsiPanel);
        subscriptionEditor.setEnabled(false);

        /*############################################################*/

        /* Inizializzazione listsPanel con layout verticale )*/
        listsPanel = new JPanel();
        listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.Y_AXIS));

        /*############################################################*/

        // Split pane orizzontale per liste e subscriptionEditor
        displayAndModifyAbb = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        displayAndModifyAbb.setLeftComponent(listsPanel);
        displayAndModifyAbb.setRightComponent(subscriptionEditor);
        displayAndModifyAbb.setEnabled(false);

        /*############################################################*/

        // Pannello navigazione (pulsanti avanti, indietro, annulla)
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));

        annulla = new JButton("Annulla");
        indietro = new JButton("Indietro");
        avanti = new JButton("Avanti");

        // Uniformazione dimensione dei pulsanti di navigazione
        buttonSizeSetter.uniformButtonSize(annulla, indietro, avanti);

        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(annulla);
        navigationPanel.add(Box.createHorizontalStrut(100));
        navigationPanel.add(indietro);
        navigationPanel.add(Box.createHorizontalStrut(100));
        navigationPanel.add(avanti);
        navigationPanel.add(Box.createHorizontalGlue());

        /*############################################################*/

        // Pannello per pulsanti reset e conferma
        applyReverseChanges = new JPanel();
        applyReverseChanges.setLayout(new BoxLayout(applyReverseChanges, BoxLayout.X_AXIS));

        resetta = new JButton("Resetta composizione");
        conferma = new JButton("Conferma composzione");

        // Uniformazione dimensione dei pulsanti di conferma e reset
        buttonSizeSetter.uniformButtonSize(resetta, conferma);

        applyReverseChanges.add(Box.createHorizontalGlue());
        applyReverseChanges.add(resetta);
        applyReverseChanges.add(Box.createHorizontalStrut(100));
        applyReverseChanges.add(conferma);
        applyReverseChanges.add(Box.createHorizontalGlue());

        /*############################################################*/

        // Split pane verticale per pannelli di navigazione e conferma/reset
        navAndAppRevChanges = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        navAndAppRevChanges.setTopComponent(applyReverseChanges);
        navAndAppRevChanges.setBottomComponent(navigationPanel);
        navAndAppRevChanges.setEnabled(false);

        /*############################################################*/

        // Split pane verticale principale
        mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplitPanel.setTopComponent(displayAndModifyAbb);
        mainSplitPanel.setBottomComponent(navAndAppRevChanges);
        mainSplitPanel.setEnabled(false);

        /*############################################################*/

        // Aggiungo il mainSplitPanel al contenuto della vista
        add(mainSplitPanel);

        // Imposto la posizione del divider dopo che il layout è stato calcolato
        SwingUtilities.invokeLater(() -> mainSplitPanel.setDividerLocation(0.80));

        mainSplitPanel.setEnabled(false);
    	
    }
    
    //----------------------------------------------------------------

    private JScrollPane listaAScorrimento(List<String> selezione) {

        JList<String> lista = new JList<>(selezione != null ? selezione.toArray(new String[0]) : new String[0]);
        lista.setEnabled(false);

        return new JScrollPane(lista);

    }

    //----------------------------------------------------------------

    private void makeLabeledList(JPanel panel, String label, List<String> campoDTO) {

        panel.add(new JLabel(label));
        panel.add(Box.createVerticalStrut(30));
        panel.add(listaAScorrimento(campoDTO));
        panel.add(Box.createVerticalStrut(60));

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
    public void setLists(List<String> sezioniAbbonamento,
                         List<String> corsiSelezionati) {

        listsPanel.removeAll();

        makeLabeledList(listsPanel, "Componenti abbonamento selezionate: ",
                sezioniAbbonamento);

        makeLabeledList(listsPanel, "Corsi selezionati: ", corsiSelezionati);

        revalidate();
        repaint();

    }

    //----------------------------------------------------------------

    @Override
    public JPanel getCorsiPanel() {

        return corsiPanel;

    }

    //----------------------------------------------------------------

    @Override
    public JPanel getMainPanel() {

        return this;

    }

    //----------------------------------------------------------------

    @Override
    public JSplitPane getSubEditorSplitPane() {

        return subscriptionEditor;

    }

    //----------------------------------------------------------------
    
    @Override
    public List<String> getNomiSezioni(){
    	
    	return nomiSezioni;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> getNomiCorsi(){
    	
    	return nomiCorsi;
    	
    }
    
    //----------------------------------------------------------------

    @Override
    public void addAnnullaListener(ActionListener listener) {
        annulla.addActionListener(listener);
    }

    //----------------------------------------------------------------

    @Override
    public void addIndietroListener(ActionListener listener) {
        indietro.addActionListener(listener);
    }

    //----------------------------------------------------------------

    @Override
    public void addAvantiListener(ActionListener listener) {
        avanti.addActionListener(listener);
    }

    //----------------------------------------------------------------

    @Override
    public void addResettaListener(ActionListener listener) {
        resetta.addActionListener(listener);
    }

    //----------------------------------------------------------------

    @Override
    public void addConfermaListener(ActionListener listener) {
        conferma.addActionListener(listener);
    }

    //----------------------------------------------------------------
    
    
}
