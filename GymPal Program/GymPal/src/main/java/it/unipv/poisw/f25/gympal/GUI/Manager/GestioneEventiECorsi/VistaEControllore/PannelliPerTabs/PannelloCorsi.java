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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;

public class PannelloCorsi extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	/*Tabella & Filtro*/
	private JTable sessioniTable;	
	private JTextField tipoCorsoFiltro;
	private JFormattedTextField dataInizioField;
	private JFormattedTextField dataFineField;
	private JButton filtraBtn;
	
	/*Operazioni*/
	private JButton creaBtn;
	private JButton modificaBtn;
	private JButton cancellaBtn;
	private JButton pulisciBtn;
	private JButton generaIdBtn;
	
	/*Input*/
	private JTextField tipoCorsoPerId;
	private JTextField idSessioneField;
	private JComboBox<String> staffIdField;
	private JTextField fasciaOrariaField;
	
	private JFormattedTextField dataField;
	
	/*Servizi*/
	private IDynamicButtonSizeSetter buttonSizeSetter;
	
	//----------------------------------------------------------------
	
	public PannelloCorsi(IDynamicButtonSizeSetter buttonSizeSetter,
						 IFontChangeRegister fontChangeRegister) {
		
		this.buttonSizeSetter = buttonSizeSetter;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		setBorder(BorderFactory.createCompoundBorder(
			      BorderFactory.createLineBorder(Color.GRAY), 
			      new EmptyBorder(10, 10, 10, 10)              
		));
		
        /*############################################################*/
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormatter df = new DateFormatter(format);
		
		df.setAllowsInvalid(true);
		
        /*############################################################*/

		dataInizioField = new JFormattedTextField(df);
		dataFineField = new JFormattedTextField(df);
		
		/* Evita che i due campi si ripristinino al valore precedente quando il
		 * focus sul campo è perso*/
		dataInizioField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		dataFineField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		
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
		
        /*############################################################*/
		
		JPanel filtroPanel = new JPanel();

		tipoCorsoFiltro = new JTextField(10);
		
		filtroPanel.add(new JLabel("Tipo corso:"));
		filtroPanel.add(tipoCorsoFiltro);
		filtroPanel.add(new JLabel("Data inizio:"));
		filtroPanel.add(dataInizioField);
		filtroPanel.add(new JLabel("Data fine:"));
		filtroPanel.add(dataFineField);
		filtraBtn = new JButton("Filtra");
		this.buttonSizeSetter.uniformButtonSize(filtraBtn);
		filtroPanel.add(filtraBtn);

		add(filtroPanel, BorderLayout.NORTH);
		
        /*############################################################*/
		
		sessioniTable = new JTable();
		sessioniTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(sessioniTable);
		
		/*Massime dimensioni tabella*/
		scrollPane.setPreferredSize(new Dimension(0, 700));
		
		scrollPane.setAlignmentX(CENTER_ALIGNMENT); 
		add(scrollPane);
		
        /*############################################################*/
		
		JPanel btnPanel = new JPanel();
		
		add(btnPanel, BorderLayout.SOUTH);
		add(createGestioneSessioniPanel());
		
		fontChangeRegister.register(this, buttonSizeSetter);
		
	}
	
	//----------------------------------------------------------------
	
	private JPanel createGestioneSessioniPanel() {
		
	    JPanel mainPanel = new JPanel();
	    mainPanel.setBorder(BorderFactory.createTitledBorder("Gestione Sessioni"));
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // Campi
	    idSessioneField = new JTextField(12);
	    idSessioneField.setEditable(false); // ID generato automaticamente

	    tipoCorsoPerId = new JTextField(10); // Tipo corso necessario per generare ID
	    staffIdField = new JComboBox<>();
	    staffIdField.setEditable(false);
	    fasciaOrariaField = new JTextField(8);

	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    DateFormatter df = new DateFormatter(format);
	    dataField = new JFormattedTextField(df);
	    dataField.setColumns(8);

	    // Pulsante "Genera ID"
	    generaIdBtn = new JButton("Genera ID");
	    buttonSizeSetter.uniformButtonSize(generaIdBtn);

	    // Pannello superiore: campi affiancati
	    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    inputPanel.add(new JLabel("Tipo corso:"));
	    inputPanel.add(tipoCorsoPerId);
	    inputPanel.add(new JLabel("ID Sessione:"));
	    inputPanel.add(idSessioneField);
	    inputPanel.add(generaIdBtn);
	    inputPanel.add(new JLabel("Staff ID:"));
	    inputPanel.add(staffIdField);
	    inputPanel.add(new JLabel("Data:"));
	    inputPanel.add(dataField);
	    inputPanel.add(new JLabel("Fascia oraria:"));
	    inputPanel.add(fasciaOrariaField);

	    // Pannello inferiore: bottoni affiancati
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    creaBtn = new JButton("Crea");
	    modificaBtn = new JButton("Modifica");
	    cancellaBtn = new JButton("Cancella");
	    pulisciBtn = new JButton("Pulisci vecchie");

	    buttonSizeSetter.uniformButtonSize(creaBtn, modificaBtn, 
	    								   cancellaBtn, pulisciBtn);

	    buttonPanel.add(creaBtn);
	    buttonPanel.add(modificaBtn);
	    buttonPanel.add(cancellaBtn);
	    buttonPanel.add(pulisciBtn);

	    // Composizione finale
	    mainPanel.add(inputPanel);
	    mainPanel.add(buttonPanel);

	    return mainPanel;
	    
	}

	
	//----------------------------------------------------------------

	
	public JTable getSessioniTable() {return sessioniTable;}
	
	//----------------------------------------------------------------
	
	public String getTipoCorsoFiltro() {
		
	    return tipoCorsoFiltro.getText().trim();
	    
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
	
	public String getTipoCorsoPerIdField() {
		
		return tipoCorsoPerId.getText().trim();
		
	}
	
	//----------------------------------------------------------------
	
	public JTextField getIdSessioneField() {
		
		return idSessioneField;
		
	}
	
	public String getIdSessioneFieldContent() {
		
		return idSessioneField.getText().trim();
		
	}
	
	//----------------------------------------------------------------
	
	public void setStaffIdOptions(List<String> ids) {
		
	    staffIdField.removeAllItems();
	    
	    for (String id : ids) {
	    	
	        staffIdField.addItem(id);
	        
	    }
	    
	}
	
	public String getStaffIdField() {
		
		return (String) staffIdField.getSelectedItem();
		
	}
	
	//----------------------------------------------------------------
	
	public String getFasciaOrariaField() {
		
		return fasciaOrariaField.getText().trim();
		
	}
	
	//----------------------------------------------------------------
	
	public String getDataField() {
	
		return dataField.getText().trim();
		
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
	
	public void addGeneraIdBtnListener(ActionListener listener) {
		
		generaIdBtn.addActionListener(listener);
	    
	}
	
	//----------------------------------------------------------------
	
	public void addTabellaSessioniSelectionListener(ListSelectionListener listener) {
		
	    sessioniTable.getSelectionModel().addListSelectionListener(listener);
	    
	}
	
	//----------------------------------------------------------------
	
	public void setSelectedStaffId(String staffId) {
		
	    staffIdField.setSelectedItem(staffId);
	    
	}
	
	//----------------------------------------------------------------
	
	public void setDataField(String dataStr) {
		
	    dataField.setText(dataStr);
	    
	}
	
	//----------------------------------------------------------------
	
	public void setFasciaOrariaField(String fascia) {
		
	    fasciaOrariaField.setText(fascia);
	    
	}
	
	//----------------------------------------------------------------
	
	public void pulisciCampi() {
		
	    idSessioneField.setText("");
	    staffIdField.setSelectedIndex(-1);  // Deseleziona la riga selezionato
	    dataField.setText("");
	    fasciaOrariaField.setText("");

	}
	
	//----------------------------------------------------------------
	
}
