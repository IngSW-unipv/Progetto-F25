package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento;

import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.DTOHandler.DTOHandlerGestione;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.IRecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.IEliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.IModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ModAbbContract.IModAbbContract;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class GestioneAbbCoordinator implements IGestioneAbbCoordinator, ICoordinator, IModAbbContract{
	
	private String schermataPreRinnovo;
	private IRegistraEMostraSchermate viewHandler;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB retrieveDBData;
    private IDeleteClientFromDB removeDBData;
    private IUpdateClientInsideDB updateDBData;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    private IRetrieveDiscountsFromDB getDiscounts;
    private IFontChangeRegister changeRegister;
    private IDialogUtils dialogUtils;
    
    private MasterDTO abbDTO;
    private DTOHandlerGestione costruttoreDTOHelper;
	
    //----------------------------------------------------------------
	
	public GestioneAbbCoordinator(IRegistraEMostraSchermate viewHandler,
								  ICommonServicesBundle serviziComuni,
								  IDialogUtils dialogUtils) {
		
		/*Servizi*/
		this.viewHandler = viewHandler;
    	this.campoValidabileFactory = serviziComuni.getCampoValidabileFactory();
    	this.validatoreCampi = serviziComuni.getValidatoreCampi();
    	this.retrieveDBData = serviziComuni.getRecuperaDati();
    	this.removeDBData = serviziComuni.getHeadHunter();
    	this.prezzoFactory = serviziComuni.getPrezzoFactory();
    	this.updateDBData = serviziComuni.getUpdater();
    	this.getDiscounts = serviziComuni.getDiscounts();
    	this.changeRegister = serviziComuni.getFontChangeRegister();
    	this.dialogUtils = dialogUtils;
    			
    	/*Navigazione GUI*/
		inizializzaCicloGestione();
		
	}
	
    //----------------------------------------------------------------
	
	private void inizializzaCicloGestione() {
		
		
		abbDTO = new MasterDTO();
		costruttoreDTOHelper = new DTOHandlerGestione(abbDTO);
		
		setupRecuperoDati();
		
	}
	
    //----------------------------------------------------------------
		
	private void setupRiepilogoEPagamento() {
		
	    IRiepilogoEPagamentoView riepilogoEPagamento = 
	    		new RiepilogoEPagamentoView(
	    				new DynamicButtonSizeSetter(), changeRegister);

	    new RiepilogoEPagamentoController(
	        riepilogoEPagamento,

	        // On Indietro
	        () -> viewHandler.mostraSchermata(schermataPreRinnovo),

	        // On Conferma
	        () -> {
	            updateDBData.update(abbDTO);
	            inizializzaCicloGestione();
	            viewHandler.mostraSchermata("SCHERMATA0");
	        },

	        // On Annulla
	        () -> {
	            inizializzaCicloGestione();
	            viewHandler.mostraSchermata("SCHERMATA0");
	        },

	        this
	        
	    );

	    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
	    viewHandler.mostraSchermata("RECAP_PAYMENT");
	    
	}
	
    //----------------------------------------------------------------
	
	private void setupRecuperoDati() {
		
		IRecuperoDatiView recuperoDati = new RecuperoDatiView (new DynamicButtonSizeSetter(),
											 new EtichettaPiuCampoFactory(),
											 changeRegister);
		
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
					
					//System.out.println("PRE ELIMINAZIONE: " + abbDTO.getCodiceFiscale());
					setupEliminaProfiloCliente();
					viewHandler.mostraSchermata("ELIMINA_CLIENT");
					
				},
				
				//OnEstrai
				() -> {
					
					retrieveDBData.retrieve(abbDTO);
					
					
				},
				
				//OnRinnova
				() -> {
					
					schermataPreRinnovo = "LOAD_CLIENT";

					setupRiepilogoEPagamento();		
					
				},
				
                //On Modifica
                () -> {
                	
                	setupModificaAbbonamento();
                	viewHandler.mostraSchermata("MODIFICA_ABB");
                	
                },
				
				this);
		
	}
	
    //----------------------------------------------------------------
	
	private void setupModificaAbbonamento() {
		
		IModificaAbbonamentoView modificaAbb = 
				new ModificaAbbonamentoView(
						new DynamicButtonSizeSetter(), changeRegister);
		
		viewHandler.registraSchermata("MODIFICA_ABB", modificaAbb.getMainPanel());
		
		new ModificaAbbonamentoController (modificaAbb,
				
				//onAnnulla
				() -> {					
					
					inizializzaCicloGestione();
					viewHandler.mostraSchermata("SCHERMATA0");},
				
				//onIndietro
				() -> {viewHandler.mostraSchermata("LOAD_CLIENT");},
				
				//onAvanti
				() -> {
					
					schermataPreRinnovo = "MODIFICA_ABB";
					
					setupRiepilogoEPagamento();
					
					
				},
				
				this);
		
	}
	
	//----------------------------------------------------------------
	
	private void setupEliminaProfiloCliente() {
		
		IEliminaProfiloView eliminazioneDati = new EliminaProfiloView(new DynamicButtonSizeSetter(),
																	  changeRegister);
		
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
									  () -> {eliminaClienteDaDB();},
									  
									  this);
		
		
	}
	
	//----------------------------------------------------------------
	
	
	
	private void eliminaClienteDaDB() {
		
	    try {
	    	
	        boolean esito = removeDBData.eliminaClienteCompletamente(abbDTO);
	        mostraMessaggioEsito(esito);
	        
	    } catch (Exception ex) {
	    	
	        dialogUtils.mostraErrore(
	            "Errore imprevisto durante l'eliminazione del "
	            + "profilo:\n" + ex.getMessage()
	        );
	        
	    } finally {
	    	
	        inizializzaCicloGestione();
	        viewHandler.mostraSchermata("SCHERMATA0");
	        
	    }
	    
	}
	
	//----------------------------------------------------------------
	
	private void mostraMessaggioEsito(boolean esito) {
		
	    if (esito) {
	    	
	        dialogUtils.mostraInfo("Eliminazione avvenuta con successo.");
	        
	    } else {
	    	
	        dialogUtils.mostraErrore("Errore durante l'eliminazione del profilo.");
	        
	    }
	    
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
    public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento) {
    	
    	costruttoreDTOHelper.impostaMetodoPagamento(metodoPagamento);
    	
    }
    
   //----------------------------------------------------------------
    
    
    @Override
    public void acquisisciScontoEta(boolean scontoEta) {
    	
    	costruttoreDTOHelper.impostaScontoEta(scontoEta);
    	
    }
    
    @Override
    public void acquisisciScontoOccasioni(boolean scontoOccasioni) {
    	
    	costruttoreDTOHelper.impostaScontoOccasioni(scontoOccasioni);
    	
    }
    
    @Override
    public void acquisisciDurataAbbonamento(DurataAbbonamento durataAbbonamento) {
    	
    	costruttoreDTOHelper.impostaDurataAbbonamento(durataAbbonamento);
    	
    }
	
	//---------------------------------------------------------------- 
    
    @Override
    public void acquisisciScontiOccasioneSelezionati(List<Sconto> scontiOccasioneSelezionati) {
    	
    	costruttoreDTOHelper.inizializzaListaScontiOccasione(scontiOccasioneSelezionati);
    	
    }
    
    //---------------------------------------------------------------- 
    
    @Override
    public IRetrieveDiscountsFromDB getScontiOccasioni() {
    	
    	return getDiscounts;
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public double getDiscountedPrice() {

    	
    	IStrategieCalcoloPrezzo strategia = prezzoFactory.getStrategy(abbDTO);
    	
    	return strategia.calcolaPrezzo(abbDTO);
    	
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
    public String esponiDurataAbbonamento() {
    	
    	return abbDTO.getDurataAbbonamento().toString();
    	
    }
    
    //---------------------------------------------------------------- 
    
    @Override
    public IDatiCliente getDTO() {
    	
    	return abbDTO;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void annullaModsCompAbbonamento() {
    	
    	costruttoreDTOHelper.ripristinaListe();
    	
    }
    
    
    //----------------------------------------------------------------
    

}
