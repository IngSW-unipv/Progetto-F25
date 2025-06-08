package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulazioneOperazione;
import it.unipv.poisw.f25.gympal.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.factories.StrategieCalcoloPrezzoFactory;

public class RiepilogoEPagamentoController {
    
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    
    private AbbonamentoDTO abbonamentoDTO;
    
    private Runnable onIndietro;
    private Runnable onConferma;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public RiepilogoEPagamentoController (IRiepilogoEPagamentoView view, 
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
        

        
        impostaEventi();
                
        
    }
    
    //----------------------------------------------------------------    

    private void impostaEventi() {
        // Imposta listener usando i metodi dell'interfaccia
        
        riepilogoEPagamento.addIndietroListener(e -> onIndietro.run());
        riepilogoEPagamento.addAnnullaListener(e -> onAnnulla.run());
        
        riepilogoEPagamento.addConfermaListener(e -> {
            if(riepilogoEPagamento.isContantiSelected() ||
               riepilogoEPagamento.isCartaSelected() ||
               riepilogoEPagamento.isNoPagamentoSelected()) {
                
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
        
        riepilogoEPagamento.addScontoSuBaseMesiListener(e -> riepilogoEPagamento.showPopupMenu());
        
        riepilogoEPagamento.addMensilitaListener(e -> {
            // Aggiorna testo sconto in base alla mensilità selezionata
            riepilogoEPagamento.setScontoSuBaseMesiText(riepilogoEPagamento.getDurataSelezionata());
            aggiornaPrezzo();
        });
        
        riepilogoEPagamento.addScontoEtaListener(e -> aggiornaPrezzo());
        riepilogoEPagamento.addScontoOccasioniListener(e -> aggiornaPrezzo());
        
        riepilogoEPagamento.addMetodoPagamentoListener(e -> {
            if(riepilogoEPagamento.isContantiSelected() || riepilogoEPagamento.isCartaSelected()) {
                abbonamentoDTO.setStatoPagamento(true);
            } else if (riepilogoEPagamento.isNoPagamentoSelected()) {
                abbonamentoDTO.setStatoPagamento(false);
            }
        });
        
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
    
    private void aggiornaPrezzo() {
        
        boolean scontoEta = riepilogoEPagamento.isScontoEtaSelected();
        
        boolean scontoOccasioni = riepilogoEPagamento.isScontoOccasioniSelected();

        String durata = null;
        
        String durataSelezionata = riepilogoEPagamento.getDurataSelezionata();
        
        if ("trimestrale".equalsIgnoreCase(durataSelezionata)) {
        	
            durata = "trimestrale";
            
        } else if ("semestrale".equalsIgnoreCase(durataSelezionata)) {
        	
            durata = "semestrale";
            
        } else if ("annuale".equalsIgnoreCase(durataSelezionata)) {
        	
            durata = "annuale";
            
        } else {
        	
            durata = null;
            
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
