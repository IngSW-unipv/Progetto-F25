package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexExpression;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;

public class RecuperoDatiController {
	
	/*Vista*/
	IRecuperoDatiView recuperoDati;
	
	/*Coordinatore*/
	IGestioneAbbCoordinator coordinator;
	
	/*CallBacks*/
	private Runnable onAnnulla;
	private Runnable onRinnova;
	private Runnable onModifica;
	private Runnable onElimina;
	private Runnable onEstrai;
	
	//----------------------------------------------------------------
	
	public RecuperoDatiController(IRecuperoDatiView recuperoDati,
						   		  Runnable onAnnulla,
								  Runnable onElimina, 
								  Runnable onEstrai,
								  Runnable onRinnova,
								  Runnable onModifica,
								  IGestioneAbbCoordinator coordinator){
		
		/*Vista*/
		this.recuperoDati = recuperoDati;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Callbacks*/
		this.onAnnulla = onAnnulla;
		this.onElimina = onElimina;
		this.onEstrai = onEstrai;
		this.onRinnova = onRinnova;
		this.onModifica = onModifica;
		
		/*Inizializzazione vista*/
		viewInit();
		
		/*Inizializzazione listeners*/
		impostaEventoAnulla();
		impostaEventoElimina();
		impostaEventoEstrazione();
		impostaEventiTextFields();
		impostaEventoRinnova();
		impostaEventoModifica();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
		recuperoDati.addAnnullaListener(e -> {onAnnulla.run();});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoElimina() {
		
		recuperoDati.addEliminaListener(e -> {onElimina.run();});
		
	}
	
	//----------------------------------------------------------------
	
    private void impostaEventoModifica() {
    	
		recuperoDati.addModificaListenr(e -> {onModifica.run();});
    	
    }
    
	//----------------------------------------------------------------
    
    
    private void impostaEventoRinnova() {
    	
		recuperoDati.addRinnovaListenr(e -> {onRinnova.run();});
    	
    }
    
	//----------------------------------------------------------------
	
	private void impostaEventoEstrazione() {
		
		recuperoDati.addEstraiListenr(e -> {
            
			coordinator.acquisisciCfCliente(recuperoDati.getCodiceFiscale().getText().trim());

			onEstrai.run();

			setLabels();
			
			recuperoDati.setEliminaEnabled(true);
			recuperoDati.setModificaEnabled(true);
			recuperoDati.setRinnovaEnabled(true);
			
        });
		
	}
	
	//----------------------------------------------------------------
	
    
    private void impostaEventiTextFields() {

    	coordinator.getValidatoreCampi().registra(registraCampo(recuperoDati.getCodiceFiscale(), 
    															IRegexExpression.CODICE_FISCALE));

    }
    
	//----------------------------------------------------------------
	
    private ICampoValidabile registraCampo(JTextField field, String regex) {
    	
        ICampoValidabile campo = coordinator.getCampoValidabileFactory()
        									.creaCampoValidabile(field, regex);

        ValidazioneCampo.applicaFeedbackSwing(campo);
        
        return campo;
    }
    
	//----------------------------------------------------------------
    
    private void setLabels() {
    	
		recuperoDati.getCfLabel().setText("Codice fiscale cliente: " + coordinator.getDTO().getCodiceFiscale());
		recuperoDati.getNomeLabel().setText("Nome cliente: " + coordinator.getDTO().getNome());
		recuperoDati.getCognomeLabel().setText("Cognome cliente: " + coordinator.getDTO().getCognome());
		recuperoDati.getContattoLabel().setText("Contatto cliente: " + coordinator.getDTO().getContatto());
		recuperoDati.getSessoLabel().setText("Sesso cliente: " + coordinator.getDTO().getSesso());
		recuperoDati.getDurataAbbonamentoLabel().setText("Durata Abbonamento: " + coordinator.getDTO().getDurataAbbonamento());
		recuperoDati.getInizioAbbLabel().setText("Data inizio abbonamento: " + coordinator.getDTO().getInizioAbbonamento());
		recuperoDati.getFineAbbLabel().setText("Data fine abbonamento: " + coordinator.getDTO().getFineAbbonamento());
    	
    }
    
	//----------------------------------------------------------------
    
    private void viewInit() {
    	
		recuperoDati.setEliminaEnabled(false);
		recuperoDati.setModificaEnabled(false);
		recuperoDati.setRinnovaEnabled(false);
		
    }
    
	//----------------------------------------------------------------

}
