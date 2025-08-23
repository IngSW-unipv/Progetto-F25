package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente;

import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.DTO.DTOHandlerRettifica;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.IRettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.RettificaInfoController;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.RettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.IModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ModAbbContract.IModAbbContract;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;

public class RettificaCoordinator implements IRettificaCoordinator, IModAbbContract{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
    
    /*Viste*/
    private IRettificaInfoView rettView;
    private IModificaAbbonamentoView modificaAbb;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB retrieveDBData;
    private IDeleteClientFromDB removeDBData;
    private IUpdateClientInsideDB updateDBData;
    private ICommitNewClientToDB commitDataToDB;
    

	/*DTO e Inizializzatore*/
    private MasterDTO abbDTO;
    private DTOHandlerRettifica costruttoreDTOHelper;
    
    //----------------------------------------------------------------
    
    public RettificaCoordinator(IRegistraEMostraSchermate viewHandler,
    							ICampoValidabileFactory campoValidabileFactory,
    							IValidatoreCampi validatoreCampi,
    							IRetrieveClientFromDB recuperaDati,
    							IDeleteClientFromDB headHunter,
    							IUpdateClientInsideDB updateClient,
    							ICommitNewClientToDB immettiDati) {
    	
    	/*Controllore*/
    	this.viewHandler = viewHandler;
    	
    	/*Servizi*/
    	this.campoValidabileFactory = campoValidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.retrieveDBData = recuperaDati;
    	this.removeDBData = headHunter;
    	this.updateDBData = updateClient;
    	this.commitDataToDB = immettiDati;
    	
    	/*Navigazione GUI*/
    	inizializzaCicloRettifica();
    	
    }
    
    //----------------------------------------------------------------
    
	private void inizializzaCicloRettifica() {
		
		
		abbDTO = new MasterDTO();
		costruttoreDTOHelper = new DTOHandlerRettifica(abbDTO);
		
		setupRettificaDatiAnagrafici();
		
		
	}
	
    //----------------------------------------------------------------
	
	private void setupRettificaDatiAnagrafici() {
		
		rettView = new RettificaInfoView(new DynamicButtonSizeSetter(),
	  			 						 new EtichettaPiuCampoFactory());

		viewHandler.registraSchermata("RETT_INFO", rettView.getMainPanel());
		
		new RettificaInfoController(
		
			rettView,
			
			/*onEstrai*/
			() -> {
		        retrieveDBData.retrieve(abbDTO);                  
		        rettView.setTextFieldsContent(abbDTO);            
		        rettView.setEliminaEnabled(true);                 
		        rettView.setSaveModsEnabled(true);},
			
			/*onElimina*/
			() -> {
											    
			if (removeDBData.huntAndKill(abbDTO)) {
			
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
						JOptionPane.ERROR_MESSAGE);}
			
				},
			
			/*onSaveMods*/
			() -> {
			
			if (updateDBData.update(abbDTO, abbDTO.getInizioAbbonamento())) {
			
				JOptionPane.showMessageDialog(null, "Modifica effettuata con successo!",
										  "Successo", JOptionPane.INFORMATION_MESSAGE);
				inizializzaCicloRettifica();
				viewHandler.mostraSchermata("SCHERMATA0");
			
			} else {
			
				JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati.",
										  "Errore", JOptionPane.ERROR_MESSAGE);
			
				}
			
							
			},
			
			/*onInsData*/
			() -> {
			
			           	
			if (commitDataToDB.commit(abbDTO, abbDTO.getInizioAbbonamento())) {
			
				JOptionPane.showMessageDialog(null, "Modifica effettuata con successo!",
										  "Successo", JOptionPane.INFORMATION_MESSAGE);
				inizializzaCicloRettifica();
				viewHandler.mostraSchermata("SCHERMATA0");
			
			} else {
			
				JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati.",
										  "Errore", JOptionPane.ERROR_MESSAGE);
			
				}
			
			},
			
			/*onAnnulla*/
			() -> {inizializzaCicloRettifica();
				   viewHandler.mostraSchermata("SCHERMATA0");
			
			},
			
			/*onAvanti*/
			() -> {setupModificaAbbonamento();
        		   viewHandler.mostraSchermata("MODIFICA_ABB");},
			
			this
		
		);		
		
	}
	
	//----------------------------------------------------------------
	
	private void setupModificaAbbonamento() {
		
		modificaAbb = new ModificaAbbonamentoView(new DynamicButtonSizeSetter());
		
		viewHandler.registraSchermata("MODIFICA_ABB", modificaAbb.getMainPanel());
		
		new ModificaAbbonamentoController (modificaAbb,
				
				//onAnnulla
				() -> {inizializzaCicloRettifica();
					   viewHandler.mostraSchermata("SCHERMATA0");},
				
				//onIndietro
				() -> {viewHandler.mostraSchermata("RETT_INFO");},
				
				//onAvanti
				() -> {	
					
					if (updateDBData.update(abbDTO, abbDTO.getInizioAbbonamento())) {
						
						JOptionPane.showMessageDialog(null, "Modifica effettuata con successo!",
												  "Successo", JOptionPane.INFORMATION_MESSAGE);
						inizializzaCicloRettifica();
						viewHandler.mostraSchermata("SCHERMATA0");
					
					} else {
					
						JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati.",
												  "Errore", JOptionPane.ERROR_MESSAGE);
					
						}			
					
				},
				
				this);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void acquisisciCfCliente(String cf) {
		
		costruttoreDTOHelper.recuperaCf(cf);
				
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
    public IDatiCliente getDTO() {
    	
    	return abbDTO;
    	
    }
    
    //----------------------------------------------------------------
       
    @Override
    public void acquisisciDatiAnagrafici(RawClientData raw) {
    	
    	costruttoreDTOHelper.impostaDatiAnagrafici(raw);
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void aggiornaDatiAnagrafici(RawClientData raw) {
    	
    	costruttoreDTOHelper.aggiornaDatiAnagrafici(raw);
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
												List<String> corsiSelezionati) {
    	
    	
    	costruttoreDTOHelper.composizioneAbbonamento(sezioniSelezionate, corsiSelezionati);
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> retrieveSezioniAbbonamento(){
    	
    	return costruttoreDTOHelper.getSezioniAbbonamento();
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public List<String> retrieveCorsiSelezionati(){
    	
    	return costruttoreDTOHelper.getCorsiSelezionati();
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void annullaModsCompAbbonamento() {
    	
    	costruttoreDTOHelper.ripristinaListe();
    	
    }
    
    
    //----------------------------------------------------------------

}
