package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView;


import java.awt.Component;
import java.awt.Window;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexExpression;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.ICustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulateOps.SimulazioneOperazione;

public class ClientInfosController implements IRegexExpression {

	/*Vista*/
    private IClientInfosView clientInfos;
    
    /*Coordinatore*/
    private ICustomerRegistrationCoordinator coordinator;

    /*CallBacks*/
    private Runnable onAvanti;
    private Runnable onIndietro;
    private Runnable onAnnulla;
    
    //----------------------------------------------------------------
    
    public ClientInfosController(IClientInfosView infos, 
    							 Runnable onAvantiCallback,
    							 Runnable onIndietroCallback,
                                 Runnable onAnnullaCallback,
                                 ICustomerRegistrationCoordinator coordinator) {
        /*Vista*/
        clientInfos = infos;
        
        /*Callbacks*/
        onAvanti = onAvantiCallback;
        onIndietro = onIndietroCallback;
        onAnnulla = onAnnullaCallback;
        
        /*Coordinatore*/
        this.coordinator = coordinator;
        
 
        /*Applicazione Listeners*/
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
    	
        ICampoValidabile campo = coordinator.getCampoValidabileFactory()
        									.creaCampoValidabile(field, regex);

        ValidazioneCampo.applicaFeedbackSwing(campo);
        
        return campo;
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventiTextFields() {

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getNome(),
    											  IRegexExpression.NAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getCognome(),
    											  IRegexExpression.SURNAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getCodiceFiscale(),
    											  IRegexExpression.CODICE_FISCALE));
    	
    	coordinator.getValidatoreCampi().registra(registraCampo(clientInfos.getContatto(),
    											  IRegexExpression.EMAIL));

    }
    
    //----------------------------------------------------------------
    
    private void impostaControlloEta () {
        
        clientInfos.addDateSpinnerListener(e -> {
            
            Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
            
            LocalDate nascita = coordinator.getDateUtils().convertiDaUtilDate(dataDiNascita);
            
            
            if (coordinator.isMinorenne(nascita)) {
                
                clientInfos.setBtnsVisibility(true);
                
            } else {
                
                clientInfos.setBtnsVisibility(false);

            }
            
            // "clientInfos" è il componente genitore, quindi ricalcola il layout
            clientInfos.getMainSplitPanel().revalidate();
            clientInfos.getMainSplitPanel().repaint();
            
        });
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAvanti() {
    	
        clientInfos.addAvantiListener(e -> {
        	
        	Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
        	
            //LocalDate nascita = dataDiNascita.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate nascita = coordinator.getDateUtils().convertiDaUtilDate(dataDiNascita);

        	/*Riferito al contenuto dei "JTextField"*/
            boolean validitaCampi = coordinator.getValidatoreCampi().tuttiValidi();
            System.out.println("ValiditaCampi: " + validitaCampi);

            /*Il metodo "richiediCertificato()" restituisce sempre 'true', di default,
             *dunque, causa negazione, la prima parte dell'equazione booleana è sempre
             *posta a 'false' - per superare questo controllo, l'utente deve scegliere
             *"Si", di modo da avere "FALSE OR TRUE = TRUE"*/
            boolean certificatoOk = coordinator.getCtrlReqAnagraficiService()
                    						   .isCertificatoValido(clientInfos.getCertIdoneitàSi().isSelected());
            
            System.out.println("CertificatoOk: " + certificatoOk);
            
            /*Se non è richiesto il permesso (cliente maggiorenne), allora tutto ok.
			  Se è richiesto (cliente minorenne), è controllato che sia selezionato "Sì"*/
            boolean permessoGenitoriOK = coordinator.getCtrlReqAnagraficiService()
                    								.isPermessoGenitoriValido(nascita, clientInfos.getPermessoGenitoriSi().isSelected());
            System.out.println("PermessoGenitoriOk: " + permessoGenitoriOK);

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
        
        clientInfos.addIndietroListener(e -> {onIndietro.run();});
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAnulla() {
        
        clientInfos.addAnnullaListener(e -> {onAnnulla.run();});
        
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoAcquisisciPermesso() {
        
    	clientInfos.addAcquisisciPermessoListener(e -> {
    		
    	    Component source = (Component) e.getSource();
    	    Window window = SwingUtilities.getWindowAncestor(source);

    	    SimulazioneOperazione.mostraCaricamentoFinto(window, "Operazione in corso...");
    	    
    	});
        
    }   
    
    //----------------------------------------------------------------
    
}
