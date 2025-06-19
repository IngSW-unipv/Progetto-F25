package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulazioneOperazione;

public class RiepilogoEPagamentoController {
    
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    private IRegistrationCoordinator coordinator;
    
    private IRiepilogoDTO abbonamentoDTO;
    
    private Runnable onIndietro;
    private Runnable onConferma;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public RiepilogoEPagamentoController (IRiepilogoEPagamentoView view, 
    									  IRiepilogoDTO abbonamentoDTO, 
                                          Runnable onIndietroCallback,
                                          Runnable onConfermaCallback,
                                          IRegistrationCoordinator coordinator) {
        
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
        this.coordinator=coordinator;
        this.abbonamentoDTO = abbonamentoDTO;
        onIndietro = onIndietroCallback;
        onConferma = onConfermaCallback;
        /*Benché "onAnnulla" utilizzi la stessa callback di "onConferma" (per non duplicare
         *codice - visto che questa callback re-inizializza il ciclo) il listener associato 
         *al bottone "Annulla" non fa altro che eseguire la callback (inizializza un nuovo
         *ciclo di registrazione), mentre il listener associato a "Conferma" lancia operazioni
         *collegate al trasferimento dati verso DB, prima di inizializzare un nuovo ciclo di
         *registrazione.*/
        onAnnulla = onConfermaCallback;
        
        /*Inizializzazione della view "RiepilogoEPagamento" con i dati acquisiti durante la
         *procedura di iscrizione*/
        riepilogoEPagamento.setDatiAbbonamento(abbonamentoDTO);
        
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

    	MetodoPagamento metodoPagamento = riepilogoEPagamento.getMetodoPagamentoSelezionato();

    	if(metodoPagamento != MetodoPagamento.NESSUNO) {
    		
    	    int result = JOptionPane.showConfirmDialog(
    	            null, // Nessun componente genitore
    	            "Vuoi davvero confermare?", // Messaggio visualizzato
    	            "Conferma iscrizione", // Titolo pannello pop-up
    	            JOptionPane.YES_NO_OPTION); // Opzioni presentate all'utente - SI : NO

    	    if(result == JOptionPane.YES_OPTION) {
    	    	
    	        try {
    	        	
    	        	/*Bisogna dire al coordinatore che la fase di acquisizione
    	             *dati è terminata, pertanto bisogna inviare le informazioni
    	             *raccolte al service-layer*/
    	        	
    	        	// Ritorno alla schermata iniziale e reset per un nuovo ciclo    	        	
    	            onConferma.run();
    	            
    	        } catch(Exception ex) {
    	        	
    	            JOptionPane.showMessageDialog(null,
    	                "Errore durante la conferma:\n" + ex.getMessage(),
    	                "Errore",
    	                JOptionPane.ERROR_MESSAGE);
    	            
    	        }
    	    }
    	    
    	} else {
    		
    	    JOptionPane.showMessageDialog(null,
    	        "Seleziona una modalità di pagamento prima di confermare",
    	        "ATTENZIONE!",
    	        JOptionPane.INFORMATION_MESSAGE);
    	    
    	}

  
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
        	
        	MetodoPagamento metodoPagamento = MetodoPagamento.NESSUNO;
        	
            if(riepilogoEPagamento.isContantiSelected()) {
            	
            	metodoPagamento = MetodoPagamento.CONTANTI;
                
            } else if (riepilogoEPagamento.isCartaSelected()) {
            	
            	metodoPagamento = MetodoPagamento.CARTA;
                
            }
            
            coordinator.acquisisciMetodoPagamento(metodoPagamento);
            
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
        									getDiscountedPrice(abbonamentoDTO));
        
    }

    //----------------------------------------------------------------
    
}
