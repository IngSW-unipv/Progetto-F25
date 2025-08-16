package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

public class IscrizioneCorsiPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable sessioniTable;
	
	private JButton iscriviBtn;
	private JButton annullaIscrizioneBtn;
	
	private JTextField tipoCorsoField;
	private JFormattedTextField dataInizioField;
	private JFormattedTextField dataFineField;
	private JButton filtraBtn;
	
	//----------------------------------------------------------------
	
	public IscrizioneCorsiPanel() {
		
		setLayout(new BorderLayout());
		
        /*############################################################*/
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormatter df = new DateFormatter(format);
		
		df.setAllowsInvalid(true);
		
        /*############################################################*/

		dataInizioField = new JFormattedTextField(df);
		dataFineField = new JFormattedTextField(df);
		
		/* Evita che i due campi si ripristinino al valore precedente quando il
		 * focus sul campo è perso*/
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
		
        /*############################################################*/
		
		JPanel filtroPanel = new JPanel();

		tipoCorsoField = new JTextField(10);
		
		filtroPanel.add(new JLabel("Tipo corso:"));
		filtroPanel.add(tipoCorsoField);
		filtroPanel.add(new JLabel("Data inizio:"));
		filtroPanel.add(dataInizioField);
		filtroPanel.add(new JLabel("Data fine:"));
		filtroPanel.add(dataFineField);
		filtraBtn = new JButton("Filtra");
		filtroPanel.add(filtraBtn);

		add(filtroPanel, BorderLayout.NORTH);
		
        /*############################################################*/
		
		sessioniTable = new JTable();
		add(new JScrollPane(sessioniTable), BorderLayout.CENTER);
		
        /*############################################################*/
		
		JPanel btnPanel = new JPanel();
		iscriviBtn = new JButton("Iscrivi Cliente");
		annullaIscrizioneBtn = new JButton("Annulla Iscrizione");
		
		btnPanel.add(iscriviBtn);
		btnPanel.add(annullaIscrizioneBtn);
		
		add(btnPanel, BorderLayout.SOUTH);
		
	}
	
	//----------------------------------------------------------------
	
	public void addIscriviBtnListener(ActionListener listener) {
		
		iscriviBtn.addActionListener(listener);
		
	}
	
	//----------------------------------------------------------------
	
	public void addAnnullaIscrizioneBtnListener(ActionListener listener) {
		
		annullaIscrizioneBtn.addActionListener(listener);
		
	}
	
	//----------------------------------------------------------------
	
	public JTable getSessioniTable() {return sessioniTable;}
	
	//----------------------------------------------------------------
	
	/*public void setSessioniTableModel(TableModel model) {
		
	    sessioniTable.setModel(model);
	    
	}*/
	
	//----------------------------------------------------------------
	
	public String getTipoCorsoFiltro() {
		
	    return tipoCorsoField.getText().trim();
	    
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

	public void addFiltroListener(ActionListener listener) {
		
	    filtraBtn.addActionListener(listener);
	    
	}
	
	//----------------------------------------------------------------

}
