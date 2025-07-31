package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Manager.ManagerController;
import it.unipv.poisw.f25.gympal.GUI.Manager.ManagerDashboardView;

public class ManagerDashboardAvviabile implements IDashboardAvviabile{
	
	public ManagerDashboardAvviabile() {}
	
    //----------------------------------------------------------------
	
	@Override
	public void avvia() {
		
		ManagerDashboardView manDashView = new ManagerDashboardView();
		CommonServicesBundle serviziComuni = new CommonServicesBundle();
		new ManagerController(manDashView, serviziComuni);
		manDashView.setVisible(true);
		
		
	}
	
    //----------------------------------------------------------------

}
