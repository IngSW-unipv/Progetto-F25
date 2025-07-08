package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.DTOHandlerHelper;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.UtenteAbbDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.IRecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.IEliminaProfiloView;

public class GestioneAbbCoordinator implements IGestioneAbbCoordinator {
	
	private IReceptionistController viewHandler;
	
    /*Viste*/
    private IRecuperoDatiView recuperoDati;
    private IEliminaProfiloView eliminazioneDati;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB veicoloDati;
    private IDeleteClientFromDB headHunter;
    
    
    private UtenteAbbDTO utenteAbbDTO;
    private DTOHandlerHelper costruttoreDTOHelper;
	
    //----------------------------------------------------------------
	
	public GestioneAbbCoordinator(IReceptionistController viewHandler,
								  ICampoValidabileFactory campovalidabileFactory,
								  IValidatoreCampi validatoreCampi,
								  IRetrieveClientFromDB veicoloDati,
								  IDeleteClientFromDB headHunter) {
		
		this.viewHandler = viewHandler;
    	this.campoValidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.veicoloDati = veicoloDati;
    	this.headHunter = headHunter;
		
		
		inizializzaCicloGestione();
		
	}
	
    //----------------------------------------------------------------
	
	private void inizializzaCicloGestione() {
		
		
		utenteAbbDTO = new UtenteAbbDTO();
		costruttoreDTOHelper = new DTOHandlerHelper(utenteAbbDTO);
		
		setupRecuperoDati();
		
	}
	
    //----------------------------------------------------------------
	
	private void setupRecuperoDati() {
		
		recuperoDati = new RecuperoDatiView ();
		
		viewHandler.registraSchermata("LOAD_CLIENT", recuperoDati.getMainPanel());
		
		new RecuperoDatiController(
				
				recuperoDati,
				
				//OnAnnulla
				() -> {
					
					inizializzaCicloGestione();
					viewHandler.mostraSchermata("SCHERMATA0");	
				}, 
				
				//onElimina
				
				() -> {
					
					eliminaProfiloCliente();
					viewHandler.mostraSchermata("ELIMINA_CLIENT");
					
				},
				
				//OnEstrai
				() -> {
					
					veicoloDati.transfer(utenteAbbDTO);
					
					
				},
				
				this);
		
	}
	
    //----------------------------------------------------------------
	
	private void eliminaProfiloCliente() {
		
		eliminazioneDati = new EliminaProfiloView();
		
		viewHandler.registraSchermata("ELIMINA_CLIENT", eliminazioneDati.getMainPanel());
		
		new EliminaProfiloController(eliminazioneDati, 
				
									  //onAnnulla
									  () -> {
										  
										  inizializzaCicloGestione();
										  viewHandler.mostraSchermata("SCHERMATA0");
										  
									  },
									  
									  //onIndietro
									  () -> {
										  
										  viewHandler.mostraSchermata("LOAD_CLIENT");
										  
									  },
									  
									  //onConferma
									  () -> {
										  
										  	
										    boolean esito = headHunter.huntAndKill(utenteAbbDTO);
										    
										    if (esito) {
										    	
										        JOptionPane.showMessageDialog(
										        		null, 
										        		"Eliminazione avvenuta con successo.",
										        		"Successo", 
										        		JOptionPane.INFORMATION_MESSAGE);
										        
										    } else {
										    	
										        JOptionPane.showMessageDialog(
										        		null, 
										        		"Errore durante l'eliminazione del profilo.", 
										        		"Errore", 
										        		JOptionPane.ERROR_MESSAGE);
										        
										    }
										  
										  inizializzaCicloGestione();
										  viewHandler.mostraSchermata("SCHERMATA0");
										  
									  },
									  
									  this);
		
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void acquisisciCfCliente(String Cf) {
		
		costruttoreDTOHelper.recuperaCf(Cf);
				
	}
	
    //----------------------------------------------------------------
    
    @Override
    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
    	return campoValidabileFactory;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IValidatoreCampi getValidatoreCampi() {
    	
    	return validatoreCampi;
    	
    }
     
    //----------------------------------------------------------------
    
    @Override
    public IUtenteAbbDTO getUtenteAbbDTO() {
    	
    	return utenteAbbDTO;
    	
    }
    
    //----------------------------------------------------------------
    

}
