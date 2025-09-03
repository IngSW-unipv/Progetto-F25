package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.EstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.GestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IEstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.CommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ICommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.staff.Dipendente;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Dipendente.DipendenteController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Dipendente.DipendenteDashboardView;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class DipendenteDashboardAvviabile implements IDashboardAvviabile{
	
	private Dipendente dipendente;
	
    //----------------------------------------------------------------
	
	public DipendenteDashboardAvviabile(Dipendente dipendente) {
		
		this.dipendente = dipendente;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void avvia() {
		
		/*Facciata strato persistenza*/
		IPersistenceFacade persistence = PersistenceFacade.getInstance();
		
        /*Servizi per visualizzazione/popolazione calendario 
         *sessioni/eventi/appuntamenti/ecc.*/
        ICalendarioService calendarioService = new CalendarioService();
        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(calendarioService);
		
		/*Servizi comuni a più parti della GUI*/
        ICommonServicesBundleFactory factory = new CommonServicesBundleFactory(persistence, calendarioFacade);
        ICommonServicesBundle serviziComuni = factory.buildBundle();
		
		/*Servizio Estrazione Turni*/
		IEstraiTurniDipendenteService estraiTurni = new EstraiTurniDipendenteService(persistence);
		IGestoreTurniPersonali gestoreTurni = new GestoreTurniPersonali(estraiTurni);
		
		/*Istanziazione controllore e relativa vista*/
		DipendenteDashboardView dipDashView = new DipendenteDashboardView(serviziComuni.getFontManager());
		new DipendenteController(dipDashView,
								 dipendente,
								 serviziComuni,
								 gestoreTurni);
		dipDashView.setVisible(true);
		
	}
	
    //----------------------------------------------------------------

}
