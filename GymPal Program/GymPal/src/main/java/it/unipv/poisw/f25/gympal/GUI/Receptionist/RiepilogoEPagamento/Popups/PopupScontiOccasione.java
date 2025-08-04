package it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.Popups;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class PopupScontiOccasione extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	private final Consumer<List<Sconto>> onApplicaCallback;
	private final Map<JCheckBox, Sconto> checkboxMap = new HashMap<>();
	private final List<Sconto> scontiDisponibili;
	private final JPanel checkBoxPanel;
	
    //----------------------------------------------------------------
	
	public PopupScontiOccasione (List<Sconto> sconti, 
							     Consumer<List<Sconto>> onApplicaCallback) {
				
		super("Sconti Disponibili");
		scontiDisponibili = sconti;
		this.onApplicaCallback = onApplicaCallback;
		/*Impostazioni Frame##########################################*/
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		/*checkBoxPanel###############################################*/
		
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
		
		aggiungiCheckBox();
		
		JButton applica = new JButton("Applica");
		
		applica.addActionListener(e -> {
			
		    List<Sconto> selezionati = checkboxMap.entrySet().stream()
		    						   .filter(entry -> entry.getKey().isSelected())
		    						   .map(Map.Entry::getValue)
		    						   .collect(Collectors.toList());

		    if (this.onApplicaCallback != null) {
		    	
		    	this.onApplicaCallback.accept(selezionati);
		    	
		    }

		    this.dispose();
		    
		});

		JButton chiudi = new JButton("Chiudi");
		chiudi.addActionListener(e -> this.dispose());

		JPanel pulsanti = new JPanel();
		pulsanti.add(applica);
		pulsanti.add(chiudi);

		this.add(pulsanti, BorderLayout.SOUTH);
		
		/*############################################################*/
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
				
	}
	
    //----------------------------------------------------------------
	
	private void aggiungiCheckBox() {
		
	    LocalDate oggi = LocalDate.now();

	    /*NOTE:
	     * scontiDisponibili.stream() --> avvia uno stream sulla lista di sconti 
	     * 								  disponibili. Ovvero, la lista è trasforma-
	     * 								  ta in una sequenza di elementi processabili
	     * 								  in modo -dichiarativo- (come a scorrere
	     * 								  i vari elementi, uno dietro l'altro, ma
	     * 								  senza usare un ciclo esplicito; for, while
	     * 								  ecc.).
	     * 								  Questo consente di filtrare, trasformare 
	     * 								  e/o raggruppare gli elementi.
	     * 								  ATTENZIONE: la lista originale non cambia
	     * 								  (gli elementi son sempre quelli):
	     * 								  essa è trasformata, filtrata o aggregata in
	     * 								  nuovi modi.
	     * 
	     * .collect(...) ---> Raccoglie il risultato dello stream in una struttura
	     * 					  dati (una mappa, in questo caso)
	     * 
	     * Collectors.groupingBy(...) ---> Specifica il criterio di raggruppamento
	     * 
	     * s -> s.getDataSconto().getMonthValue() ---> Estrae il valore numerico del
	     * 											   mese da ogni sconto*/
	    
	    /*Questa istruzione assegna ad una chiave numerica tutti gli sconti (rag-
	     *gruppati in una lista) il cui mese corrisponde a detta chiave*/
	    Map<Integer, List<Sconto>> scontiPerMese = scontiDisponibili.stream()
	    										   .collect(Collectors.groupingBy
	    										   (s -> s.getDataSconto().
	    												   getMonthValue()));

	    /*NOTE:
	     * scontiPerMese.keySet() ---> prende l'insieme delle chiavi della mappa	
	     * 							   (altresì i numeri dei mesi)
	     * 
	     * .stream() ---> crea uno stream a partire dall'insieme di chiavi
	     * 
	     * .sorted() ---> pone i numeri dei mesi in ordine crescente
	     * 
	     * .collect(Collectors.toList()) ---> raccoglie i numeri ordinati in una
	     * 									  nuova lista*/
	    List<Integer> mesiOrdinati = scontiPerMese.keySet().stream()
	    										  .sorted()
	    										  .collect(Collectors.toList());

	    for (Integer mese : mesiOrdinati) {
	    	
	    	/*Selezione degli sconti appartenenti ad un determinato mese:
	    	 *"Dammi tutti gli sconti il cui mese corrisponde al numero 'mese'"*/
	        List<Sconto> scontiDelMese = scontiPerMese.get(mese);

	        /*############################################################*/
	        
	        // Bottone toggle intestazione mese
	        String nomeMese = Month.of(mese).getDisplayName(TextStyle.FULL, 
	        				  							    Locale.ITALIAN);
	        
	        JToggleButton toggleButton = new JToggleButton("▼ " + nomeMese.
	        													  toUpperCase());
	        toggleButton.setFont(toggleButton.getFont().deriveFont(Font.BOLD));
	        toggleButton.setFocusPainted(false);
	        toggleButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	        
	        /*############################################################*/
	        
	        // Pannello contenente i checkbox del mese
	        JPanel contenitoreCheck = new JPanel();
	        contenitoreCheck.setLayout(new BoxLayout(contenitoreCheck, 
	        							   BoxLayout.Y_AXIS));
	        contenitoreCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
	        contenitoreCheck.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

	        for (Sconto s : scontiDelMese) {
	        	
	            JCheckBox cb = new JCheckBox(s.getNomeSconto() + " - gg/MM: " +
							                 s.getDataSconto().getDayOfMonth() + " / " +
							                 s.getDataSconto().getMonthValue() + " - " +
							                 s.getAmountSconto() + "%");

	            boolean validoOggi = (oggi.getMonthValue() == s.getDataSconto().getMonthValue())
	                              && (oggi.getDayOfMonth() == s.getDataSconto().getDayOfMonth());

	            cb.setEnabled(true); //Impostare a 'validoOggi', anziché 'true'
	            cb.setOpaque(true);
	            cb.setBackground(validoOggi ? Color.GREEN.brighter() : 
	            							  Color.LIGHT_GRAY);
	            
	            cb.setForeground(validoOggi ? Color.BLACK : Color.DARK_GRAY);
	            cb.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

	            checkboxMap.put(cb, s);
	            
	            contenitoreCheck.add(cb);
	            contenitoreCheck.add(Box.createVerticalStrut(5));
	        }

	        /*############################################################*/
	        
	        // Comportamento toggle: mostra/nascondi il pannello checkbox
	        toggleButton.addActionListener(e -> {
	            boolean visibile = contenitoreCheck.isVisible();
	            contenitoreCheck.setVisible(!visibile);
	            toggleButton.setText((visibile ? "► " : "▼ ") + nomeMese.toUpperCase());
	            checkBoxPanel.revalidate();
	            checkBoxPanel.repaint();
	        });
	        
	        /*############################################################*/

	        // Stato iniziale: NON-espanso
	        contenitoreCheck.setVisible(false);
	        toggleButton.setText("► " + nomeMese.toUpperCase());

	        checkBoxPanel.add(toggleButton);
	        checkBoxPanel.add(contenitoreCheck);
	        checkBoxPanel.add(Box.createVerticalStrut(15));
	        
	    }
	    
	}
	
	//----------------------------------------------------------------
	
}
