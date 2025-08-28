package it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento;

import java.awt.Component;
import java.awt.Window;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder.IDatiClienteReadOnly;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.Popups.PopupScontiOccasione;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulateOps.SimulazioneOperazione;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class RiepilogoEPagamentoController {
    
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    private ICoordinator coordinator;
    
    private Runnable onIndietro;
    private Runnable onConferma;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public RiepilogoEPagamentoController (IRiepilogoEPagamentoView view, 
    									  Runnable onIndietroCallback,
                                          Runnable onConfermaCallback,
                                          Runnable onAnnullaCallback,
                                          ICoordinator coordinator) {
        
        
        
        riepilogoEPagamento = view;
        this.coordinator= coordinator;
        
        onIndietro = onIndietroCallback;
        onConferma = onConfermaCallback;
        onAnnulla  = onAnnullaCallback;


        viewInit();
    	impostaMensPreEsist();
    	
        aggiornaPrezzo();
        
        /*Inizializzazione listeners*/
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
    	    Component source = (Component) e.getSource();
    	    Window window = SwingUtilities.getWindowAncestor(source);

    	    SimulazioneOperazione.mostraCaricamentoFinto(window, "Operazione in corso...");
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
        
        riepilogoEPagamento.addScontoOccasioniCheckboxListener(e -> {
        	
            boolean scontoOccasioni = riepilogoEPagamento.isScontoOccasioniSelected();
            
            riepilogoEPagamento.setScontoOccasioniButtonEnabled(scontoOccasioni);
            
            if (!scontoOccasioni) {
            	
                coordinator.acquisisciScontiOccasioneSelezionati(Collections.emptyList());
                
            }
            
            aggiornaPrezzo();
            
        });
        
        riepilogoEPagamento.addScontoOccasioniListener(e -> {
        	
            if (!riepilogoEPagamento.isScontoOccasioniSelected()) {return;}
        	
        	List<Sconto> sconti = coordinator.getScontiOccasioni().
        									  retrieveAllDiscounts();
        	
            SwingUtilities.invokeLater(() -> {
                new PopupScontiOccasione(sconti, scontiSelezionati -> {
                    
                    coordinator.acquisisciScontiOccasioneSelezionati(scontiSelezionati); 
                    aggiornaPrezzo(); 

                });
                
                
            });
            
        	aggiornaScontiEDurata(); 
        	aggiornaPrezzo();
        
        
        });
        
        
    	
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
    	
	
    	coordinator.acquisisciScontoEta(riepilogoEPagamento.isScontoEtaSelected());
    	coordinator.acquisisciScontoOccasioni(riepilogoEPagamento.isScontoOccasioniSelected());
    	coordinator.acquisisciDurataAbbonamento(durataEnum);
    	
    }
    
    //----------------------------------------------------------------
    
    private void aggiornaPrezzo() {

        
        riepilogoEPagamento.setPrezzoTotale(coordinator.
        									getDiscountedPrice());
        
    }

    //----------------------------------------------------------------
    
    private void impostaMensPreEsist() {
    	
    	if(coordinator.getDTO().getDurataAbbonamento() != null) {
    		
    		riepilogoEPagamento.setScontoSuBaseMesiText(coordinator.
    													esponiDurataAbbonamento());
    		
    	}
    	
    }
    
    //----------------------------------------------------------------
    
    private void viewInit() {
    	
        /*Inizializzazione della view "RiepilogoEPagamento" con i dati acquisiti durante la
         *procedura di iscrizione*/
        riepilogoEPagamento.setDatiAbbonamento((IDatiClienteReadOnly) 
        										coordinator.getDTO());
        
        /*Tasto "Conferma" disabilitato fintanto che una opzione di pagamento non è
         *selezionata.*/
        riepilogoEPagamento.setConfermaEnabled(false);
    	
    }
    
    //----------------------------------------------------------------
    
}
