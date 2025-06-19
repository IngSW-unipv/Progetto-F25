package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabile.ICampoValidabile;
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
    
    private ICampoValidabile registraCampo(JTextField field, String regex) {
    	
    	/*Piccolo metodo di supporto sviluppato per rispettare il principio "DRY"
    	 *("Don't Repeat Yourself")*/
    	
        ICampoValidabile campo = coordinator.getCampoValidabileFactory().creaCampoValidabile(field, regex);

        ValidazioneCampo.applicaFeedbackSwing(campo);
        
        return campo;
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventiTextFields() {

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getNome(), IRegexExpression.NAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getCognome(), IRegexExpression.SURNAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getCodiceFiscale(), IRegexExpression.CODICE_FISCALE));
    	
    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getContatto(), IRegexExpression.EMAIL));

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
        	
        	Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
            LocalDate nascita = dataDiNascita.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        	/*Riferito al contenuto dei "JTextField"*/
            boolean validitaCampi = coordinator.getValidatoreCampi().tuttiValidi();

            /*Il metodo "richiediCertificato()" restituisce sempre 'true', di default,
             *dunque, causa negazione, la prima parte dell'equazione booleana è sempre
             *posta a 'false' - per superare questo controllo, l'utente deve scegliere
             *"Si", di modo da avere "FALSE OR TRUE = TRUE"*/
            boolean certificatoOk = !coordinator.getCtrlReqAnagraficiService().richiediCertificato() ||
            						 clientInfos.getCertIdoneitàSi().isSelected();
            
            /*Se non è richiesto il permesso (cliente maggiorenne), allora tutto ok.
			  Se è richiesto (cliente minorenne), è controllato che sia selezionato "Sì"*/
            boolean permessoGenitoriOK = !coordinator.getCtrlReqAnagraficiService().richiediPermessoGenitori(nascita) ||
            							  clientInfos.getPermessoGenitoriSi().isSelected();

            if (validitaCampi && certificatoOk && permessoGenitoriOK) {

                String sesso = clientInfos.getMaschio().isSelected() ? "Maschio" : "Femmina";


                coordinator.acquisisciDatiAnagrafici(
                		
                    clientInfos.getNome().getText().trim(),
                    clientInfos.getCognome().getText().trim(),
                    clientInfos.getCodiceFiscale().getText().trim(),
                    clientInfos.getContatto().getText().trim(),
                    sesso, true, true, nascita);

                onAvanti.run();

            } else {

                JOptionPane.showMessageDialog(clientInfos.getMainPanel(),
                    "Per favore, compila correttamente tutti i campi (Verde = OK) - per completare la procedura " +
                    "il cliente -DEVE- fornire certificato di idoneità e/o il permesso genitoriale.",
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
