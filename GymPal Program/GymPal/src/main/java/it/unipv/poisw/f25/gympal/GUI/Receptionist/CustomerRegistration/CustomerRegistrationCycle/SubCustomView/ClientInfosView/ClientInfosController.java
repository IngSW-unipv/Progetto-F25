package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView;

import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulazioneOperazione;

public class ClientInfosController implements IRegexExpression {

    private IClientInfosView clientInfos;
    private IRegistrationCoordinator coordinator;
    

    
    private Runnable onAvanti;
    private Runnable onIndietro;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public ClientInfosController(IClientInfosView infos, Runnable onAvantiCallback,
                                                         Runnable onIndietroCallback,
                                                         Runnable onAnnullaCallback,
                                                 IRegistrationCoordinator coordinator) {
        
        clientInfos = infos;
        onAvanti = onAvantiCallback;
        onIndietro = onIndietroCallback;
        onAnnulla = onAnnullaCallback;
        
        this.coordinator = coordinator;
        
 
        
        impostaEventiTextFields();
        impostaControlloEta ();
        impostaEventoAvanti();
        impostaEventoIndietro();
        impostaEventoAnulla();
        impostaEventoAcquisisciPermesso();
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventiTextFields() {

        ValidazioneCampo.aggiungiValidatore(clientInfos.getNome(), NAME_REGEXEXPRESSION);
        
        ValidazioneCampo.aggiungiValidatore(clientInfos.getCognome(), SURNAME_REGEXEXPRESSION);
        
        ValidazioneCampo.aggiungiValidatore(clientInfos.getCodiceFiscale(), CODICE_FISCALE);
        
        ValidazioneCampo.aggiungiValidatore(clientInfos.getContatto(), EMAIL);

    }
    
    //----------------------------------------------------------------
    
    private void impostaControlloEta () {
        
        clientInfos.getDateSpinner().addChangeListener(e -> {
            
            Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
            
            LocalDate nascita = dataDiNascita.toInstant().atZone(ZoneId.systemDefault())
                    									 .toLocalDate();
            
            
            if (coordinator.isMinorenne(nascita)) {
                
                clientInfos.getPermessoGenitoriSi().setVisible(true);
                clientInfos.getPermessoGenitoriNo().setVisible(true);
                clientInfos.getPermessoGenitoriLabel().setVisible(true);
                clientInfos.getAcquisisciPermesso().setVisible(true);
                
            } else {
                
                clientInfos.getPermessoGenitoriSi().setVisible(false);
                clientInfos.getPermessoGenitoriNo().setVisible(false);
                clientInfos.getPermessoGenitoriLabel().setVisible(false);
                clientInfos.getAcquisisciPermesso().setVisible(false);
            }
            
            // "clientInfos" è il componente genitore, quindi ricalcola il layout
            clientInfos.getMainSplitPanel().revalidate();
            clientInfos.getMainSplitPanel().repaint();
            
        });

        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAvanti() {
        
        clientInfos.getAvantiButton().addActionListener(e -> {
            
            // Controllo validità globale
            
            boolean validitaCampi = true;
            
            // Syso per verificare lo stato dei campi
            /*
            System.out.println("Nome background: " + clientInfos.getNome().getBackground());
            System.out.println("Cognome background: " + clientInfos.getCognome().getBackground());
            System.out.println("Codice Fiscale background: " + clientInfos.getCodiceFiscale().getBackground());
            */
            
            // Estrazione valori RGB per ogni campo
            
            Color nomeColor = clientInfos.getNome().getBackground();
            Color cognomeColor = clientInfos.getCognome().getBackground();
            Color codiceFiscaleColor = clientInfos.getCodiceFiscale().getBackground();
            Color contatto = clientInfos.getContatto().getBackground();
            
            // E' controllato che i colori siano uguali a #b2fab4 (r=178, g=250, b=180).
            /* Bisogna confrontare i singoli valori RGB perché quando sono confrontati i colori
             * con il metodo getBackground(), è possibile che ci siano piccole discrepanze nel
             * modo in cui il colore viene gestito (come la trasparenza o altre proprietà 
             * interne)
             */
            
            /* Ho scelto di percorrere questa strada per non creare ridondanza con il controllo
             * eseguito nel metodo "impostaEventiTextFields()"
             */
            
            if (nomeColor.getRed() != 178 || nomeColor.getGreen() != 250 || nomeColor.getBlue() != 180 ||
                cognomeColor.getRed() != 178 || cognomeColor.getGreen() != 250 || cognomeColor.getBlue() != 180 ||
                codiceFiscaleColor.getRed() != 178 || codiceFiscaleColor.getGreen() != 250 || codiceFiscaleColor.getBlue() != 180 ||
                contatto.getRed() != 178 || contatto.getGreen() != 250 || contatto.getBlue() != 180) {
                
                validitaCampi = false;
            }
            
            // Controllo RadioButtons: è verificato che i radio per Certificato di Idoneità e Permesso Genitori
            // siano su "Si"
            /*
            System.out.println("Certificato di Idoneità: " + clientInfos.getCertIdoneitàSi().isSelected());
            System.out.println("Permesso Genitori: " + clientInfos.getPermessoGenitoriSi().isSelected());
            */
            
            if (!clientInfos.getCertIdoneitàSi().isSelected() || 
                !clientInfos.getPermessoGenitoriSi().isSelected()) {
                
                validitaCampi = false;
            }
            
            // Se tutto è valido, procedi con l'azione "Avanti"
            
            if (validitaCampi) {
                
            	String sesso;
                
                if(clientInfos.getMaschio().isSelected()) {
                    sesso = "Maschio";
                } else {
                    sesso = "Femmina";
                }
                

                
                /* L'informazione contenuta nel JDateSpinner è tradotta nel formato accettato dal
                 * metodo "setDataNascita(LocalDate dataNascita)"
                 */
                Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
                LocalDate nascita = dataDiNascita.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   
                
                coordinator.acquisisciDatiAnagrafici(clientInfos.getNome().getText().trim(),
                									 clientInfos.getCognome().getText().trim(),
                									 clientInfos.getCodiceFiscale().getText().trim(),
                									 clientInfos.getContatto().getText().trim(),
                									 sesso, true, true, nascita);
                
                onAvanti.run();
                
            } else {
                
                // Mostra un messaggio di errore se uno dei campi è invalido
                
                /* "clientInfos" è il componente genitore della finestra di dialogo, dunque il
                 * dialogo sarà disegnato centrato rispetto a "clientInfos" (se invece vi fosse
                 * "null", allora il dialogo sarebbe centrato rispetto allo schermo).
                 * 
                 * "Errore" è il titolo della finestra di dialogo.
                 * 
                 * "JOptionPane.ERROR_MESSAGE" è il tipo di messaggio, che determina l’icona
                 * da mostrare accanto al testo
                 */
                
                JOptionPane.showMessageDialog(clientInfos.getMainPanel(), 
                    "Per favore, compila correttamente tutti i campi (Verde = OK) - per completare la procedura" +
                    " il cliente -DEVE- fornire certificato di idonetà e/o il permesso genitoriale.", 
                    "Errore", JOptionPane.ERROR_MESSAGE);
                
            }
            
        });
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoIndietro() {
        
        clientInfos.getIndietroButton().addActionListener(e -> {
            
            onIndietro.run();
            
        });
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAnulla() {
        
        clientInfos.getAnnullaButton().addActionListener(e -> {
            
            onAnnulla.run();
            
        });
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAcquisisciPermesso() {
        
        clientInfos.getAcquisisciPermesso().addActionListener(e -> {
            
            JFrame framePadre = (JFrame) javax.swing.SwingUtilities.getWindowAncestor((java.awt.Component) e.getSource());

            SimulazioneOperazione simulazione = new SimulazioneOperazione(framePadre);
            simulazione.start();
            
        });
        
    }    
    
}
