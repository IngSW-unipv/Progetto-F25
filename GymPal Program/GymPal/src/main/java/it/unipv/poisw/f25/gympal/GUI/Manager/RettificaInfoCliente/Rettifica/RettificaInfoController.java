package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica;

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
	Runnable onAvanti;
	
	/*Flusso*/
	private StatoFlussoRettifica statoCorrente = StatoFlussoRettifica.INIZIALE;
	
	//----------------------------------------------------------------
	
	public RettificaInfoController(IRettificaInfoView rettInfo,
								   Runnable onEstrai,
								   Runnable onElimina,
								   Runnable onSaveMods,
								   Runnable onInsData,
								   Runnable onAnnulla,
								   Runnable onAvanti,
								   IRettificaCoordinator coordinator) {
		
		this.rettInfo = rettInfo;
		
		this.onEstrai = onEstrai;
		this.onElimina = onElimina;
		this.onSaveMods = onSaveMods;
		this.onInsData = onInsData;
		this.onAnnulla = onAnnulla;
		this.onAvanti = onAvanti;
		
		this.coordinator = coordinator;
		
		impostaEventoAnulla();
		impostaEventoAvanti();
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
	
	public void impostaEventoAvanti() {
		
		rettInfo.addAvantiListener(e -> {onAvanti.run();});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoEstrazione() {
		
		rettInfo.addEstraiListenr(e -> {
            
			coordinator.acquisisciCfCliente(rettInfo.getCodiceFiscale().getText().trim());

			onEstrai.run();
			
			statoCorrente = StatoFlussoRettifica.CLIENTE_ESTRATTO;
			aggiornaEditabilitaCampi();
			
        });
		
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
            
	            statoCorrente = StatoFlussoRettifica.CLIENTE_ELIMINATO;
	            aggiornaEditabilitaCampi();
                	        	
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
    	
	    	rettInfo.addSaveModsListener(e -> eseguiSeCampiValidi(() -> {
	        	
	            coordinator.aggiornaDatiAnagrafici(rettInfo.getDatiClienteRaw());
	            onSaveMods.run();
	            
	        })
	    );
    	
    }
    
    //----------------------------------------------------------------
    
    private void impostaEventoInsData() {
    	
        rettInfo.addInsDataListener(e -> eseguiSeCampiValidi(() -> {
        	
                coordinator.acquisisciDatiAnagrafici(rettInfo.getDatiClienteRaw());
                onInsData.run();
                
            })
        );
    }
    
    //----------------------------------------------------------------
    
    private void eseguiSeCampiValidi(Runnable eseguiAzione) {
    	
        boolean validitaCampi = coordinator.getValidatoreCampi().tuttiValidi();
        System.out.println("ValiditaCampi: " + validitaCampi);

        if (validitaCampi) {
        	
            eseguiAzione.run();
            
        } else {
        	
            JOptionPane.showMessageDialog(
                rettInfo.getMainPanel(),
                "Per favore, compila correttamente tutti i campi (Verde = OK) "
                + "- per completare la procedura ",
                "Errore", JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    //----------------------------------------------------------------
    
    private void aggiornaEditabilitaCampi() {
    	
        switch (statoCorrente) {
        
            case CLIENTE_ESTRATTO:
                rettInfo.getCfAnagrafico().setEditable(false);
                rettInfo.setInsDataEnabled(false);
                break;
                
            case CLIENTE_ELIMINATO:
                rettInfo.getCfAnagrafico().setEditable(true);
                rettInfo.setInsDataEnabled(true);
                rettInfo.setSaveModsEnabled(false);
                break;
                
            default:
                rettInfo.getCfAnagrafico().setEditable(true);
                rettInfo.setInsDataEnabled(false);
                break;
                
        }
        
    }
    
    //----------------------------------------------------------------


	
}
