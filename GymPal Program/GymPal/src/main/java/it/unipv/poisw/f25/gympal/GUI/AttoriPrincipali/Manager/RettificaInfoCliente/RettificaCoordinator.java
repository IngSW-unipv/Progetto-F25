package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente;

import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.DTOHandler.DTOHandlerRettifica;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.Rettifica.IRettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.Rettifica.RettificaInfoController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.Rettifica.RettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.IModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ModAbbContract.IModAbbContract;

public class RettificaCoordinator implements IRettificaCoordinator, IModAbbContract{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB retrieveDBData;
    private IDeleteClientFromDB removeDBData;
    private IUpdateClientInsideDB updateDBData;
    private ICommitNewClientToDB commitDataToDB;
    private IFontChangeRegister changeRegister;
    

	/*DTO e Inizializzatore*/
    private MasterDTO abbDTO;
    private DTOHandlerRettifica costruttoreDTOHelper;
    
    //----------------------------------------------------------------
    
    public RettificaCoordinator(IRegistraEMostraSchermate viewHandler,
    							ICommonServicesBundle serviziComuni) {
    	
    	/*Controllore*/
    	this.viewHandler = viewHandler;
    	
    	/*Servizi*/
    	this.campoValidabileFactory = serviziComuni.getCampoValidabileFactory();
    	this.validatoreCampi = serviziComuni.getValidatoreCampi();
    	this.retrieveDBData = serviziComuni.getRecuperaDati();
    	this.removeDBData = serviziComuni.getHeadHunter();
    	this.updateDBData = serviziComuni.getUpdater();
    	this.commitDataToDB = serviziComuni.getImmettiDati();
    	this.changeRegister = serviziComuni.getFontChangeRegister();
    	
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
		
		IRettificaInfoView rettView = new RettificaInfoView(new DynamicButtonSizeSetter(),
	  			 						 new EtichettaPiuCampoFactory(), changeRegister);

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
		
		IModificaAbbonamentoView modificaAbb = new ModificaAbbonamentoView(new DynamicButtonSizeSetter(), changeRegister);
		
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
