package it.unipv.poisw.f25.gympal.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;

public class RiepilogoEPagamentoView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JSplitPane mainSplitPanel;
	private JPanel mainUpperPanel;
	private JPanel mainBottomPanel; //Qui finisce il riepilogo
	
	private JSplitPane secondSplitPanel; //Il pannello superiore coincide con "mainSplitPanel"
	private JPanel indietroConfermaPanel; //Qui finisce il pannello con il bottone "Intrietro"
	
	private JButton indietro;
	private JButton conferma;
	
	private JLabel prezzoTotaleLabel;
	
    //----------------------------------------------------------------
	
	public RiepilogoEPagamentoView() {
		
	    setLayout(new BorderLayout());
	    
	    /*############################################################*/
	    
	    mainUpperPanel = new JPanel(new GridLayout(0, 2, 10, 10));
	    
	    mainUpperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    
	    mainSplitPanel.setTopComponent(mainUpperPanel);
	    
	    /*############################################################*/
	    
        mainBottomPanel = new JPanel();
        
        mainBottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        prezzoTotaleLabel = new JLabel("Totale: € 0.00");
        
        mainBottomPanel.add(prezzoTotaleLabel);

        mainSplitPanel.setBottomComponent(mainBottomPanel);
        
	    /*############################################################*/
	    
	    indietroConfermaPanel = new JPanel();
	    
	    indietroConfermaPanel.setLayout(new BoxLayout(indietroConfermaPanel, BoxLayout.X_AXIS));
	    
	    indietro = new JButton ("Indietro");
		indietro.setMaximumSize(new Dimension(80, 40));
		
		conferma = new JButton ("Conferma");
		conferma.setMaximumSize(new Dimension(80, 40));
		
		indietroConfermaPanel.add(Box.createHorizontalGlue());
		indietroConfermaPanel.add(indietro);
		indietroConfermaPanel.add(Box.createHorizontalStrut(200));
		indietroConfermaPanel.add(conferma);
		indietroConfermaPanel.add(Box.createHorizontalGlue());
	    
	    /*############################################################*/
	    
	    secondSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    
	    secondSplitPanel.setTopComponent(mainSplitPanel);
	    
	    secondSplitPanel.setBottomComponent(indietroConfermaPanel);
	    
		SwingUtilities.invokeLater(() -> {
			
			secondSplitPanel.setDividerLocation(0.8); 
		    
		});
		
		secondSplitPanel.setEnabled(false);
	    
	    /*############################################################*/

	    add(secondSplitPanel);
	    
	}

	
    //----------------------------------------------------------------
	
	private void labeledEntry (JPanel panel, String label, String campoDTO ) { 
		
		panel.add(new JLabel (label));
		panel.add(new JLabel (campoDTO));
		
	}
	
	//----------------------------------------------------------------
	
		private JScrollPane listaAScorrimento(List<String> selezione) {
			
			/*La parte "selezione.toArray(new String[0])" converte la "List<String> selezione"
			 *in un array di tipo "String[]", il formato richiesto dal costruttore di "JList"*/
			
			/*"new String[0]" è un'array di appoggio che consente al metodo "toArray" di sapere
			 *il tipo. Alla fine è restituito un array avente tanti elementi quanti sono gli
			 *elementi della lista convertita.*/
			
			JList<String> lista = new JList<>(selezione != null ? selezione.toArray(new String[0]) : new String[0]);
			lista.setEnabled(false); //Listan NON modificabile
			
			return new JScrollPane(lista);
			
		}
	
	//----------------------------------------------------------------
	
	private void labeledList (JPanel panel, String label, List<String> campoDTO ) {
		
		panel.add(new JLabel (label));
		panel.add(listaAScorrimento(campoDTO));
		
	}
	
	//----------------------------------------------------------------
	
	public void setDatiAbbonamento(AbbonamentoDTO abbDTO) {

	    mainUpperPanel.removeAll(); // Nel caso in cui vi siano dati pre-esistenti, è fatta tabula rasa

	    labeledEntry(mainUpperPanel, "Nome: ", abbDTO.getNome());
	    labeledEntry(mainUpperPanel, "Cognome: ", abbDTO.getCognome());
	    labeledEntry(mainUpperPanel, "Codice Fiscale: ", abbDTO.getCodiceFiscale());
	    labeledEntry(mainUpperPanel, "Sesso: ", abbDTO.getSesso());

	    
	    /*Protezione contro eccezione di tipo "NullPointerException"*/	    
	    if (abbDTO.getDataNascita() != null) {
	    	
	        Date dataNascita = Date.from(abbDTO.getDataNascita().atStartOfDay(ZoneId.systemDefault()).toInstant());
	        String dataNascitaCliente = new java.text.SimpleDateFormat("dd/MM/yyyy").format(dataNascita);
	        labeledEntry(mainUpperPanel, "Data di nascita: ", dataNascitaCliente);
	        
	    } else {
	    	
	        labeledEntry(mainUpperPanel, "Data di nascita: ", "Dato mancante");
	        
	    }
	    
		/*La porzione "abbDTO.getCertificatoIdoneita() ? "Si" : "No" è interpretata in questo modo:
		 *se "getCertificatoIdoneita()" restituisce "true", allora è visualizzato "Si", altrimenti
		 *è visualizzato "No"*/

	    labeledEntry(mainUpperPanel, "Certificato di idoneità: ", abbDTO.getCertificatoIdoneita() ? "Si" : "No");
	    labeledEntry(mainUpperPanel, "Permesso genitori: ", abbDTO.getPermessoGenitori() ? "Si" : "No");

	    labeledList(mainUpperPanel, "Componenti abbonamento selezionate: ", abbDTO.getSezioniAbbonamento());
	    labeledList(mainUpperPanel, "Corsi selezionati: ", abbDTO.getCorsiSelezionati());

	    revalidate(); 
	    repaint();
	    
	}

	//----------------------------------------------------------------
	
	public JButton getIndietroButton() {
		return indietro;
	}
	
	//----------------------------------------------------------------
	
	public JButton getConfermaButton() {
		return conferma;
	}
	
	//----------------------------------------------------------------

    public void setPrezzoTotale(double totale) {
    	
        // Formattazione in euro con 2 decimali
    	
        DecimalFormat df = new DecimalFormat("€ 0.00");
        
        prezzoTotaleLabel.setText("Totale: " + df.format(totale));
        
    }
	
}
