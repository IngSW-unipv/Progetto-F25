package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class DettaglioCellaFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	//----------------------------------------------------------------
	
	public DettaglioCellaFrame() {
		
        setSize(500, 400);
        setLocationRelativeTo(null); // centro su schermo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
		
	}
	
	//----------------------------------------------------------------

	public DettaglioCellaFrame(IDatiCellaCalendarioDTO dati, 
							   LocalDate data, 
							   int ora, int minuti) {
		
        super("Dettagli cella - " + data + " " 
        	 + String.format("%02d:%02d", ora, minuti));

        setLayout(new BorderLayout());
        
        /*############################################################*/

        JPanel contenuto = new JPanel();
        contenuto.setLayout(new BoxLayout(contenuto, BoxLayout.Y_AXIS));
        contenuto.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        contenuto.add(new JLabel("Data: " + data + "  Ora: " 
        						+ String.format("%02d:%02d", ora, minuti)));

        contenuto.add(creaSezione("Corsi:", dati.getCorsi()));
        contenuto.add(creaSezione("Appuntamenti PT:", dati.getAppuntamentiPT()));
        contenuto.add(creaSezione("Eventi:", dati.getEventiGenerici()));
        
        /*############################################################*/

        JPanel footer = new JPanel();
        JButton chiudi = new JButton("Chiudi");
        chiudi.addActionListener(e -> dispose());

        JButton modifica = new JButton("Modifica...");
        
        modifica.addActionListener(e -> {
        	
            // futura logica: apri schermata di modifica
            JOptionPane.showMessageDialog(this, "Funzione non ancora implementata.");
            
        });

        footer.add(modifica);
        footer.add(chiudi);

        /*############################################################*/
        
        add(contenuto, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
	
	//----------------------------------------------------------------

    private JPanel creaSezione(String titolo, List<String> elementi) {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(titolo));

        if (elementi == null || elementi.isEmpty()) {
        	
            panel.add(new JLabel("Nessuno"));
            
        } else {
        	
            for (String el : elementi) {
            	
                panel.add(new JLabel("- " + el));
                
            }
            
        }

        return panel;
    }
    
	//----------------------------------------------------------------

}
