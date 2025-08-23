package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class PannelloDipendenti extends JPanel{

	private static final long serialVersionUID = 1L;

    private JTable dipendentiTable;

    private JTextField staffIdField;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField contattoField;

    private JButton creaBtn;
    private JButton modificaBtn;
    private JButton cancellaBtn;
    private JButton pulisciBtn;

    private final IDynamicButtonSizeSetter buttonSizeSetter;

    //------------------------------------------------------------

    public PannelloDipendenti(IDynamicButtonSizeSetter buttonSizeSetter) {

        this.buttonSizeSetter = buttonSizeSetter;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createLineBorder(Color.GRAY),
                  new EmptyBorder(10, 10, 10, 10)));

        initTable();
        add(createGestioneDipendentiPanel());
        
    }

    //------------------------------------------------------------

    private void initTable() {

        dipendentiTable = new JTable();
        dipendentiTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(dipendentiTable);
        scrollPane.setPreferredSize(new Dimension(0, 700));
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);

        add(scrollPane);
        
    }

    //------------------------------------------------------------

    private JPanel createGestioneDipendentiPanel() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Gestione Dipendenti"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        staffIdField = new JTextField(20);
        nomeField = new JTextField(10);
        cognomeField = new JTextField(10);
        contattoField = new JTextField(20);

        inputPanel.add(new JLabel("Staff ID:"));
        inputPanel.add(staffIdField);
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Cognome:"));
        inputPanel.add(cognomeField);
        inputPanel.add(new JLabel("Contatto:"));
        inputPanel.add(contattoField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        creaBtn = new JButton("Crea");
        modificaBtn = new JButton("Modifica");
        cancellaBtn = new JButton("Cancella");
        pulisciBtn = new JButton("Pulisci");

        buttonSizeSetter.uniformButtonSize(creaBtn, modificaBtn, 
        								   cancellaBtn, pulisciBtn);

        buttonPanel.add(creaBtn);
        buttonPanel.add(modificaBtn);
        buttonPanel.add(cancellaBtn);
        buttonPanel.add(pulisciBtn);

        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        return mainPanel;
        
    }

    //------------------------------------------------------------

    public JTable getDipendentiTable() {
    	
        return dipendentiTable;
        
    }
    
    //------------------------------------------------------------

    public String getStaffId() {
    	
        return staffIdField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getNome() {
    	
        return nomeField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getCognome() {
    	
        return cognomeField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getContatto() {
    	
        return contattoField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public void setStaffId(String staffId) {
    	
        this.staffIdField.setText(staffId);
        
    }
    
    //------------------------------------------------------------

    public void setNome(String nome) {
    	
        this.nomeField.setText(nome);
        
    }
    
    //------------------------------------------------------------

    public void setCognome(String cognome) {
    	
        this.cognomeField.setText(cognome);
        
    }
    
    //------------------------------------------------------------

    public void setContatto(String contatto) {
    	
        this.contattoField.setText(contatto);
        
    }
    
    //------------------------------------------------------------

    public void pulisciCampi() {
    	
        staffIdField.setText("");
        nomeField.setText("");
        cognomeField.setText("");
        contattoField.setText("");
        
    }

    //------------------------------------------------------------

    public void addCreaBtnListener(ActionListener listener) {
    	
        creaBtn.addActionListener(listener);
        
    }
    
    //------------------------------------------------------------

    public void addModificaBtnListener(ActionListener listener) {
    	
        modificaBtn.addActionListener(listener);
        
    }
    
    //------------------------------------------------------------

    public void addCancellaBtnListener(ActionListener listener) {
    	
        cancellaBtn.addActionListener(listener);
        
    }
    
    //------------------------------------------------------------

    public void addPulisciBtnListener(ActionListener listener) {
    	
        pulisciBtn.addActionListener(listener);
        
    }
    
    //------------------------------------------------------------

    public void addDipendentiTableSelectionListener(ListSelectionListener listener) {
    	
        dipendentiTable.getSelectionModel().addListSelectionListener(listener);
        
    }
    
    //------------------------------------------------------------

}
