package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;

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
	
	/*DTO*/
	private IUtenteAbbDTO abbDTO;
	
	//----------------------------------------------------------------
	
	public RecuperoDatiController(IRecuperoDatiView recuperoDati,
						   		  Runnable onAnnulla,
								  Runnable onElimina, 
								  Runnable onEstrai,
								  Runnable onRinnova,
								  Runnable onModifica,
								  IGestioneAbbCoordinator coordinator){
		
		this.recuperoDati = recuperoDati;
		this.coordinator = coordinator;
		
		this.abbDTO = (IUtenteAbbDTO)coordinator.getDTO();
		
		this.onAnnulla = onAnnulla;
		this.onElimina = onElimina;
		this.onEstrai = onEstrai;
		this.onRinnova = onRinnova;
		this.onModifica = onModifica;
		
		
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
	
	private void impostaEventoElimina() {
		
		recuperoDati.addEliminaListener(e -> {onElimina.run();});
		
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

    	coordinator.getValidatoreCampi().registra(registraCampo(recuperoDati.getCodiceFiscale(), 
    															IRegexExpression.CODICE_FISCALE));

    }
    
	//----------------------------------------------------------------
    
    private void impostaEventoRinnova() {
    	
		recuperoDati.addRinnovaListenr(e -> {onRinnova.run();});
    	
    }
    
	//----------------------------------------------------------------
    
    private void impostaEventoModifica() {
    	
		recuperoDati.addModificaListenr(e -> {onModifica.run();});
    	
    }
    
	//----------------------------------------------------------------
    
    private void setLabels() {
    	
		recuperoDati.getCfLabel().setText("Codice fiscale cliente: " + abbDTO.getCodiceFiscale());
		recuperoDati.getNomeLabel().setText("Nome cliente: " + abbDTO.getNome());
		recuperoDati.getCognomeLabel().setText("Cognome cliente: " + abbDTO.getCognome());
		recuperoDati.getContattoLabel().setText("Contatto cliente: " + abbDTO.getContatto());
		recuperoDati.getSessoLabel().setText("Sesso cliente: " + abbDTO.getSesso());
		recuperoDati.getDurataAbbonamentoLabel().setText("Durata Abbonamento: " + abbDTO.getDurataAbbonamento());
		recuperoDati.getInizioAbbLabel().setText("Data inizio abbonamento: " + abbDTO.getInizioAbbonamento());
		recuperoDati.getFineAbbLabel().setText("Data fine abbonamento: " + abbDTO.getFineAbbonamento());
    	
    }
    
	//----------------------------------------------------------------
    
    private void viewInit() {
    	
		recuperoDati.setEliminaEnabled(false);
		recuperoDati.setModificaEnabled(false);
		recuperoDati.setRinnovaEnabled(false);
		
    }
    
	//----------------------------------------------------------------

}
