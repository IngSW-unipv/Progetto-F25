package it.unipv.poisw.f25.gympal.GUI.Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

public class SimulazioneOperazione extends JDialog {
	
    private static final long serialVersionUID = 1L;
    private final JProgressBar barraCaricamento;

    //----------------------------------------------------------------
    // Costruttore per Window generico
    public SimulazioneOperazione(Window owner, String messaggio) {
    	
        super(owner, "Caricamento in corso...", ModalityType.APPLICATION_MODAL);

        UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);

        barraCaricamento = new JProgressBar(0, 100);
        barraCaricamento.setValue(0);
        barraCaricamento.setStringPainted(true);
        barraCaricamento.setForeground(Color.decode("#b2fab4"));

        JPanel pannello = new JPanel(new BorderLayout(10, 10));
        pannello.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pannello.add(barraCaricamento, BorderLayout.CENTER);

        setContentPane(pannello);
        setSize(400, 150);
        setResizable(false);
        setLocationRelativeTo(owner);
        
    }
    
    //----------------------------------------------------------------

    public void aggiornaProgresso(int percentuale) {
    	
        barraCaricamento.setValue(percentuale);
        
    }
    
    //----------------------------------------------------------------

    // Metodo statico aggiornato, accetta Window e messaggio
    public static void mostraCaricamentoFinto(Window owner, String messaggio) {
        SimulazioneOperazione dialog = new SimulazioneOperazione(owner, messaggio);

        Timer timer = new Timer(20, null);
        
        timer.addActionListener(e -> {
        	
            int valore = dialog.barraCaricamento.getValue();
            
            if (valore >= 100) {
            	
                timer.stop();
                dialog.dispose();
                
            } else {
            	
                dialog.aggiornaProgresso(valore + 1);
                
            }
            
        });

        timer.start();
        dialog.setVisible(true);
    }
    
    //----------------------------------------------------------------
    
}

