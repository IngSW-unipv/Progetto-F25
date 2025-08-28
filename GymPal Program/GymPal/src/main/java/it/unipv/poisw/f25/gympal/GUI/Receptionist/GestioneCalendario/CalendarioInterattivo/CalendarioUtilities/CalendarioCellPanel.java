package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class CalendarioCellPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private LocalDate data;
	private int ora;
	private int minuti;

	private JButton button;
	
	/*Azzurro*/
	private final Color COLORE_CORSI = new Color(100, 200, 255); 
	
	/*Arancione*/
	private final Color COLORE_APPUNTAMENTI = new Color(255, 170, 80); 
	
	/*Viola*/
	private final Color COLORE_EVENTI = new Color(200, 100, 255);
	
	/*Giallo*/
	private final Color COLORE_TURNI = new Color(255, 240, 80);
	
	private JPanel fascePanel;
	private JPanel fasciaCorsi;
	private JPanel fasciaAppuntamenti;
	private JPanel fasciaEventi;
	private JPanel fasciaTurni;
	
	//----------------------------------------------------------------
		
	public CalendarioCellPanel(LocalDate data, int ora, int minuti) {
		
	    this.data = data;
	    this.ora = ora;
	    this.minuti = minuti;
	    
	    setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    
	    /*############################################################*/

	    fascePanel = new JPanel();
	    fascePanel.setLayout(new GridBagLayout());
	    fascePanel.setOpaque(false); /*Sfondo visibile (dietro bottone)*/
	    
	    /*############################################################*/

	    GridBagConstraints gbcFasce = new GridBagConstraints();
	    gbcFasce.gridx = 0;
	    gbcFasce.gridy = 0;
	    gbcFasce.fill = GridBagConstraints.BOTH;
	    gbcFasce.weightx = 1.0;
	    gbcFasce.weighty = 1.0;
	    add(fascePanel, gbcFasce);

	    /*Creazione fasce colorate*/
	    fasciaCorsi = creaFascia(COLORE_CORSI);
	    fasciaAppuntamenti = creaFascia(COLORE_APPUNTAMENTI);
	    fasciaEventi = creaFascia(COLORE_EVENTI);
	    fasciaTurni = creaFascia(COLORE_TURNI);
	    
	    fasciaCorsi.setToolTipText("Corsi");
	    fasciaAppuntamenti.setToolTipText("Appuntamenti PT");
	    fasciaEventi.setToolTipText("Eventi generici");
	    fasciaTurni.setToolTipText("Turni staff");

	    /*############################################################*/
	    
	    /*Disposte in layout verticale*/
	    JPanel contenitoreFasce = new JPanel();
	    contenitoreFasce.setLayout(new GridBagLayout());
	    contenitoreFasce.setOpaque(false);
	    contenitoreFasce.setPreferredSize(new Dimension(60, 80));

	    /*Aggiunte al contenitore*/
	    contenitoreFasce.setLayout(new BoxLayout(contenitoreFasce, BoxLayout.Y_AXIS));
	    contenitoreFasce.add(fasciaCorsi);
	    contenitoreFasce.add(fasciaAppuntamenti);
	    contenitoreFasce.add(fasciaEventi);
	    contenitoreFasce.add(fasciaTurni);

	    fascePanel.add(contenitoreFasce);
	    
	    /*############################################################*/
	    
	    button = new JButton(String.format("%02d:%02d", ora, minuti));
	    button.setOpaque(true);
	    button.setFocusable(false);
	    button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
	    button.setPreferredSize(new Dimension(180, 80));

	    /*Bottone posto in primo piano, sopra le fasce*/
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.fill = GridBagConstraints.NONE;
	    add(button, gbc);

	    
	}

	
	//----------------------------------------------------------------
	
	private JPanel creaFascia(Color colore) {
		
	    JPanel fascia = new JPanel();
	    fascia.setBackground(colore);
	    fascia.setMaximumSize(new Dimension(60, 20)); 
	    fascia.setPreferredSize(new Dimension(60, 20));
	    fascia.setVisible(false); /*Fascia nascota, di default*/
	    return fascia;
	    
	}
	
	//----------------------------------------------------------------



	public LocalDate getData() {
		
	    return data;
	    
	}
	
	//----------------------------------------------------------------

	public int getOra() {
		
	    return ora;
	    
	}
	    
    //----------------------------------------------------------------

	public int getMinuti() {
		
	    return minuti;
	    
	}
	    
    //----------------------------------------------------------------
	    
	public JButton getButton() {
	    	
	    return button;
	        
	}
	    
    //----------------------------------------------------------------
	    
	public void addButtonListener(ActionListener listener) {
	    	
		button.addActionListener(listener);
	    	
	}
	    
    //----------------------------------------------------------------
	    
	public void aggiornaColoreSfondo(IDatiCellaCalendarioDTO dati) {
		
		boolean corsi = !dati.getCorsi().isEmpty();
	    boolean appuntamenti = !dati.getAppuntamentiPT().isEmpty();
	    boolean eventi = !dati.getEventiGenerici().isEmpty();
	    boolean turni = !dati.getTurni().isEmpty();

	    boolean haContenuti = corsi || appuntamenti || eventi || turni;

	    /* Sfondo generale: verde = presenza dati | rosso = assenza dati*/
	    setBackground(haContenuti ? new Color(200, 255, 200) : 
	    							new Color(255, 200, 200));

	    // Attivazione fasce
	    fasciaCorsi.setVisible(corsi);
	    fasciaAppuntamenti.setVisible(appuntamenti);
	    fasciaEventi.setVisible(eventi);
	    fasciaTurni.setVisible(turni);

	    revalidate();
	    repaint();
	        
	 }
	    
    //----------------------------------------------------------------
	    
}
