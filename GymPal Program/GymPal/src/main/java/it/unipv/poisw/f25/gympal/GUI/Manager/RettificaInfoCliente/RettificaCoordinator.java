package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Manager.IManagerController;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.DTO.DTOHandlerRettifica;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.IRettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.RettificaInfoController;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica.RettificaInfoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DTOBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;

public class RettificaCoordinator implements IRettificaCoordinator{
	
	/*Controllore*/
	private IManagerController viewHandler;

	/*DTO e Inizializzatore*/
    private MasterDTO abbDTO;
    private DTOHandlerRettifica costruttoreDTOHelper;
    
    /*Viste*/
    private IRettificaInfoView rettView;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB recuperaDati;
    private IDeleteClientFromDB headHunter;
    private IUpdateClientInsideDB updateClient;
    private ICommitNewClientToDB immettiDati;
    
    //----------------------------------------------------------------
    
    public RettificaCoordinator(IManagerController viewHandler,
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
    	this.recuperaDati = recuperaDati;
    	this.headHunter = headHunter;
    	this.updateClient = updateClient;
    	this.immettiDati = immettiDati;
    	
    	/*Navigazione GUI*/
    	inizializzaCicloRettifica();
    	
    }
    
    //----------------------------------------------------------------
    
	private void inizializzaCicloRettifica() {
		
		
		abbDTO = new MasterDTO();
		costruttoreDTOHelper = new DTOHandlerRettifica(abbDTO);
		rettView = new RettificaInfoView(new DynamicButtonSizeSetter(),
							  			 new EtichettaPiuCampoFactory());
		
		viewHandler.registraSchermata("RETT_INFO", rettView.getMainPanel());
		
		new RettificaInfoController(
				
				rettView,
				
				/*onEstrai*/
				() -> {recuperaDati.retrieve(abbDTO);},
				
				/*onElimina*/
				() -> {
														    
				    if (headHunter.huntAndKill(abbDTO)) {
				    	
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
					
				},
				
				/*onSaveMods*/
				() -> {
					
					if (updateClient.update(abbDTO, abbDTO.getInizioAbbonamento())) {
						
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
					
                	                	
                	if (immettiDati.commit(abbDTO, abbDTO.getInizioAbbonamento())) {
						
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
				() -> {
					
					inizializzaCicloRettifica();
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
    public IDatiCliente getDTO() {
    	
    	return abbDTO;
    	
    }
    
    //----------------------------------------------------------------
       
    @Override
    public void acquisisciDatiAnagrafici(String nome, String cognome,
			 						     String codiceFiscale, String contatto, 
			 						     String sesso, LocalDate dataNascita) {
    	
    	costruttoreDTOHelper.impostaDatiAnagrafici(nome, cognome, codiceFiscale, 
    											   contatto, sesso,dataNascita);
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void aggiornaDatiAnagrafici(String nome, String cognome, String contatto, 
			 						   String sesso, LocalDate dataNascita) {
    	
    	costruttoreDTOHelper.aggiornaDatiAnagrafici(nome, cognome,contatto, 
    											    sesso,dataNascita);
    	
    }
    
    //----------------------------------------------------------------

}
