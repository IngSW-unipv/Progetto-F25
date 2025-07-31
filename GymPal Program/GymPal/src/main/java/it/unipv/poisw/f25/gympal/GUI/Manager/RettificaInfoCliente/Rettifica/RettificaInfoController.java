package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.IRettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;

public class RettificaInfoController {

	
	/*Vista*/
	IRettificaInfoView rettInfo;
	
	/*Coordinatore*/
	IRettificaCoordinator coordinator;
	
	/*CallBacks*/
	Runnable onEstrai;
	Runnable onElimina;
	Runnable onSaveMods;
	Runnable onInsData;
	Runnable onAnnulla;
	//Runnable onAvanti;
	
	//----------------------------------------------------------------
	
	public RettificaInfoController(IRettificaInfoView rettInfo,
								   Runnable onEstrai,
								   Runnable onElimina,
								   Runnable onSaveMods,
								   Runnable onInsData,
								   Runnable onAnnulla,
								   IRettificaCoordinator coordinator) {
		
		this.rettInfo = rettInfo;
		
		this.onEstrai = onEstrai;
		this.onElimina = onElimina;
		this.onSaveMods = onSaveMods;
		this.onInsData = onInsData;
		this.onAnnulla = onAnnulla;
		
		this.coordinator = coordinator;
		
		impostaEventoAnulla();
		impostaEventoEstrazione();
		impostaEventiTextFields();
		impostaEventoElimina();
		impostaEventoSaveMods();
		impostaEventoInsData();
		
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
		rettInfo.addAnnullaListener(e -> {onAnnulla.run();});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoEstrazione() {
		
		rettInfo.addEstraiListenr(e -> {
            
			coordinator.acquisisciCfCliente(rettInfo.getCodiceFiscale().getText().trim());

			onEstrai.run();

			setInfos();
			
			rettInfo.setEliminaEnabled(true);
			rettInfo.setSaveModsEnabled(true);
			
        });
		
	}
	
	//----------------------------------------------------------------
	
    private void setInfos() {
    	
    	rettInfo.getNome().setText(coordinator.getDTO().getNome());
    	rettInfo.getCognome().setText(coordinator.getDTO().getCognome());
    	rettInfo.getContatto().setText(coordinator.getDTO().getContatto());
    	rettInfo.getCfAnagrafico().setText(coordinator.getDTO().getCodiceFiscale());
    	
    	LocalDate date = coordinator.getDTO().getDataNascita();
    	Date convertedDate = java.sql.Date.valueOf(date);
    	rettInfo.getDateSpinner().setValue(convertedDate);
    	
    	if(coordinator.getDTO().getSesso().equals("M")) {
    		
    		rettInfo.getMaschio().setSelected(true);
    		
    	} else {
    		
    		rettInfo.getFemmina().setSelected(true);
    		
    	}
    	
    }
    
	//----------------------------------------------------------------
    
    private ICampoValidabile registraCampo(JTextField field, String regex) {
    	
        ICampoValidabile campo = coordinator.getCampoValidabileFactory()
        									.creaCampoValidabile(field, regex);

        ValidazioneCampo.applicaFeedbackSwing(campo);
        
        return campo;
    }
    
	//----------------------------------------------------------------
    
    private void impostaEventiTextFields() {
    	
    	coordinator.getValidatoreCampi().registra(registraCampo(rettInfo.getCodiceFiscale(),
				  								  IRegexExpression.CODICE_FISCALE));

    	coordinator.getValidatoreCampi().registra(registraCampo(rettInfo.getNome(),
    											  IRegexExpression.NAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(rettInfo.getCognome(),
    											  IRegexExpression.SURNAME_REGEXEXPRESSION));

    	coordinator.getValidatoreCampi().registra(registraCampo(rettInfo.getCfAnagrafico(),
    											  IRegexExpression.CODICE_FISCALE));
    	
    	coordinator.getValidatoreCampi().registra(registraCampo(rettInfo.getContatto(),
    											  IRegexExpression.EMAIL));

    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoElimina() {
    	
    	rettInfo.addEliminaListener(e -> {
			
	        int result = JOptionPane.showConfirmDialog(
                    null, 
                    "Vuoi davvero eliminare i dati del cliente?", 
                    "Conferma eliminazione", 
                    JOptionPane.YES_NO_OPTION); 
	        
	        if (result == JOptionPane.YES_OPTION) {
            
                rettInfo.getCfAnagrafico().setEditable(true);
                rettInfo.setInsDataEnabled(true);
                rettInfo.setSaveModsEnabled(false);
	        	
                try {

        	        onElimina.run();
                        
                 } catch (Exception ex) {
            	 
                        JOptionPane.showMessageDialog(
                        	null,
                            "Errore, tentativo eliminazione FALLITO:\n" + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                 }
             	
	        }
	                    
        });
    	    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoSaveMods() {
    	
    	rettInfo.addSaveModsListener(e -> {
    		
    		boolean validitaCampi = coordinator.getValidatoreCampi().tuttiValidi();
            System.out.println("ValiditaCampi: " + validitaCampi);
    		
            java.util.Date utilDate = (java.util.Date) rettInfo.
            						   				   getDateSpinner().getValue();
            LocalDate nascita = utilDate.toInstant().
            					atZone(ZoneId.systemDefault()).toLocalDate();
            
                        
            if(validitaCampi) {
            
	    		String sesso = rettInfo.getMaschio().isSelected() ? "Maschio" : "Femmina";
	    		
	    		coordinator.aggiornaDatiAnagrafici(rettInfo.getNome().getText().trim(), 
	    										   rettInfo.getCognome().getText().trim(), 
	    										   rettInfo.getContatto().getText().trim(), 
	    										   sesso,nascita);
	    			    			    		
	    		onSaveMods.run();
    		
            
            } else {

                JOptionPane.showMessageDialog(rettInfo.getMainPanel(),
                    "Per favore, compila correttamente tutti i campi (Verde = OK) "
                    + "- per completare la procedura ",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            }   		
    	
    	});
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoInsData() {
    	
    	rettInfo.addInsDataListener(e -> {
    		
    		boolean validitaCampi = coordinator.getValidatoreCampi().tuttiValidi();
            System.out.println("ValiditaCampi: " + validitaCampi);
    		
            java.util.Date utilDate = (java.util.Date) rettInfo.
            						   				   getDateSpinner().getValue();
            LocalDate nascita = utilDate.toInstant().
            					atZone(ZoneId.systemDefault()).toLocalDate();
            
                        
            if(validitaCampi) {
            
	    		String sesso = rettInfo.getMaschio().isSelected() ? "Maschio" : "Femmina";
	    		
	    		coordinator.acquisisciDatiAnagrafici(rettInfo.getNome().getText().trim(), 
	    										     rettInfo.getCognome().getText().trim(),
	    										     rettInfo.getCfAnagrafico().getText().trim(),
	    										     rettInfo.getContatto().getText().trim(), 
	    										     sesso,nascita);
	    			    			    		
	    		onInsData.run();
    		
            
            } else {

                JOptionPane.showMessageDialog(rettInfo.getMainPanel(),
                    "Per favore, compila correttamente tutti i campi (Verde = OK) "
                    + "- per completare la procedura ",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            }   		
    	
    	});
    	
    }
    
    //----------------------------------------------------------------

	
}
