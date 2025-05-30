package it.unipv.poisw.f25.gympal.ReceptionistController;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.factories.StrategieCalcoloPrezzoFactory;

public class RiepilogoEPagamentoController {
	
	private RiepilogoEPagamentoView riepilogoEPagamento;
	
	private Runnable onIndietro;
	private Runnable onConferma;
	
	//----------------------------------------------------------------
	
	public RiepilogoEPagamentoController (RiepilogoEPagamentoView view, 
										  AbbonamentoDTO abbonamentoDTO, 
										  Runnable onIndietro,
										  Runnable onConferma) {
		
		
	    System.out.println("DEBUG nel controller riepilogo - sezioni:");
	    if (abbonamentoDTO.getSezioniAbbonamento() != null) {
	        for (String s : abbonamentoDTO.getSezioniAbbonamento()) {
	            System.out.println(" - " + s);
	        }
	    }
	    System.out.println("DEBUG nel controller riepilogo - corsi:");
	    if (abbonamentoDTO.getCorsiSelezionati() != null) {
	        for (String c : abbonamentoDTO.getCorsiSelezionati()) {
	            System.out.println(" - " + c);
	        }
	    }
		
		
		riepilogoEPagamento = view;
		
		this.onIndietro = onIndietro;
		this.onConferma = onConferma;
		
		/*Inizializzazione della view "RiepilogoEPagamento" con i dati acquisiti durante la
		 *procedura di iscrizione*/
		riepilogoEPagamento.setDatiAbbonamento(abbonamentoDTO);
		
        IStrategieCalcoloPrezzo strategia = StrategieCalcoloPrezzoFactory.getStrategy(abbonamentoDTO);
        double totale = strategia.calcolaPrezzo(abbonamentoDTO);
        
        riepilogoEPagamento.setPrezzoTotale(totale);
		
		impostaEventoIndietro();
		impostaEventoConferma();
		
		
	}
	
	//----------------------------------------------------------------	

	private void impostaEventoIndietro() {
		
		riepilogoEPagamento.getIndietroButton().addActionListener(e -> {
	        
	    	onIndietro.run();
	        
	    });
	    
	}
    
	//----------------------------------------------------------------
	
	private void impostaEventoConferma() {
        riepilogoEPagamento.getConfermaButton().addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(
            		
                null, // Nessun componente genitore
                
                "Vuoi davvero confermare?", // Messaggio visualizzato
                
                "Conferma iscrizione", //Titolo pannello pop-up
                
                JOptionPane.YES_NO_OPTION //Opzioni presentate all'utente - SI : NO
            );

            if (result == JOptionPane.YES_OPTION) {
                try {
                	
                    //AbbonamentoDAO dao = new AbbonamentoDAO(); //Creazione nuovo DAO
                	
                	/*Il metodo "inserisciAbbonamento" deve fare tre cose:
                	 * 
                	 *1) Estrae i dati dal DAO
                	 *
                	 *2) Costruisce una query SQL
                	 *
                	 *3) Esegue la query*/
                	
                    //dao.inserisciAbbonamento(abbonamentoDTO); 
                	

                    onConferma.run(); // Ritorno alla schermata iniziale e reset per un nuovo ciclo

                } catch (Exception ex) {
                	
                    JOptionPane.showMessageDialog(
                    		
                        null,
                        
                        "Errore durante la conferma:\n" + ex.getMessage(),
                        
                        "Errore",
                        
                        JOptionPane.ERROR_MESSAGE
                        
                    );
                }
            }

        });
    }
	
	//----------------------------------------------------------------
	
}
