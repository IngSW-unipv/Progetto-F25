package it.unipv.poisw.f25.gympal.GUI.Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

public class SimulazioneOperazione extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private JProgressBar barraCaricamento;
	private Timer timer;
	
	private int avanzamento = 0;
	
	public SimulazioneOperazione (JFrame framePadre) {
		
		/*E' richiamato il costruttore di "JDialog": 
		 * 
		 *"framePadre" indica il frame di riferimento rispetto a cui è visualizzato il JDialog
		 *
		 *"Operzione in corso..." è il titolo del JDialog
		 *
		 *"true" dichiara il JDialog come "modale", altresì: fintanto che il JDialog rimane
		 *aperto NON è possibile interagire con altre finestre*/
		super(framePadre, "Operazione in corso...", true);
		
		/*### - Configurazione barra di caricamento - ################*/
		
		/*Queste due istruzioni impostano il colore della percentuale sovrapposta alla barra
		 *di caricamento*/
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		
		barraCaricamento = new JProgressBar(0, 100);
		barraCaricamento.setValue(0);
		
		//L'istruzione sottostante fa si che sia mostrata la percentuale di caricamento raggiunta
		barraCaricamento.setStringPainted(true);
		//L'istruzione sottostante fa si che il colore della barra sia verde chiaro
		barraCaricamento.setForeground(Color.decode("#b2fab4"));
		
				
	    /*############################################################*/
		
		JPanel pannello = new JPanel(new BorderLayout(10, 10));
		
		pannello.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		pannello.add(barraCaricamento, BorderLayout.CENTER);
		
		setContentPane(pannello);
		setSize(400, 150);
		setResizable(false);
		
		//L'istruzione sottostante centra il popup rispetto al frame di riferimento
		setLocationRelativeTo(framePadre);
		
	    /*############################################################*/
		
		//E' predisposto un timer per regolare l'avanzamento della barra di caricamento
		
		timer = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				avanzamento++;
				barraCaricamento.setValue(avanzamento);
				
				if(avanzamento >= 100) {timer.stop(); /*Rimozione JDialog*/ dispose();}
				
			}
			
		});
		
	}
	
	//----------------------------------------------------------------
	
	public void start() {
		
		avanzamento = 0;
		
		barraCaricamento.setValue(0);
		
		timer.start();
		
		setVisible(true);
		
	}
	
	//----------------------------------------------------------------

}
