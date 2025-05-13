package it.unipv.poisw.f25.gympal.HelperClasses;

import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.ReceptionistController.ReceptionistController;
import it.unipv.poisw.f25.gympal.staff.Receptionist;
import it.unipv.poisw.f25.gympal.utility.IDashboardAvviabile;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	private Receptionist receptionist;

    public ReceptionistDashboardAvviabile(Receptionist receptionist) {
    	
        this.receptionist = receptionist;
        
    }

    @Override
    public void avvia() {
    	
        ReceptionistDashboardView view = new ReceptionistDashboardView();
        new ReceptionistController(view, receptionist);
        view.setVisible(true);
        
    }

}
