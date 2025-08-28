package it.unipv.poisw.f25.gympal.GUI.Dipendente;

import java.util.List;

import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.staff.Dipendente;
import it.unipv.poisw.f25.gympal.GUI.LogoutView.ILogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.IVisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class DipendenteController implements IRegistraEMostraSchermate{

	private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IDashboard dipDashView;
    private ILogoutConfirmationView logoutView;
    
    /*Servizi*/
    private ICommonServicesBundle serviziComuni;
    private IGestoreTurniPersonali gestoreTurni;
    
    /*Coordinatori GUI*/
    private IVisualizzaTurniCoordinator turniCoordinator;
    
    /*Oggetto Dipendente: contiene "staffID"*/
    private Dipendente dipendente;
     
    //----------------------------------------------------------------
    
    public DipendenteController(IDashboard dipDashView,
    							Dipendente dipendente,
    							ICommonServicesBundle serviziComuni,
    							IGestoreTurniPersonali gestoreTurni) {
    	
    	/*Dashboard*/
    	this.dipDashView = dipDashView;
    	
    	/*Servizi*/
    	this.serviziComuni = serviziComuni;
    	this.gestoreTurni = gestoreTurni;
    	
    	/*Oggetto contenente "staffID"*/
    	this.dipendente = dipendente;
    	
    	/**/
    	registraAzioniPulsanti();
        inizializzaSchermateStatiche();
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void registraSchermata(String nomeSchermata, JPanel view) {
    	
    	dipDashView.registraSchermata(nomeSchermata, view);
        
    }

    @Override
    public void mostraSchermata(String nomeSchermata) {
    	
        schermataPreLogout = nomeSchermata;
        dipDashView.mostraSchermata(nomeSchermata);
        
    }
    
    //----------------------------------------------------------------
    
    private void inizializzaSchermateStatiche() {

        logoutView = new LogoutConfirmationView();
        dipDashView.registraSchermata("LOGOUT_VIEW", logoutView.getMainPanel());
        
    }
    
    //----------------------------------------------------------------
    
    private void registraAzioniPulsanti() {
    	
    	dipDashView.aggiungiComando("MOSTRA_TURNI", () -> {
    		
    		mostraVisualizzaTurniSeNonInizializzato();
    		mostraSchermata("TURNI");}
    	
    	);

    	dipDashView.aggiungiComando("LOGOUT", () -> {
        	
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            
            new LogoutConfirmationController(logoutView, 
						            		(IDashboard)dipDashView, 
											 schermataPreLogout,
											 serviziComuni);
            dipDashView.mostraSchermata("LOGOUT_VIEW"); 
            
        });
        
    }
    
    //----------------------------------------------------------------
    
    private void mostraVisualizzaTurniSeNonInizializzato() {
    	
        if (turniCoordinator == null) {
        	
            String staffID = dipendente.getStaffID();

            List<Turno> turni = gestoreTurni.caricaTurni(staffID);
            List<TurnoIndividuale> personali = gestoreTurni
            								  .estraiTurniPersonali(staffID, turni);

            turniCoordinator = new VisualizzaTurniCoordinator(this, 
            				                                  personali,
            				                                  serviziComuni.getFontChangeRegister());
            
        }
        
    }
    
    //----------------------------------------------------------------
	
}
