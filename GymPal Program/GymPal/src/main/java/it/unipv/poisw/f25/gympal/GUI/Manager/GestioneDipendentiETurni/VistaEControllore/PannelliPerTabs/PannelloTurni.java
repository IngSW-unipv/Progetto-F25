package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class PannelloTurni extends JPanel{

	private static final long serialVersionUID = 1L;

    private JTable turniTable;
    private JFormattedTextField dataInizioField;
    private JFormattedTextField dataFineField;
    private JButton filtraBtn;

    private JFormattedTextField dataTurnoField;
    private JComboBox<String> recMatBox;
    private JComboBox<String> recPomBox;
    private JComboBox<String> ptMatBox;
    private JComboBox<String> ptPomBox;

    private JButton creaBtn;
    private JButton modificaBtn;
    private JButton cancellaBtn;
    private JButton pulisciBtn;

    private final IDynamicButtonSizeSetter buttonSizeSetter;

    //------------------------------------------------------------

    public PannelloTurni(IDynamicButtonSizeSetter buttonSizeSetter) {
    	
        this.buttonSizeSetter = buttonSizeSetter;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createLineBorder(Color.GRAY),
                  new EmptyBorder(10, 10, 10, 10)));

        initFiltroPanel();
        initTable();
        add(createGestioneTurniPanel());
        
    }

    //------------------------------------------------------------

    private void initFiltroPanel() {
    	
        JPanel filtroPanel = new JPanel();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter df = new DateFormatter(format);

        dataInizioField = new JFormattedTextField(df);
        dataFineField = new JFormattedTextField(df);
        dataInizioField.setColumns(8);
        dataFineField.setColumns(8);

        dataInizioField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        dataFineField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        
		dataInizioField.addFocusListener(new FocusAdapter() {
			
		    @Override
		    public void focusLost(FocusEvent e) {
		    	
		        if (dataInizioField.getText().trim().isEmpty()) {
		        	
		            dataInizioField.setValue(null);
		            
		        }
		        
		    }
		    
		});

		dataFineField.addFocusListener(new FocusAdapter() {
			
		    @Override
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

    //------------------------------------------------------------

    private void initTable() {
    	
        turniTable = new JTable(); 
        turniTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(turniTable);
        scrollPane.setPreferredSize(new Dimension(0, 700));
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        add(scrollPane);
        
    }

    //------------------------------------------------------------

    private JPanel createGestioneTurniPanel() {
    	
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Gestione Turni"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dataTurnoField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        dataTurnoField.setColumns(8);
        recMatBox = new JComboBox<>();
        recPomBox = new JComboBox<>();
        ptMatBox = new JComboBox<>();
        ptPomBox = new JComboBox<>();

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Data:"));
        inputPanel.add(dataTurnoField);
        inputPanel.add(new JLabel("Rec. Mattina:"));
        inputPanel.add(recMatBox);
        inputPanel.add(new JLabel("Rec. Pomeriggio:"));
        inputPanel.add(recPomBox);
        inputPanel.add(new JLabel("PT Mattina:"));
        inputPanel.add(ptMatBox);
        inputPanel.add(new JLabel("PT Pomeriggio:"));
        inputPanel.add(ptPomBox);
        
        /*############################################################*/

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        creaBtn = new JButton("Crea");
        modificaBtn = new JButton("Modifica");
        cancellaBtn = new JButton("Cancella");
        pulisciBtn = new JButton("Pulisci vecchi");

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

    public JTable getTurniTable() {
    	
        return turniTable;
        
    }
    
    //------------------------------------------------------------

    public String getDataInizioFiltro() {
    	
        return dataInizioField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getDataFineFiltro() {
    	
        return dataFineField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getDataTurno() {    	
    	
        return dataTurnoField.getText().trim();
        
    }
    
    //------------------------------------------------------------

    public String getRecMat() {
    	
        return (String) recMatBox.getSelectedItem();
        
    }
    
    //------------------------------------------------------------

    public String getRecPom() {
    	
        return (String) recPomBox.getSelectedItem();
        
    }
    
    //------------------------------------------------------------

    public String getPtMat() {
    	
        return (String) ptMatBox.getSelectedItem();
        
    }
    
    //------------------------------------------------------------

    public String getPtPom() {
    	
        return (String) ptPomBox.getSelectedItem();
        
    }
    
    //------------------------------------------------------------
    
    public void setDataTurno(String data) {
    	
        dataTurnoField.setText(data);
        
    }
    
    //------------------------------------------------------------

    public void setRecMat(String id) {
    	
        recMatBox.setSelectedItem(id);
        
    }
    
    //------------------------------------------------------------
    
    public void setRecPom(String id) {
    	
    	recPomBox.setSelectedItem(id);
    	
    }
    
    //------------------------------------------------------------
    
    public void setPtMat(String id) {
    	
    	ptMatBox.setSelectedItem(id);
    	
    }
    
    //------------------------------------------------------------
    
    public void setPtPom(String id) {
    	
    	ptPomBox.setSelectedItem(id);
    	
    }
    
    //------------------------------------------------------------
    
    public void setPtOptions(List<String> ptIds) {
    	
        ptMatBox.setModel(new DefaultComboBoxModel<>(ptIds.toArray(new String[0])));
        ptPomBox.setModel(new DefaultComboBoxModel<>(ptIds.toArray(new String[0])));
        
    }
    
    //------------------------------------------------------------

    public void setReceptionistsOptions(List<String> recIds) {
    	
        recMatBox.setModel(new DefaultComboBoxModel<>(recIds.toArray(new String[0])));
        recPomBox.setModel(new DefaultComboBoxModel<>(recIds.toArray(new String[0])));
        
    }
    
    //------------------------------------------------------------

    public void pulisciCampi() {
        dataTurnoField.setText("");
        recMatBox.setSelectedIndex(-1);
        recPomBox.setSelectedIndex(-1);
        ptMatBox.setSelectedIndex(-1);
        ptPomBox.setSelectedIndex(-1);
    }

    //------------------------------------------------------------

    public void addFiltroBtnListener(ActionListener listener) {
    	
        filtraBtn.addActionListener(listener);
        
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

    public void addTabellaTurniSelectionListener(ListSelectionListener listener) {
    	
        turniTable.getSelectionModel().addListSelectionListener(listener);
        
    }
    
    //------------------------------------------------------------

}
