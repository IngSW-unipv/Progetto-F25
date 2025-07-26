package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento;

import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.DTOHandlerHelper;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.UtenteAbbDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.IRecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.RecuperoDatiView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.EliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente.IEliminaProfiloView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.IModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente.ModificaAbbonamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class GestioneAbbCoordinator implements IGestioneAbbCoordinator, ICoordinator{
	
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
    private IRetrieveClientFromDB veicoloDati;
    private IDeleteClientFromDB headHunter;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    private IUpdateClientInsideDB updateClient;
    
    
    private UtenteAbbDTO utenteAbbDTO;
    private DTOHandlerHelper costruttoreDTOHelper;
	
    //----------------------------------------------------------------
	
	public GestioneAbbCoordinator(IReceptionistController viewHandler,
								  ICampoValidabileFactory campovalidabileFactory,
								  IValidatoreCampi validatoreCampi,
								  IRetrieveClientFromDB veicoloDati,
								  IDeleteClientFromDB headHunter,
								  IStrategieCalcoloPrezzoFactory prezzoFactory,
								  IUpdateClientInsideDB updateClient) {
		
		this.viewHandler = viewHandler;
    	this.campoValidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.veicoloDati = veicoloDati;
    	this.headHunter = headHunter;
    	this.prezzoFactory = prezzoFactory;
    	this.updateClient = updateClient;
		
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
		
		recuperoDati = new RecuperoDatiView (new DynamicButtonSizeSetter());
		
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
					
					setupEliminaProfiloCliente();
					viewHandler.mostraSchermata("ELIMINA_CLIENT");
					
				},
				
				//OnEstrai
				() -> {
					
					veicoloDati.retrieve(utenteAbbDTO);
					
					
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
	                    	updateClient.update(utenteAbbDTO);
	                    	
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
	                    	updateClient.update(utenteAbbDTO);
	                    	
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
    public IDatiCliente getDTO() {
    	
    	return utenteAbbDTO;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento) {
    	
    	costruttoreDTOHelper.impostaMetodoPagamento(metodoPagamento);
    	
    }
    
   //----------------------------------------------------------------
    
    @Override
    public void acquisisciScontiEDurata(boolean scontoEta, boolean scontoOccasioni,
			DurataAbbonamento durataAbbonamento) {

    	costruttoreDTOHelper.impostaScontiEDurata(scontoEta, scontoOccasioni,
					  							  durataAbbonamento);

	}
	
	//---------------------------------------------------------------- 
    
    @Override
    public double getDiscountedPrice(ICalcolaPrezzo abbDTO) {

    	
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
    

}
