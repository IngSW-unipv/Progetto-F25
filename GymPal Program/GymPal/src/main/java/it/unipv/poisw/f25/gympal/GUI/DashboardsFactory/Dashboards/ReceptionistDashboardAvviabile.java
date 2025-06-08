package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	private Receptionist receptionist;

    public ReceptionistDashboardAvviabile(Receptionist receptionist) {
    	
        this.receptionist = receptionist;
        
    }

    @Override
    public void avvia() {
    	
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView();
        new ReceptionistController(recDashView, receptionist);
        recDashView.setVisible(true);
        
    }

}
