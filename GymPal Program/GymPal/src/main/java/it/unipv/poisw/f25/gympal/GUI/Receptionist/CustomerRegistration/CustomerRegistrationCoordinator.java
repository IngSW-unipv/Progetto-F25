package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ISubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.IClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.CostruttoreDTOHelper;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.ICoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

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
    
    private ICommitNewClientToDB veicoloDati;


    private AbbonamentoDTO abbonamentoDTO;
    private CostruttoreDTOHelper costruttoreDTOHelper;
    
    //----------------------------------------------------------------


    public CustomerRegistrationCoordinator(IReceptionistController viewHandler,
    									   ICalcoloEtaService etaService,
    									   ICampoValidabileFactory campovalidabileFactory,
    									   IValidatoreCampi validatoreCampi,
    									   ICtrlReqAnagraficiService controlloRequisiti,
    									   IStrategieCalcoloPrezzoFactory prezzoFactory,
    									   ICommitNewClientToDB veicoloDati) {
        
    	this.viewHandler = viewHandler;
    	this.etaService = etaService;
    	this.campovalidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.controlloRequisiti = controlloRequisiti;
    	this.prezzoFactory = prezzoFactory;
    	
    	this.veicoloDati = veicoloDati;

    	
        inizializzaCicloRegistrazioneCliente();
                
    }
    
    //----------------------------------------------------------------

    public void inizializzaCicloRegistrazioneCliente() {
    	
        abbonamentoDTO = new AbbonamentoDTO();
        
    	costruttoreDTOHelper = new CostruttoreDTOHelper(abbonamentoDTO);
        
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
    	
        clientInfosView = new ClientInfosView(new DynamicButtonSizeSetter());
        
        viewHandler.registraSchermata("INFOS_VIEW", clientInfosView.getMainPanel());

        new ClientInfosController(
            clientInfosView,

            // Callback onAvanti
            () -> {
                if (riepilogoEPagamento != null) {
                    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
                }

                riepilogoEPagamento = new RiepilogoEPagamentoView(new DynamicButtonSizeSetter());

                new RiepilogoEPagamentoController(
                    riepilogoEPagamento,
                                        
                    //On Indietro
                    () -> viewHandler.mostraSchermata("INFOS_VIEW"),
                    
                    //On Conferma
                    () -> {
                    	
                    	/*Qui è chiamato il metodo che passa i dati al service-layer*/
                    	veicoloDati.transfer(abbonamentoDTO);
                    	
                        inizializzaCicloRegistrazioneCliente();
                        viewHandler.mostraSchermata("SCHERMATA0");
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
    public double getDiscountedPrice(ICalcolaPrezzo abbonamentoDTO) {

    	
    	IStrategieCalcoloPrezzo strategia = prezzoFactory.getStrategy(abbonamentoDTO);
    	
    	return strategia.calcolaPrezzo(abbonamentoDTO);
    	
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
    public IDatiCliente getDTO() {
    	
    	return abbonamentoDTO;
    	
    }
    
    //---------------------------------------------------------------- 
    
}
