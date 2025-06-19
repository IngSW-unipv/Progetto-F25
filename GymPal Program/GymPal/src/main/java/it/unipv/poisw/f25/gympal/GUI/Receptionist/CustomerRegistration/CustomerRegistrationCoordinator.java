package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ICustomerRegistrationViewHandler;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ISubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.IClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.CostruttoreDTOHelper;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public class CustomerRegistrationCoordinator implements IRegistrationCoordinator{

    private ICustomerRegistrationViewHandler viewHandler;

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

    private AbbonamentoDTO abbonamentoDTO;
    private CostruttoreDTOHelper costruttoreDTOHelper;
    
    //----------------------------------------------------------------


    public CustomerRegistrationCoordinator(ICustomerRegistrationViewHandler viewHandler,
    									   ICalcoloEtaService etaService,
    									   ICampoValidabileFactory campovalidabileFactory,
    									   IValidatoreCampi validatoreCampi,
    									   ICtrlReqAnagraficiService controlloRequisiti,
    									   IStrategieCalcoloPrezzoFactory prezzoFactory) {
        
    	this.viewHandler = viewHandler;
    	this.etaService = etaService;
    	this.campovalidabileFactory = campovalidabileFactory;
    	this.validatoreCampi = validatoreCampi;
    	this.controlloRequisiti = controlloRequisiti;
    	this.prezzoFactory = prezzoFactory;
    	
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
    	
        subView = new SubscriptionCustomizationView();
        
        viewHandler.registraSchermata("SUB_VIEW", subView.getMainPanel());

        new SubscriptionCustomizationController(
            subView,
            
            () -> viewHandler.mostraSchermata("INFOS_VIEW"),
            
            () -> {
                inizializzaCicloRegistrazioneCliente();
                viewHandler.mostraSchermata("SCHERMATA0");
            },
            
            this
        );
        
    }

    // ----------------------------------------------------------------

    private void setupClientInfos() {
    	
        clientInfosView = new ClientInfosView();
        
        viewHandler.registraSchermata("INFOS_VIEW", clientInfosView.getMainPanel());

        new ClientInfosController(
            clientInfosView,

            // Callback onAvanti
            () -> {
                if (riepilogoEPagamento != null) {
                    viewHandler.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento.getMainPanel());
                }

                riepilogoEPagamento = new RiepilogoEPagamentoView();

                new RiepilogoEPagamentoController(
                    riepilogoEPagamento,
                    abbonamentoDTO,
                    
                    //On Indietro
                    () -> viewHandler.mostraSchermata("INFOS_VIEW"),
                    
                    //On Conferma
                    () -> {
                    	
                    	/*Qui è chiamato il metodo che passa i dati al service-layer*/
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
    
    /*@Override
    public void acquisisciStatoPagamento(boolean statoPagamento) {
    	
    	costruttoreDTOHelper.statoPagamento(statoPagamento);
    	
    }*/
    
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
    public double getDiscountedPrice(IRiepilogoDTO abbonamentoDTO) {

    	
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
    
}
