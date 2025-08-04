package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOBuilder.MasterDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ISubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.IClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.DTOHandlerRegistrazione;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.EtichettaPiuCampoFactory;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class CustomerRegistrationCoordinator implements IRegistrationCoordinator, ICoordinator{

    private IReceptionistController viewHandler;

    /*Viste*/
    private ISubscriptionCustomizationView subView;
    private IClientInfosView clientInfosView;
    private IRiepilogoEPagamentoView riepilogoEPagamento;
    
    /*Servizi*/
    private ICalcoloEtaService etaService;
    private ICampoValidabileFactory campovalidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private ICtrlReqAnagraficiService controlloRequisiti;
    private IStrategieCalcoloPrezzoFactory prezzoFactory;
    private ICommitNewClientToDB commitDataToDB;
    private IRetrieveDiscountsFromDB getDiscounts;


    private MasterDTO abbDTO;
    private DTOHandlerRegistrazione costruttoreDTOHelper;
    
    //----------------------------------------------------------------


    public CustomerRegistrationCoordinator(IReceptionistController viewHandler,
    									   ICalcoloEtaService etaService,
    									   ICampoValidabileFactory campovalidabileFactory,
    									   IValidatoreCampi validatoreCampi,
    									   ICtrlReqAnagraficiService controlloRequisiti,
    									   IStrategieCalcoloPrezzoFactory prezzoFactory,
    									   ICommitNewClientToDB veicoloDati,
    									   IRetrieveDiscountsFromDB getDiscounts) {
        
    	/*Servizi*/
    	this.viewHandler = viewHandler;
    	this.etaService = etaService;
    	this.campovalidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.controlloRequisiti = controlloRequisiti;
    	this.prezzoFactory = prezzoFactory;
    	this.getDiscounts = getDiscounts;
    	this.commitDataToDB = veicoloDati;

    	/*Navigazione GUI*/
        inizializzaCicloRegistrazioneCliente();
                
    }
    
    //----------------------------------------------------------------

    private void inizializzaCicloRegistrazioneCliente() {
    	
        abbDTO = new MasterDTO();
        
    	costruttoreDTOHelper = new DTOHandlerRegistrazione(abbDTO);
        
        setupSubscriptionCustomization();
        
        setupClientInfos();
        
    }

    // ----------------------------------------------------------------

    private void setupSubscriptionCustomization() {
    	
        subView = new SubscriptionCustomizationView(new DynamicButtonSizeSetter());
        
        viewHandler.registraSchermata("SUB_VIEW", subView.getMainPanel());

        new SubscriptionCustomizationController(
        		
            subView,
            
            //OnAvanti
            () -> viewHandler.mostraSchermata("INFOS_VIEW"),
            
            //OnAnnulla
            () -> {
                inizializzaCicloRegistrazioneCliente();
                viewHandler.mostraSchermata("SCHERMATA0");
            },
            
            this
        );
        
    }

    // ----------------------------------------------------------------

    private void setupClientInfos() {
    	
        clientInfosView = new ClientInfosView(new DynamicButtonSizeSetter(),
        									  new EtichettaPiuCampoFactory());
        
        viewHandler.registraSchermata("INFOS_VIEW", clientInfosView.getMainPanel());

        new ClientInfosController(
            clientInfosView,

            // Callback onAvanti
            () -> {
                /*if (riepilogoEPagamento != null) {
                    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
                }*/

                riepilogoEPagamento = new RiepilogoEPagamentoView(new DynamicButtonSizeSetter());

                new RiepilogoEPagamentoController(
                    riepilogoEPagamento,
                                        
                    //On Indietro
                    () -> viewHandler.mostraSchermata("INFOS_VIEW"),
                    
                    //On Conferma
                    () -> {
                    	
                    	/*Qui è chiamato il metodo che passa i dati al service-layer*/
                    	if (commitDataToDB.commit(abbDTO)) {
    						
    					    JOptionPane.showMessageDialog(null, "Registrazione effettuata con successo!",
    					    							  "Successo", JOptionPane.INFORMATION_MESSAGE);
                            inizializzaCicloRegistrazioneCliente();
                            viewHandler.mostraSchermata("SCHERMATA0");
    	                    
    					} else {
    						
    					    JOptionPane.showMessageDialog(null, "Errore durante salvataggio dati.",
    					    							  "Errore", JOptionPane.ERROR_MESSAGE);
    					    
    					}
                    	
                    	

                    },
                    
                    //On Annulla
                    () -> {
                    	
                    	inizializzaCicloRegistrazioneCliente();
                        viewHandler.mostraSchermata("SCHERMATA0");
                    	
                    },
                    
                    this
                );

                viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
                viewHandler.mostraSchermata("RECAP_PAYMENT");
            },

            // Callback onIndietro
            () -> viewHandler.mostraSchermata("SUB_VIEW"),

            // Callback onAnnulla
            () -> {
                inizializzaCicloRegistrazioneCliente();
                viewHandler.mostraSchermata("SCHERMATA0");
            },

            this
        );
        
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
												List<String> corsiSelezionati) {
    	
    	
    	costruttoreDTOHelper.composizioneAbbonamento(sezioniSelezionate, corsiSelezionati);
    	
    }
     
    //----------------------------------------------------------------
    
    @Override
    public void acquisisciDatiAnagrafici(String nome, String cognome,
			 String codiceFiscale,
			 String contatto, String sesso,
			 boolean certificatoIdoneita,
			 boolean permessoGenitori, 
			 LocalDate dataNascita) {
    	
    	costruttoreDTOHelper.impostaDatiAnagrafici(nome, cognome, codiceFiscale, 
    											   contatto, sesso, certificatoIdoneita,
    											   permessoGenitori, dataNascita);
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public boolean isMinorenne(LocalDate dataNascita) {
    	
    	return etaService.isMinorenne(dataNascita);
    	
    };
    
    //----------------------------------------------------------------
    
    @Override
    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
    	return campovalidabileFactory;
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public IValidatoreCampi getValidatoreCampi() {
    	
    	return validatoreCampi;
    	
    }
     
    //----------------------------------------------------------------
    
    @Override
    public ICtrlReqAnagraficiService getCtrlReqAnagraficiService() {
    	
    	return controlloRequisiti;
    	
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
    public String esponiDurataAbbonamento() {
    	
    	return abbDTO.getDurataAbbonamento().toString();
    	
    }
    
    //---------------------------------------------------------------- 
    
    @Override
    public IDatiCliente getDTO() {
    	
    	return abbDTO;
    	
    }
    
    //---------------------------------------------------------------- 
    
}
