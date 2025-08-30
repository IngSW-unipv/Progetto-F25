package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;

public class PannelloEventi extends JPanel {

	private static final long serialVersionUID = 1L;

	/*Tabella & Filtro*/
    private JTable eventiTable;
    private JFormattedTextField dataInizioField;
    private JFormattedTextField dataFineField;
    private JButton filtraBtn;

    /*Input*/
    private JTextField nomeEventoField;
    private JFormattedTextField dataEventoField;
    private JTextField oraInizioField;
    private JTextField oraFineField;
    private JTextField destinatarioField;
    private JTextArea messaggioArea;

    /*Operazioni*/
    private JButton creaBtn;
    private JButton modificaBtn;
    private JButton cancellaBtn;
    private JButton pulisciBtn;

    /*Servizi*/
    private IDynamicButtonSizeSetter buttonSizeSetter;
    
	//----------------------------------------------------------------

    public PannelloEventi(IDynamicButtonSizeSetter buttonSizeSetter,
    					  IFontChangeRegister fontChangeRegister) {
    	
        this.buttonSizeSetter = buttonSizeSetter;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createLineBorder(Color.GRAY),
                  new EmptyBorder(10, 10, 10, 10)));

        initFiltroPanel();
        initTable();
        add(createGestioneEventiPanel());
        
        fontChangeRegister.register(this, buttonSizeSetter);
        
    }
    
	//----------------------------------------------------------------

    private void initFiltroPanel() {
    	
        JPanel filtroPanel = new JPanel();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter df = new DateFormatter(format);

        dataInizioField = new JFormattedTextField(df);
        dataFineField = new JFormattedTextField(df);
        dataInizioField.setColumns(8);
        dataFineField.setColumns(8);

        dataInizioField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        dataFineField.setFocusLostBehavior(JFormattedTextField.PERSIST);

        dataInizioField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (dataInizioField.getText().trim().isEmpty()) {
                    dataInizioField.setValue(null);
                }
            }
        });

        dataFineField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (dataFineField.getText().trim().isEmpty()) {
                    dataFineField.setValue(null);
                }
            }
        });

        filtraBtn = new JButton("Filtra");
        buttonSizeSetter.uniformButtonSize(filtraBtn);

        filtroPanel.add(new JLabel("Data inizio:"));
        filtroPanel.add(dataInizioField);
        filtroPanel.add(new JLabel("Data fine:"));
        filtroPanel.add(dataFineField);
        filtroPanel.add(filtraBtn);

        add(filtroPanel, BorderLayout.NORTH);
        
    }
    
	//----------------------------------------------------------------

    private void initTable() {
    	
        eventiTable = new JTable();
        eventiTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(eventiTable);
        scrollPane.setPreferredSize(new Dimension(0, 700));
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        add(scrollPane);
        
    }
    
	//----------------------------------------------------------------

    private JPanel createGestioneEventiPanel() {
    	
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Gestione Eventi"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nomeEventoField = new JTextField(10);
        dataEventoField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        dataEventoField.setColumns(8);
        oraInizioField = new JTextField(5);
        oraFineField = new JTextField(5);
        destinatarioField = new JTextField(10);
        messaggioArea = new JTextArea(3, 20);
        messaggioArea.setLineWrap(true);
        messaggioArea.setWrapStyleWord(true);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Nome evento:"));
        inputPanel.add(nomeEventoField);
        inputPanel.add(new JLabel("Data:"));
        inputPanel.add(dataEventoField);
        inputPanel.add(new JLabel("Ora inizio:"));
        inputPanel.add(oraInizioField);
        inputPanel.add(new JLabel("Ora fine:"));
        inputPanel.add(oraFineField);
        inputPanel.add(new JLabel("Destinatario:"));
        inputPanel.add(destinatarioField);

        JPanel msgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        msgPanel.add(new JLabel("Messaggio:"));
        msgPanel.add(new JScrollPane(messaggioArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        creaBtn = new JButton("Crea");
        modificaBtn = new JButton("Modifica");
        cancellaBtn = new JButton("Cancella");
        pulisciBtn = new JButton("Pulisci vecchi");

        buttonSizeSetter.uniformButtonSize(creaBtn, modificaBtn, cancellaBtn, pulisciBtn);

        buttonPanel.add(creaBtn);
        buttonPanel.add(modificaBtn);
        buttonPanel.add(cancellaBtn);
        buttonPanel.add(pulisciBtn);

        mainPanel.add(inputPanel);
        mainPanel.add(msgPanel);
        mainPanel.add(buttonPanel);

        return mainPanel;
        
    }
    
	//----------------------------------------------------------------

    public JTable getEventiTable() {
    	
        return eventiTable;
        
    }
    
	//----------------------------------------------------------------

    public String getDataInizioFiltro() {
    	
        return dataInizioField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getDataFineFiltro() {
    	
        return dataFineField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getNomeEvento() {
    	
        return nomeEventoField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getDataEvento() {
    	
        return dataEventoField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getOraInizio() {
    	
        return oraInizioField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getOraFine() {
    	
        return oraFineField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getDestinatario() {
    	
        return destinatarioField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getMessaggio() {
    	
        return messaggioArea.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public void setEventoFields(String nome, String data, String oraInizio, 
    							String oraFine, String msg, String destinatario) {
    	
        nomeEventoField.setText(nome);
        dataEventoField.setText(data);
        oraInizioField.setText(oraInizio);
        oraFineField.setText(oraFine);
        messaggioArea.setText(msg);
        destinatarioField.setText(destinatario);
        
    }
    
	//----------------------------------------------------------------

    public void pulisciCampi() {
    	
        nomeEventoField.setText("");
        dataEventoField.setText("");
        oraInizioField.setText("");
        oraFineField.setText("");
        destinatarioField.setText("");
        messaggioArea.setText("");
        
    }
    
	//----------------------------------------------------------------

    public void addFiltroBtnListener(ActionListener listener) {
    	
        filtraBtn.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    public void addCreaBtnListener(ActionListener listener) {
    	
        creaBtn.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    public void addModificaBtnListener(ActionListener listener) {
    	
        modificaBtn.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    public void addCancellaBtnListener(ActionListener listener) {
    	
        cancellaBtn.addActionListener(listener);
        
    }

	//----------------------------------------------------------------
    
    public void addPulisciBtnListener(ActionListener listener) {
    	
        pulisciBtn.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    public void addTabellaEventiSelectionListener(ListSelectionListener listener) {
    	
        eventiTable.getSelectionModel().addListSelectionListener(listener);
        
    }
    
	//----------------------------------------------------------------

}
