package it.unipv.poisw.f25.gympal.GUI.Manager;

import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.IRettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.RettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class ManagerController implements IManagerController{
	
	private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IManagerDashboardView manDashView;
    private LogoutConfirmationView logoutView;
    
    /*Servizi*/
    private CommonServicesBundle serviziComuni;
    
    /*Coordinatori GUI*/
    private IRettificaCoordinator rettCoord;
    
    //----------------------------------------------------------------
    
    public ManagerController(IManagerDashboardView view,
    						 CommonServicesBundle serviziComuni) {
    	
    	/*Dashboard*/
    	manDashView = view;
    	
    	/*Servizi*/
    	this.serviziComuni = serviziComuni;
    	
    	/**/
        registraAzioniPulsanti();
        inizializzaSchermateStatiche();
        
        /*Coordinatori*/
        rettCoord = new RettificaCoordinator(this,
        			this.serviziComuni.getCampoValidabileFactory(),
        			this.serviziComuni.getValidatoreCampi(),
        			this.serviziComuni.getRecuperaDati(),
        			this.serviziComuni.getHeadHunter(),
        			this.serviziComuni.getUpdater(),
        			this.serviziComuni.getImmettiDati());
    	
    }
    
    //----------------------------------------------------------------
    
    private void inizializzaSchermateStatiche() {

        logoutView = new LogoutConfirmationView();
        manDashView.registraSchermata("LOGOUT_VIEW", logoutView);
        
    }

    //----------------------------------------------------------------

    private void registraAzioniPulsanti() {

    	/*manDashView.aggiungiComando("REGISTER", () -> mostraSchermata("SUB_VIEW"));*/

    	manDashView.aggiungiComando("RETTIFICA", () -> mostraSchermata("RETT_INFO"));

    	manDashView.aggiungiComando("LOGOUT", () -> {
        	
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            new LogoutConfirmationController(logoutView, (IDashboard)manDashView, schermataPreLogout);
            manDashView.mostraSchermata("LOGOUT_VIEW"); 
            
        });
        
    }

    //----------------------------------------------------------------
    
    @Override
    public void registraSchermata(String nomeSchermata, JPanel view) {
    	
    	manDashView.registraSchermata(nomeSchermata, view);
        
    }

    @Override
    public void mostraSchermata(String nomeSchermata) {
    	
        schermataPreLogout = nomeSchermata;
        manDashView.mostraSchermata(nomeSchermata);
        
    }

    //----------------------------------------------------------------

}
