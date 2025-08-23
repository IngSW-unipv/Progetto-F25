package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.EstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.GestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.IEstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Dipendente.DipendenteController;
import it.unipv.poisw.f25.gympal.GUI.Dipendente.DipendenteDashboardView;
import it.unipv.poisw.f25.gympal.staff.Dipendente;

public class DipendenteDashboardAvviabile implements IDashboardAvviabile{
	
	private final Dipendente dipendente;
	
	public DipendenteDashboardAvviabile(Dipendente dipendente) {
		
		this.dipendente = dipendente;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public void avvia() {
		
		/*Servizi comuni a più parti della GUI*/
		ICommonServicesBundle serviziComuni = new CommonServicesBundle();
		
		/*Servizio Estrazione Turni*/
		IEstraiTurniDipendenteService estraiTurni = new EstraiTurniDipendenteService();
		IGestoreTurniPersonali gestoreTurni = new GestoreTurniPersonali(estraiTurni);
		
		/*Istanziazione controllore e relativa vista*/
		DipendenteDashboardView dipDashView = new DipendenteDashboardView();
		new DipendenteController(dipDashView,
								 dipendente,
								 serviziComuni,
								 gestoreTurni);
		dipDashView.setVisible(true);
		
	}
	
    //----------------------------------------------------------------

}
