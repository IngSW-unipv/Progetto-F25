package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	
    public ReceptionistDashboardAvviabile() {}
    
    //----------------------------------------------------------------

    @Override
    public void avvia() {
    	
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView();
        RegistrationServicesBundle serviziReg = new RegistrationServicesBundle();
        CommonServicesBundle serviziComuni = new CommonServicesBundle();
        new ReceptionistController(recDashView, serviziReg, serviziComuni);
        recDashView.setVisible(true);
        
    }
    
    //----------------------------------------------------------------

}
