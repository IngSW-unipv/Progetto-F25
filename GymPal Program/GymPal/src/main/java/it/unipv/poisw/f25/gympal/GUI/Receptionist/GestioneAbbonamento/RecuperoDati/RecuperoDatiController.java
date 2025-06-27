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
								  IGestioneAbbCoordinator coordinator){
		
		this.recuperoDati = recuperoDati;
		this.coordinator = coordinator;
		
		this.abbDTO = coordinator.getUtenteAbbDTO();
		
		this.onAnnulla = onAnnulla;
		this.onElimina = onElimina;
		this.onEstrai = onEstrai;
		
		recuperoDati.setEliminaEnabled(false);
		recuperoDati.setModificaEnabled(false);
		recuperoDati.setRinnovaEnabled(false);
		
		impostaEventoAnulla();
		impostaEventoElimina();
		impostaEventoEstrazione();
		impostaEventiTextFields();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
		recuperoDati.getAnnullaButton().addActionListener(e -> {
            
            onAnnulla.run();
            
        });
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoEstrazione() {
		
		recuperoDati.getEstraiButton().addActionListener(e -> {
            
			coordinator.acquisisciCfCliente(recuperoDati.getCodiceFiscale().getText().trim());
			
			onEstrai.run();
			
			/////////////////////
			recuperoDati.getCfLabel().setText("Codice fiscale cliente: " + abbDTO.getCf());
			recuperoDati.getNomeLabel().setText("Nome cliente: " + abbDTO.getNome());
			recuperoDati.getCognomeLabel().setText("Cognome cliente: " + abbDTO.getCognome());
			recuperoDati.getContattoLabel().setText("Contatto cliente: " + abbDTO.getContatto());
			recuperoDati.getSessoLabel().setText("Sesso cliente: " + abbDTO.getSesso());
			recuperoDati.getDurataAbbonamentoLabel().setText("Durata Abbonamento: " + abbDTO.getAbbonamento());
			recuperoDati.getInizioAbbLabel().setText("Data inizio abbonamento: " + abbDTO.getInizioAbbonamento());
			recuperoDati.getFineAbbLabel().setText("Data fine abbonamento: " + abbDTO.getFineAbbonamento());
			////////////////////
			
			recuperoDati.setEliminaEnabled(true);
			recuperoDati.setModificaEnabled(true);
			recuperoDati.setRinnovaEnabled(true);
			
        });
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoElimina() {
		
		recuperoDati.getEliminaButton().addActionListener(e -> {
            
            onElimina.run();
            
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

    	coordinator.getValidatoreCampi().registra(registraCampo(recuperoDati.getCodiceFiscale(), 
    															IRegexExpression.CODICE_FISCALE));


    }
    
	//----------------------------------------------------------------

}
