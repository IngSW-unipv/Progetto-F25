package it.unipv.poisw.f25.gympal.ReceptionistController;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.SimulazioneOperazione;
import it.unipv.poisw.f25.gympal.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.factories.StrategieCalcoloPrezzoFactory;

public class RiepilogoEPagamentoController {
	
	private RiepilogoEPagamentoView riepilogoEPagamento;
	
	private AbbonamentoDTO abbonamentoDTO;
	
	private Runnable onIndietro;
	private Runnable onConferma;
	private Runnable onAnnulla;
	
	//----------------------------------------------------------------
	
	public RiepilogoEPagamentoController (RiepilogoEPagamentoView view, 
										  AbbonamentoDTO abbonamentoDTO, 
										  Runnable onIndietroCallback,
										  Runnable onConfermaCallback) {
		
		//DEBUG
		/*
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
	    }*/
		
		
		riepilogoEPagamento = view;
		
		this.abbonamentoDTO = abbonamentoDTO;
		onIndietro = onIndietroCallback;
		onConferma = onConfermaCallback;
		/*Benché "onAnnulla" utilizzi la stessa callback di "onConferma" (per non duplicare
		 *codice - visto che questa callback re-inizializza il ciclo) il listener associato 
		 *al bottone "Annulla" non fa altro che eseguire la callback (inizializza un nuovo
		 *ciclo di registrazione), mentre il listener *associato a "Conferma" esegue operazioni
		 *collegate al trasferimento dati verso DB, prima di inizializzare un nuovo ciclo di
		 *registrazione.*/
		onAnnulla = onConfermaCallback;
		
		/*Inizializzazione della view "RiepilogoEPagamento" con i dati acquisiti durante la
		 *procedura di iscrizione*/
		riepilogoEPagamento.setDatiAbbonamento(abbonamentoDTO);
		aggiornaPrezzo();
		

		
		impostaEventoIndietro();
		impostaEventoAnulla();
		impostaEventoConferma();
		impostaEventoScontoSuBaseMesi();
		impostaEventoMensilita();
		impostaEventiCheckBox();
		impostaEventiJRadioButton();
		impostaEventoAvvioPagamento();
				
		
	}
	
	//----------------------------------------------------------------	

	private void impostaEventoIndietro() {
		
		riepilogoEPagamento.getIndietroButton().addActionListener(e -> {
	        
	    	onIndietro.run();
	        
	    });
	    
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
		riepilogoEPagamento.getAnnullaButton().addActionListener(e -> {
			
			onAnnulla.run();
			
		});
		
	}
    
	//----------------------------------------------------------------
	
	private void impostaEventoConferma() {
		
        riepilogoEPagamento.getConfermaButton().addActionListener(e -> {

        	if(riepilogoEPagamento.getContanti().isSelected() ||
             	   riepilogoEPagamento.getCarta().isSelected() ||
             	   riepilogoEPagamento.getNoPagamento().isSelected()) {
        	
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
            
            } else {
            	
                JOptionPane.showMessageDialog(
                		
                        null,
                        
                        "Seleziona una modalità di pagamento prima di confermare",
                        
                        "ATTENZIONE!",
                        
                        JOptionPane.INFORMATION_MESSAGE
                        
                    );            	
            	
            }

        });
        
    }
	
	//----------------------------------------------------------------
	
	private void impostaEventoScontoSuBaseMesi () {
		
		riepilogoEPagamento.getScontoSuBaseMesi().addActionListener(e -> {
			
			//Mostra il menu popup
			riepilogoEPagamento.getPopupMenu().show(riepilogoEPagamento.getScontoSuBaseMesi(), 
					
								     0, riepilogoEPagamento.getScontoSuBaseMesi().getHeight());
			
		});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoMensilita () {
		
		riepilogoEPagamento.getTrimestrale().addActionListener(e -> {
			
			if(riepilogoEPagamento.getTrimestrale().isSelected()) {
				
				riepilogoEPagamento.getScontoSuBaseMesi().setText(riepilogoEPagamento.
														  getTrimestrale().getText());
				
				aggiornaPrezzo();

			}
			
		});
		
		riepilogoEPagamento.getSemestrale().addActionListener(e -> {
			
			if(riepilogoEPagamento.getSemestrale().isSelected()) {
				
				riepilogoEPagamento.getScontoSuBaseMesi().setText(riepilogoEPagamento.
														  getSemestrale().getText());
				
				aggiornaPrezzo();
			}
			
		});
		
		riepilogoEPagamento.getAnnuale().addActionListener(e -> {
			
			if(riepilogoEPagamento.getAnnuale().isSelected()) {
				
				riepilogoEPagamento.getScontoSuBaseMesi().setText(riepilogoEPagamento.
														  getAnnuale().getText());
				
				aggiornaPrezzo();
			}
			
		});
		
		
		riepilogoEPagamento.getNessuno().addActionListener(e -> {
			
			if(riepilogoEPagamento.getNessuno().isSelected()) {
				
				riepilogoEPagamento.getScontoSuBaseMesi().setText(riepilogoEPagamento.
														  getNessuno().getText());
				
				aggiornaPrezzo();
			}
			
		});
		
	}
	
	//----------------------------------------------------------------	
	
	private void impostaEventiCheckBox() {
		
	    riepilogoEPagamento.getScontoEtaCheckBox().addActionListener(e -> aggiornaPrezzo());
	    
	    riepilogoEPagamento.getScontoOccasioniCheckBox().addActionListener(e -> aggiornaPrezzo());
	    
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiJRadioButton() {
		
	    riepilogoEPagamento.getContanti().addActionListener(e -> abbonamentoDTO.
	    														 setStatoPagamento(true));
	    
	    riepilogoEPagamento.getCarta().addActionListener(e -> abbonamentoDTO.
				 											  setStatoPagamento(true));
	    
	    riepilogoEPagamento.getNoPagamento().addActionListener(e -> abbonamentoDTO.
				  												    setStatoPagamento(false));
	    
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAvvioPagamento() {
		
		riepilogoEPagamento.getAvvioPagamento().addActionListener(e -> {
			
			/*Questa istruzione rintraccia il componente grafico principale, e lo assegna al
			 *riferimento "framePadre"*/
			JFrame framePadre = (JFrame) javax.swing.
								SwingUtilities.getWindowAncestor((java.awt.Component) e.
								getSource());
			
			/*Il popup con la barra di caricamento è creato ed ancorato al frame ricavato 
			 *dall'istruzione precedente*/
	        SimulazioneOperazione simulazione = new SimulazioneOperazione(framePadre);
	        simulazione.start();
			
		});
		
	}
	
	//----------------------------------------------------------------
	
	private void aggiornaPrezzo() {
		
	    boolean scontoEta = riepilogoEPagamento.getScontoEtaCheckBox().isSelected();
	    
	    boolean scontoOccasioni = riepilogoEPagamento.getScontoOccasioniCheckBox().isSelected();

	    String durata = null;
	    
	    if (riepilogoEPagamento.getTrimestrale().isSelected()) {
	    	
	        durata = "trimestrale";
	        
	    } else if (riepilogoEPagamento.getSemestrale().isSelected()) {
	    	
	        durata = "semestrale";
	        
	    } else if (riepilogoEPagamento.getAnnuale().isSelected()) {
	    	
	        durata = "annuale";
	    }

	    IStrategieCalcoloPrezzo strategia = StrategieCalcoloPrezzoFactory.getStrategy(
	        abbonamentoDTO,
	        scontoEta,
	        scontoOccasioni,
	        durata
	    );

	    double totale = strategia.calcolaPrezzo(abbonamentoDTO);
	    
	    riepilogoEPagamento.setPrezzoTotale(totale);
	    
	}

	//----------------------------------------------------------------
	
}
