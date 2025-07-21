package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	
    public ReceptionistDashboardAvviabile() {}

    @Override
    public void avvia() {
    	
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView();
        new ReceptionistController(recDashView);
        recDashView.setVisible(true);
        
    }

}
