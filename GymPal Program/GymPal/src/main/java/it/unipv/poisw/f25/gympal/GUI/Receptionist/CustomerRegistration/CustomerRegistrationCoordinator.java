package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.ICustomerRegistrationViewHandler;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.DTO.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ISubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.IClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.IRiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoView;

public class CustomerRegistrationCoordinator {

    private final ICustomerRegistrationViewHandler viewHandler;

    private ISubscriptionCustomizationView subView;
    private IClientInfosView clientInfosView;
    private IRiepilogoEPagamentoView riepilogoEPagamento;

    private AbbonamentoDTO abbonamentoDTO;
    
    //----------------------------------------------------------------


    public CustomerRegistrationCoordinator(ICustomerRegistrationViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        inizializzaCicloRegistrazioneCliente();
    }
    
    //----------------------------------------------------------------

    public void inizializzaCicloRegistrazioneCliente() {
    	
        abbonamentoDTO = new AbbonamentoDTO();
        
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
            
            abbonamentoDTO
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
                    () -> viewHandler.mostraSchermata("INFOS_VIEW"),
                    () -> {
                        inizializzaCicloRegistrazioneCliente();
                        viewHandler.mostraSchermata("SCHERMATA0");
                    }
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

            abbonamentoDTO
        );
        
    }
    
    //----------------------------------------------------------------
    
}
