package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulazioneOperazione;

public class RiepilogoEPagamentoController {
    
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    private IRegistrationCoordinator coordinator;
    
    private IAbbonamentoDTO abbonamentoDTO;
    
    private Runnable onIndietro;
    private Runnable onConferma;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public RiepilogoEPagamentoController (IRiepilogoEPagamentoView view, 
    									  Runnable onIndietroCallback,
                                          Runnable onConfermaCallback,
                                          Runnable onAnnullaCallback,
                                          IRegistrationCoordinator coordinator) {
        
        
        
        riepilogoEPagamento = view;
        this.coordinator=coordinator;
        this.abbonamentoDTO = coordinator.getAbbonamentoDTO();
        
        
        onIndietro = onIndietroCallback;
        onConferma = onConfermaCallback;
        onAnnulla  = onAnnullaCallback;
        
        /*Inizializzazione della view "RiepilogoEPagamento" con i dati acquisiti durante la
         *procedura di iscrizione*/
        riepilogoEPagamento.setDatiAbbonamento(abbonamentoDTO);
        
        /*Tasto "Conferma" disabilitato fintanto che una opzione di pagamento non è
         *selezionata.*/
        riepilogoEPagamento.setConfermaEnabled(false);
  
        
        aggiornaPrezzo();

        
        impostaEventoAvvioPagamento();
        impostaEventoMetodoPagamento();
        impostaEventoPopupMenu();
        impostaEventiOpzioniSconto();
        impostaEventoIndietro();
        impostaEventoAnnulla();
        impostaEventoConferma();
                
        
    }
    
    //----------------------------------------------------------------    
   
    private void impostaEventoConferma() {
    	
        riepilogoEPagamento.addConfermaListener(e -> {

                 

        int result = JOptionPane.showConfirmDialog(
                        null, // Nessun componente genitore
                        "Vuoi davvero confermare?", // Messaggio visualizzato
                        "Conferma iscrizione", // Titolo pannello pop-up
                        JOptionPane.YES_NO_OPTION); // Opzioni presentate all'utente - SI : NO

        if (result == JOptionPane.YES_OPTION) {
        	
                try {
        	        	
        	        // Ritorno alla schermata iniziale e reset per un nuovo ciclo 
            		onConferma.run();
                        
                 } catch (Exception ex) {
            	 
                        JOptionPane.showMessageDialog(
                        	null,
                            "Errore durante la conferma:\n" + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                 }
                    
             }


        });
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAvvioPagamento() {
    	
        riepilogoEPagamento.addAvvioPagamentoListener(e -> {
            /*Questa istruzione rintraccia il componente grafico principale, e lo assegna al
             *riferimento "framePadre"*/
            JFrame framePadre = (JFrame) javax.swing.SwingUtilities.getWindowAncestor((java.awt.Component) e.getSource());
            
            /*Il popup con la barra di caricamento è creato ed ancorato al frame ricavato 
             *dall'istruzione precedente*/
            SimulazioneOperazione simulazione = new SimulazioneOperazione(framePadre);
            simulazione.start();
        });
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoMetodoPagamento() {
    	
        riepilogoEPagamento.addMetodoPagamentoListener(e -> {
        	
        	/*E' lecito che il controllore conosca le enum - in quanto parti del mdoello
        	 *di dominio, esse rappresentano un concetto stabile, e vengono usate per
        	 *popolare o leggere parti del DTO.
        	 *
        	 *In quanto tipo immutabile, la enum è priva di logica, quindi non si porta
        	 *dietro alcuna dipendenza pesante. 
        	 *
        	 *Questo controllore non contiene logica di business associata ai valori
        	 *della enum, si limita ad assegnarli, passarli, o leggerli per decidere se
        	 *ha senso proseguire con un'azione.*/
        	
        	MetodoPagamento metodoPagamento = riepilogoEPagamento.getMetodoPagamentoSelezionato();
            
            if (metodoPagamento != null) {
            	
                coordinator.acquisisciMetodoPagamento(metodoPagamento);
                
                riepilogoEPagamento.setConfermaEnabled(true);
                
            } else {
            	
                riepilogoEPagamento.setConfermaEnabled(false);
            }
            
        }); 	
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoPopupMenu() {
    	
    	riepilogoEPagamento.addScontoSuBaseMesiListener(e -> riepilogoEPagamento.
    														 showPopupMenu());
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventiOpzioniSconto() {
    	
        riepilogoEPagamento.addMensilitaListener(e -> {
            /* Aggiorna testo sconto ("Trimestrale", "Semestrale", ... ) in base alla
             * mensilità selezionata, ed innesca un corrispondente ricalcolo del prezzo*/
            riepilogoEPagamento.setScontoSuBaseMesiText(riepilogoEPagamento.getDurataSelezionata());
            aggiornaScontiEDurata();
            aggiornaPrezzo();
        });
        
        riepilogoEPagamento.addScontoEtaListener(e -> {aggiornaScontiEDurata(); 
        											   aggiornaPrezzo();});
        
        riepilogoEPagamento.addScontoOccasioniListener(e -> {aggiornaScontiEDurata(); 
        													 aggiornaPrezzo();});
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoIndietro() {
    	
    	riepilogoEPagamento.addIndietroListener(e -> onIndietro.run());
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAnnulla() {
    	
    	riepilogoEPagamento.addAnnullaListener(e -> onAnnulla.run());
    	
    }
    
    //----------------------------------------------------------------
    
    private void aggiornaScontiEDurata() {
    	
    	String durataSelezionata = riepilogoEPagamento.getDurataSelezionata();
    	DurataAbbonamento durataEnum;

    	switch (durataSelezionata) {
    	
	    	case "Mensile":
	    		durataEnum = DurataAbbonamento.MENSILE;
	    		break;
    	
    	    case "Trimestrale":
    	        durataEnum = DurataAbbonamento.TRIMESTRALE;
    	        break;
    	        
    	    case "Semestrale":
    	        durataEnum = DurataAbbonamento.SEMESTRALE;
    	        break;
    	        
    	    case "Annuale":
    	        durataEnum = DurataAbbonamento.ANNUALE;
    	        break;
    	        
    	    default:
    	        durataEnum = DurataAbbonamento.NESSUNO;
    	        break;
    	        
    	}
    	
    	coordinator.acquisisciScontiEDurata(riepilogoEPagamento.isScontoEtaSelected(), 
    										riepilogoEPagamento.isScontoOccasioniSelected(),
    										durataEnum);
    	
    }
    
    //----------------------------------------------------------------
    
    private void aggiornaPrezzo() {

        
        riepilogoEPagamento.setPrezzoTotale(coordinator.
        									getDiscountedPrice((ICalcolaPrezzo)abbonamentoDTO));
        
    }

    //----------------------------------------------------------------
    
}
