package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento;

import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.DTOHandlerGestione;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.IRecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.IEliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.IModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ModAbbContract.IModAbbContract;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class GestioneAbbCoordinator implements IGestioneAbbCoordinator, ICoordinator, IModAbbContract{
	
	private String schermataPreRinnovo;
	private IReceptionistController viewHandler;
	
    /*Viste*/
    private IRecuperoDatiView recuperoDati;
    private IEliminaProfiloView eliminazioneDati;
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    private IModificaAbbonamentoView modificaAbb;
    
    /*Servizi*/
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private IRetrieveClientFromDB retrieveDBData;
    private IDeleteClientFromDB removeDBData;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    private IUpdateClientInsideDB updateDBData;
    private IRetrieveDiscountsFromDB getDiscounts;
    
    private MasterDTO abbDTO;
    private DTOHandlerGestione costruttoreDTOHelper;
	
    //----------------------------------------------------------------
	
	public GestioneAbbCoordinator(IReceptionistController viewHandler,
								  ICampoValidabileFactory campovalidabileFactory,
								  IValidatoreCampi validatoreCampi,
								  IRetrieveClientFromDB veicoloDati,
								  IDeleteClientFromDB headHunter,
								  IStrategieCalcoloPrezzoFactory prezzoFactory,
								  IUpdateClientInsideDB updateClient,
								  IRetrieveDiscountsFromDB getDiscounts) {
		
		/*Servizi*/
		this.viewHandler = viewHandler;
    	this.campoValidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.retrieveDBData = veicoloDati;
    	this.removeDBData = headHunter;
    	this.prezzoFactory = prezzoFactory;
    	this.updateDBData = updateClient;
    	this.getDiscounts = getDiscounts;
		
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
	
	private void setupRecuperoDati() {
		
		recuperoDati = new RecuperoDatiView (new DynamicButtonSizeSetter(),
											 new EtichettaPiuCampoFactory());
		
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
					
					/*if (riepilogoEPagamento != null) {
	                    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
	                }*/

	                riepilogoEPagamento = new RiepilogoEPagamentoView(new DynamicButtonSizeSetter());

	                new RiepilogoEPagamentoController(
	                    riepilogoEPagamento,
	                                        
	                    //On Indietro
	                    () -> viewHandler.mostraSchermata(schermataPreRinnovo),
	                    
	                    //On Conferma
	                    () -> {
	                    	
	                    	/*Qui è chiamato il metodo che passa i dati al service-layer*/
	                    	updateDBData.update(abbDTO);
	                    	
	                    	inizializzaCicloGestione();
	                        viewHandler.mostraSchermata("SCHERMATA0");
	                    },
	                    
	                    //On Annulla
	                    () -> {
	                    	
	                    	inizializzaCicloGestione();
	                        viewHandler.mostraSchermata("SCHERMATA0");
	                    	
	                    },
                    
	                    this
	                );

	                viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
	                viewHandler.mostraSchermata("RECAP_PAYMENT");		
					
				},
				
                //On Modifica
                () -> {
                	
                	setupModificaAbbonamento();
                	viewHandler.mostraSchermata("MODIFICA_ABB");
                	
                },
				
				this);
		
	}
	
    //----------------------------------------------------------------
	
	private void setupEliminaProfiloCliente() {
		
		eliminazioneDati = new EliminaProfiloView(new DynamicButtonSizeSetter());
		
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
										  
										  	
										    boolean esito = removeDBData.huntAndKill(abbDTO);
										    
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
	
	private void setupModificaAbbonamento() {
		
		modificaAbb = new ModificaAbbonamentoView(new DynamicButtonSizeSetter());
		
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
					
					/*if (riepilogoEPagamento != null) {
	                    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
	                }*/

	                riepilogoEPagamento = new RiepilogoEPagamentoView(new DynamicButtonSizeSetter());

	                new RiepilogoEPagamentoController(
	                    riepilogoEPagamento,
	                                        
	                    //On Indietro
	                    () -> viewHandler.mostraSchermata(schermataPreRinnovo),
	                    
	                    //On Conferma
	                    () -> {
	                    	
	                    	/*Qui è chiamato il metodo che passa i dati al service-layer*/
	                    	updateDBData.update(abbDTO);
	                    	
	                    	inizializzaCicloGestione();
	                        viewHandler.mostraSchermata("SCHERMATA0");
	                    },
	                    
	                    //On Annulla
	                    () -> {
	                    	
	                    	inizializzaCicloGestione();
	                        viewHandler.mostraSchermata("SCHERMATA0");
	                    	
	                    },
                    
	                    this
	                );

	                viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
	                viewHandler.mostraSchermata("RECAP_PAYMENT");
					
					
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
